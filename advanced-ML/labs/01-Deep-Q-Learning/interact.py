import os
import time

import gym
import numpy as np
from gym.wrappers import Monitor
from tqdm import trange

from core.action_selector import load_selector, load_atari_selector
from env import make_env, ActionWrapper

os.environ['KMP_DUPLICATE_LIB_OK'] = 'True'

from atari import AtariCNN

#
dummy = AtariCNN((4, 84, 84), 4)


def record_play(model, env):
    env = Monitor(env, './video', force=True)
    total_reward = 0
    state = env.reset()

    while True:
        action = model.choose_action(state)
        next_state, reward, done, _ = env.step(action)

        env.render()
        time.sleep(0.03)

        total_reward += reward
        state = next_state

        if done:
            return total_reward


def play_games(model, games, env, render=False, action_wrapper=ActionWrapper()):
    rewards = np.zeros(games)
    for i in trange(games):
        state, game_reward = env.reset(), 0
        done = False

        while not done:
            if render:
                env.render()
                time.sleep(0.03)

            a = model.choose_action(state)
            a = action_wrapper.action(a)
            state, reward, done, _ = env.step(a)
            game_reward += reward

        rewards[i] = game_reward

    env.close()
    return rewards


def play_car(model_path, games=1, render=True):
    return play_games(model=load_selector(model_path),
                      games=games,
                      env=gym.make('MountainCar-v0'),
                      render=render)


def play_breakout(model_path, games=1, render=False, action_wrapper=ActionWrapper()):
    return play_games(model=load_atari_selector(model_path),
                      games=games,
                      env=make_env('BreakoutNoFrameskip-v4', terminal_on_life_loss=False),
                      render=render,
                      action_wrapper=action_wrapper)


def play_montezuma_revenge(model_path, games, render=False, action_wrapper=ActionWrapper()):
    play_games(model=load_atari_selector(model_path),
               games=games,
               env=make_env('MontezumaRevenge-v4', terminal_on_life_loss=False),
               render=render,
               action_wrapper=action_wrapper)


if __name__ == '__main__':
    t = play_breakout(model_path='trained_models/atari/breakout/breakout_70.pt',
                      games=3,
                      render=True,
                      action_wrapper=ActionWrapper())
    print(t)
