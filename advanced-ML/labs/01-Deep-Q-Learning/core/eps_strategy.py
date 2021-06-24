import numpy as np


class EpsilonStrategy:
    def __init__(self, start=1, decay=5e-3, min_eps=0.1):
        self.start = start
        self.eps = start
        self.decay = decay
        self.min_eps = min_eps

    def eps(self):
        return self.eps

    def check_random_prob(self):
        return np.random.random(1) < self.eps

    def decrease(self):
        raise NotImplementedError


class LinearEpsilonStrategy(EpsilonStrategy):
    def decrease(self):
        self.eps = max(self.eps - self.decay, self.min_eps)
        return self.eps


class ExpEpsilonStrategy(EpsilonStrategy):
    def decrease(self):
        self.eps = max(self.eps * self.decay, self.min_eps)
        return self.eps
