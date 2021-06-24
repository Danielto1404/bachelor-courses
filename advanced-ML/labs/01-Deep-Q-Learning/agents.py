import os
from copy import deepcopy

import gym
import matplotlib.pyplot as plt
import torch
import torch.nn as nn

from core.action_selector import ActionSelector
from core.eps_strategy import EpsilonStrategy, LinearEpsilonStrategy
from core.experience_buffer import ExperienceBuffer, Transition
from core.rewards import *

os.environ['KMP_DUPLICATE_LIB_OK'] = 'True'


class Agent:
    def __init__(self,
                 state_dim,
                 action_dim,
                 env=gym.make('MountainCar-v0'),
                 lr=0.0003,
                 gamma=0.99,
                 weight_decay=0,
                 atari_mode=False,
                 loss_function=nn.MSELoss(),
                 update_model_frequency=3,
                 experience_buffer=ExperienceBuffer(),
                 eps_strategy=EpsilonStrategy(decay=5e-2),
                 device=torch.device('cuda' if torch.cuda.is_available() else 'cpu')):

        self.state_dim = state_dim
        self.action_dim = action_dim
        self.env = env

        # Buffer
        self.experience_buffer = experience_buffer

        # Constants
        self.eps_strategy = eps_strategy
        self.gamma = gamma
        self.weight_decay = weight_decay
        self.update_model_frequency = update_model_frequency
        self.lr = lr

        # Network
        self.loss_function = loss_function
        self.device = device

        # Default values
        self.atari_mode = atari_mode
        self.optimizer = None
        self.policy_model, self.target_model = None, None
        self.policy_action_selector = None

        self.set_policy_model(self.__get_model__())
        self.set_target_model(self.__get_model__())

    def choose_eps_action(self, state):
        """
        Represents eps-greedy selection according to given eps-strategy.
        """
        if self.eps_strategy.check_random_prob():
            return self.env.action_space.sample()
        else:
            return self.choose_action(state)

    def choose_action(self, state):
        """
        :return: (best action, Q-value for best action)
        """
        return self.policy_action_selector.choose_action(state)

    def store_transition(self, state, next_state, action, reward, terminal):
        """
        Stores environment transition in buffer.
        """
        transition = Transition(state=state, next_state=next_state, action=action, reward=reward, terminal=terminal)
        self.experience_buffer.store_transition(transition)

    def learn(self, episode):
        """
        Trains Q-function net.
        Using fixed target-model to predict target Q-function and q-model to choose actions while training.
        """
        if not self.experience_buffer.is_ready_for_sample():
            return

        states, next_states, actions, rewards, terminals = self.__sample_batch__()
        q_eval = self.policy_model(states)[self.experience_buffer.batch_indices, actions]

        with torch.no_grad():
            q_future = self.target_model(next_states).max(dim=1).values
            q_future[terminals] = 0.0
            q_target = rewards + self.gamma * q_future

        loss = self.__fit_network__(q_eval=q_eval, q_target=q_target)

        if episode % self.update_model_frequency == 0:
            self.target_model.load_state_dict(self.policy_model.state_dict())

        return loss

    def set_policy_model(self, model):
        self.policy_model = model.to(self.device)
        self.policy_action_selector = ActionSelector(model=model,
                                                     atari_mode=self.atari_mode,
                                                     device=self.device)
        self.optimizer = torch.optim.Adam(params=self.policy_model.parameters(),
                                          lr=self.lr,
                                          weight_decay=self.weight_decay)

    def set_target_model(self, model):
        self.target_model = model.to(self.device)

    def __get_model__(self):
        neurons = 128
        return nn.Sequential(
            nn.Linear(self.state_dim, neurons),
            nn.ReLU(),
            nn.Linear(neurons, neurons),
            nn.ReLU(),
            nn.Linear(neurons, self.action_dim)
        )

    def __sample_batch__(self):
        """
        :return: ( [states], [next_states], [actions], [rewards], [is_done] )
        """
        states, next_states, actions, rewards, terminals = self.experience_buffer.sample_batch()
        return [
            torch.FloatTensor(states).to(self.device),
            torch.FloatTensor(next_states).to(self.device),
            torch.LongTensor(actions).to(self.device),
            torch.FloatTensor(rewards).to(self.device),
            torch.BoolTensor(terminals).to(self.device)
        ]

    def __fit_network__(self, q_eval, q_target):
        error = self.loss_function(q_eval, q_target)
        self.optimizer.zero_grad()
        error.backward()
        nn.utils.clip_grad_value_(self.policy_model.parameters(), clip_value=1)
        self.optimizer.step()
        return error.detach().cpu().item()


class AgentTrainer:
    def __init__(self,
                 agent: Agent,
                 reward_function=velocity_potentials,
                 episodes=400,
                 verbose=True):
        self.agent = agent
        self.episodes = episodes
        self.verbose = verbose
        self.reward_function = reward_function

    def train(self, path='trained_models/car_model.pt', render_step=None):
        rewards = np.zeros(self.episodes)
        for episode in np.arange(self.episodes):
            state = self.agent.env.reset()
            done = False
            episode_reward = 0

            while not done:
                action = self.agent.choose_eps_action(state)
                next_state, reward, done, _ = self.agent.env.step(action)
                episode_reward += reward

                if render_step is not None and episode % render_step == 0:
                    self.agent.env.render()

                self.agent.store_transition(state=state,
                                            next_state=next_state,
                                            action=action,
                                            terminal=done,
                                            reward=self.reward_function(state, next_state, reward))

                self.agent.learn(episode=episode)
                state = next_state

            rewards[episode] = episode_reward

            self.agent.eps_strategy.decrease()
            if self.verbose:
                print("game {:03d} | reward {}{:04f} | eps {:05f}".format(
                    episode + 1,
                    '-' if episode_reward < 0 else '+',
                    abs(episode_reward),
                    self.agent.eps_strategy.eps))

        self.agent.set_target_model(model=deepcopy(self.agent.policy_model))
        torch.save(self.agent.target_model, path)
        return rewards


class AgentEnsemble:
    def __init__(self,
                 n_agents,
                 n_games,
                 state_dim,
                 action_dim,
                 eps_decay,
                 reward_function=velocity_potentials,
                 verbose=False,
                 device=torch.device('cuda' if torch.cuda.is_available() else 'cpu')):
        self.n_agents = n_agents
        self.n_games = n_games
        self.device = device
        self.verbose = verbose
        self.trainers = [
            AgentTrainer(agent=Agent(state_dim=state_dim,
                                     action_dim=action_dim,
                                     eps_strategy=LinearEpsilonStrategy(decay=eps_decay),
                                     device=device),
                         episodes=n_games,
                         reward_function=reward_function,
                         verbose=verbose)
            for _ in range(n_agents)
        ]

    def train(self, path='trained_models/mountain-car/'):
        rewards = np.zeros((self.n_agents, self.n_games))
        for i, t in enumerate(self.trainers):
            if self.verbose:
                print('\n~~~~~~ Agent {} ~~~~~~\n'.format(i + 1))

            rewards[i] = t.train(path=f"{path}/car_agent{i}_{self.n_agents}.pt")

        return rewards.mean(axis=0)


if __name__ == '__main__':
    def train_car_agents(n_agents, n_games, eps_decay):
        agents_ensemble = AgentEnsemble(n_agents=n_agents,
                                        n_games=n_games,
                                        state_dim=2,
                                        action_dim=3,
                                        eps_decay=eps_decay,
                                        reward_function=velocity_potentials,
                                        verbose=True)

        rewards = agents_ensemble.train(path='trained_models/mountain-car/')

        plt.figure(figsize=(16, 9))
        plt.grid()
        plt.plot(range(n_games), rewards, color='green')
        plt.xlabel('game')
        plt.ylabel(f'mean reward of {n_agents} agent(s)')
        plt.savefig('plots/{}-car-agents-training'.format(n_agents))
        plt.show()


    train_car_agents(n_agents=1, n_games=200, eps_decay=1e-2)
