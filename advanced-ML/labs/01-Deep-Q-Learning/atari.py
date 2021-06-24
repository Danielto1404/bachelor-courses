import os

import numpy as np
import torch
import torch.nn.functional as F
import torch.optim
from torch import nn
from tqdm import trange

from agents import Agent
from core.eps_strategy import ExpEpsilonStrategy
from core.experience_buffer import ExperienceBuffer
from env import make_env

os.environ['KMP_DUPLICATE_LIB_OK'] = 'True'


class AtariCNN(nn.Module):
    def __init__(self, input_shape, n_actions):
        super(AtariCNN, self).__init__()

        frames, width, height = input_shape

        self.conv1 = nn.Conv2d(frames, 32, kernel_size=(8, 8), stride=(4, 4))
        self.conv2 = nn.Conv2d(32, 64, kernel_size=(4, 4), stride=(2, 2))
        self.conv3 = nn.Conv2d(64, 64, kernel_size=(3, 3), stride=(1, 1))

        self.dense1 = nn.Linear(self.__get_dense_shape__(input_shape), 512)
        self.dense2 = nn.Linear(512, n_actions)

        torch.nn.init.kaiming_normal_(self.conv1.weight)
        torch.nn.init.kaiming_normal_(self.conv2.weight)
        torch.nn.init.kaiming_normal_(self.conv3.weight)
        torch.nn.init.kaiming_normal_(self.dense1.weight)
        torch.nn.init.kaiming_normal_(self.dense2.weight)

    def forward(self, x):
        x = F.leaky_relu(self.conv1(x), 0.01)
        x = F.leaky_relu(self.conv2(x), 0.01)
        x = F.leaky_relu(self.conv3(x), 0.01)
        x = F.leaky_relu(self.dense1(x.view(x.shape[0], -1)), 0.01)
        x = self.dense2(x)
        return x

    def conv(self, x):
        x = self.conv1(x)
        x = self.conv2(x)
        x = self.conv3(x)
        return x

    def __get_dense_shape__(self, shape):
        o = self.conv(torch.zeros(1, *shape))
        return int(np.prod(o.size()))


class AtariAgent(Agent):
    def __get_model__(self):
        return AtariCNN(input_shape=self.state_dim, n_actions=self.action_dim)


class AtariAgentTrainer:
    def __init__(self,
                 agent: Agent,
                 episodes):
        self.agent = agent
        self.episodes = episodes

    def train(self, path='trained_models/atari'):
        rewards, test_rewards = [], []
        game_reward, best_mean_reward = 0, 0
        state = self.agent.env.reset()
        progress = trange(self.episodes, desc='epochs')

        for episode in progress:
            self.agent.eps_strategy.decrease()

            action = self.agent.choose_eps_action(state)
            next_state, reward, done, _ = self.agent.env.step(action)

            game_reward += reward

            self.agent.store_transition(state=state,
                                        next_state=next_state,
                                        action=action,
                                        terminal=done,
                                        reward=reward)

            test_reward = self.agent.learn(episode=episode)

            if test_reward is not None:
                test_rewards.append(test_reward)

            if done:
                rewards.append(game_reward)
                game_reward = 0

                mean_reward = np.mean(rewards[-100:])

                if mean_reward > best_mean_reward:
                    best_mean_reward = mean_reward
                    torch.save(self.agent.policy_model, '{}_best.pt'.format(path))

                progress_status = "last game reward: {} | games: {} | mean reward: {:02f} | epsilon: {:02f}".format(
                    rewards[-1], len(rewards), mean_reward, self.agent.eps_strategy.eps
                )

                progress.set_postfix_str(progress_status)

                state = self.agent.env.reset()
            else:
                state = next_state

        torch.save(self.agent.policy_model, '{}.pt'.format(path))
        return rewards


if __name__ == '__main__':
    env = make_env("BreakoutNoFrameskip-v4")

    atari_agent = AtariAgent(state_dim=(4, 84, 84),
                             action_dim=4,
                             lr=1e-4,
                             weight_decay=1e-4,
                             env=env,
                             atari_mode=True,
                             update_model_frequency=500,
                             loss_function=nn.SmoothL1Loss(),
                             experience_buffer=ExperienceBuffer(size=80_000,
                                                                batch_size=32,
                                                                start_sample_from=40_000),
                             eps_strategy=ExpEpsilonStrategy(decay=.999995,
                                                             min_eps=0.02))

    trainer = AtariAgentTrainer(agent=atari_agent, episodes=600_000)

    trainer.train(path='trained_models/atari/breakout/breakout-cpu.pt')
