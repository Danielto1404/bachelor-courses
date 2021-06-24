from collections import namedtuple, deque

import numpy as np

Transition = namedtuple(typename='Transition',
                        field_names=['state', 'next_state', 'action', 'reward', 'terminal'])


class ExperienceBuffer:
    def __init__(self, size=1e4, batch_size=128, start_sample_from=128):
        size = int(size)
        batch_size = int(batch_size)
        start_sample_from = int(start_sample_from)

        if batch_size > size:
            raise AssertionError('random sample size should be <= size')

        if batch_size > start_sample_from:
            raise AssertionError('start sample from should be >= batch_size')

        self.buffer = deque(maxlen=size)
        self.size = size
        self.batch_size = min(batch_size, self.size)
        self.batch_indices = np.arange(self.batch_size)
        self.start_sample_from = start_sample_from

    def __len__(self):
        return len(self.buffer)

    def clear(self):
        self.buffer.clear()

    def sample_batch(self):
        if not self.is_ready_for_sample():
            raise AssertionError('Buffer have not enough elements')

        indices = np.random.choice(len(self.buffer), self.batch_size, replace=False)
        states, next_states, actions, rewards, terminals = zip(*[self.buffer[i] for i in indices])
        return (np.array(states, dtype=np.float32),
                np.array(next_states, dtype=np.float32),
                np.array(actions, dtype=np.int64),
                np.array(rewards, dtype=np.float32),
                np.array(terminals, dtype=np.bool)
                )

    def store_transition(self, transition: Transition):
        self.buffer.append(transition)

    def is_ready_for_sample(self):
        return len(self.buffer) >= self.start_sample_from
