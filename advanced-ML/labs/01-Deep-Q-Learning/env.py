import collections

import cv2
import gym
import numpy as np


class FireResetEnv(gym.Wrapper):
    def __init__(self, env=None, terminal_on_life_loss=False, lives=5):
        super(FireResetEnv, self).__init__(env)
        self.terminal_on_life_loss = terminal_on_life_loss
        self.FIRE_ACTION = 1
        self.lives = lives
        self.initial_lives = lives
        assert env.unwrapped.get_action_meanings()[self.FIRE_ACTION] == 'FIRE'
        assert len(env.unwrapped.get_action_meanings()) >= 3

    def step(self, action):
        state, reward, done, info = self.env.step(action)
        lives = info.get('ale.lives', self.initial_lives)
        if lives != self.lives:
            self.lives = lives
            self.env.step(self.FIRE_ACTION)

        return state, reward, done or (lives != self.initial_lives and self.terminal_on_life_loss), info

    def reset(self):
        self.env.reset()
        obs, _, done, _ = self.env.step(self.FIRE_ACTION)
        if done:
            self.reset()

        way = np.random.choice([2, 3])
        for _ in range(np.random.randint(6)):
            obs, _, done, _ = self.step(way)
        if done:
            self.reset()
        return obs


class MaxAndSkipEnv(gym.Wrapper):
    def __init__(self, env=None, skip=4, use_for_pool=2):
        super(MaxAndSkipEnv, self).__init__(env)
        # most recent raw observations (for max pooling across time steps)
        self._obs_buffer = collections.deque(maxlen=use_for_pool)
        self._skip = skip

    def step(self, action):
        total_reward = 0.0
        done, info = None, None
        for _ in range(self._skip):
            obs, reward, done, info = self.env.step(action)
            self._obs_buffer.append(obs)
            total_reward += reward
            if done:
                break
        max_frame = np.max(np.stack(self._obs_buffer), axis=0)
        return max_frame, total_reward, done, info

    def reset(self):
        self._obs_buffer.clear()
        obs = self.env.reset()
        self._obs_buffer.append(obs)
        return obs


class ProcessFrame84(gym.ObservationWrapper):
    def __init__(self, env=None):
        super(ProcessFrame84, self).__init__(env)
        self.observation_space = gym.spaces.Box(low=0,
                                                high=255,
                                                shape=(84, 84, 1),
                                                dtype=np.uint8)

    def observation(self, obs):
        return ProcessFrame84.process(obs)

    @staticmethod
    def process(frame):
        if frame.size == 210 * 160 * 3:
            img = np.reshape(frame, [210, 160, 3]).astype(np.float32)
        elif frame.size == 250 * 160 * 3:
            img = np.reshape(frame, [250, 160, 3]).astype(np.float32)
        else:
            assert False, "Unknown resolution."
        img = img[:, :, 0] * 0.299 + img[:, :, 1] * 0.587 + img[:, :, 2] * 0.114
        resized_screen = cv2.resize(img, (84, 110), interpolation=cv2.INTER_AREA)
        x_t = resized_screen[18:102, :]
        x_t = np.reshape(x_t, [84, 84, 1])
        return x_t.astype(np.uint8)


class BufferWrapper(gym.ObservationWrapper):
    def __init__(self, env, frames, dtype=np.float32):
        super(BufferWrapper, self).__init__(env)
        self.dtype = dtype
        self.frames = frames
        self.buffer = None
        old_space = env.observation_space
        self.observation_space = gym.spaces.Box(old_space.low.repeat(frames, axis=0),
                                                old_space.high.repeat(frames, axis=0),
                                                dtype=dtype)

    def reset(self):
        self.buffer = np.zeros_like(self.observation_space.low, dtype=self.dtype)
        return self.observation(self.env.reset())

    def observation(self, observation):
        self.buffer[:-1] = self.buffer[1:]
        self.buffer[-1] = observation
        return self.buffer


class ImageToPyTorch(gym.ObservationWrapper):
    def __init__(self, env):
        super(ImageToPyTorch, self).__init__(env)
        height, width, frames = self.observation_space.shape
        self.observation_space = gym.spaces.Box(low=0.0,
                                                high=1.0,
                                                shape=(frames, height, width),
                                                dtype=np.float32)

    def observation(self, observation):
        return np.moveaxis(observation, 2, 0)


class ScaledFloatFrame(gym.ObservationWrapper):
    def observation(self, observation):
        return np.array(observation, dtype=np.float32) / 255.0


def make_env(env_name, terminal_on_life_loss=True):
    env = gym.make(env_name)
    env = MaxAndSkipEnv(env, skip=4, use_for_pool=2)
    env = FireResetEnv(env, terminal_on_life_loss=terminal_on_life_loss)
    env = ProcessFrame84(env)
    env = ImageToPyTorch(env)
    env = BufferWrapper(env, frames=4)
    env = ScaledFloatFrame(env)
    return env


class ActionWrapper:
    def action(self, a):
        return a


class BreakoutFireDropActionWrapper(ActionWrapper):
    def action(self, a):
        return 0 if a == 0 else a + 1


def ipython_test():
    env = make_env('BreakoutNoFrameskip-v4')
    env.reset()

    def step(a):
        env.render()
        print(env.step(a)[3])

    return step
