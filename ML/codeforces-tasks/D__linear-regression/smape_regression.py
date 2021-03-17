import random
from copy import deepcopy

import numpy as np


class Dataset:
    def __init__(self, X: np.array, y: np.array, n: int, features_cnt: int):
        self.X = X
        self.y = y
        self.n = n
        self.features_cnt = features_cnt


class RidgeStochasticLinearRegression:
    def __init__(self, h, lambda_, gamma, iterations=2500):
        self.h = h
        self.lambda_ = lambda_
        self.gamma = gamma
        self.iterations = iterations
        self.w = None
        self.momentum = None

    def fit(self, dataset: Dataset):
        self.w = random_weights(n=dataset.n, features_cnt=dataset.features_cnt)
        self.momentum = deepcopy(self.w)

        for _ in np.arange(self.iterations):
            i = np.random.randint(dataset.n)
            x, y = dataset.X[i], dataset.y[i]

            gradient = self.smape_gradient(x=x, y=y, predicted=np.dot(self.w, x))

            self.momentum = self.gamma * self.momentum + (1 - self.gamma) * gradient
            self.w = self.w * (1 - self.h * self.lambda_) - self.h * self.momentum

    def fit_predict(self, train: Dataset, test: Dataset):
        self.fit(train)
        return self.predict(test)

    def predict(self, dataset: Dataset):
        return np.dot(dataset.X, self.w)

    def __str__(self):
        return '''
--- Ridge Stochastic Linear Regression ---
   h              : {}
   gamma          : {}
   lambda         : {}
'''.format(self.h, self.gamma, self.lambda_)

    @staticmethod
    def mse_gradient(x, y, predicted):
        error = predicted - y
        print(error)
        return np.array([xi * error for xi in x])

    @staticmethod
    def smape_gradient(x, y, predicted):
        def smape_partial_derivative(xi, yi, pred):
            abs_predicted = np.abs(pred)
            abs_y = np.abs(yi)
            diff = yi - pred
            abs_diff = np.abs(diff)

            if abs_diff * abs_predicted * abs_y == 0:
                return 0

            derivative = -xi * diff / (abs_diff * (abs_predicted + abs_y)) \
                         - xi * pred * abs_diff / (abs_predicted * (abs_predicted + abs_y) ** 2)

            return derivative

        return np.fromiter(
            map(lambda xi: smape_partial_derivative(xi, y, predicted), x),
            dtype=np.float)


def random_weights(n, features_cnt):
    k = 1 / 2 * n
    return np.random.uniform(-k, k, features_cnt)


def cf():
    n, m = map(int, input().split())
    X, y = [], []
    for _ in range(n):
        data = np.fromiter(input().split(), dtype=np.float)
        X.append(np.insert(data[:-1], m, 1))
        y.append(data[-1])

    dataset = Dataset(X=np.array(X), y=np.array(y), n=n, features_cnt=m + 1)

    model = RidgeStochasticLinearRegression(h=1e-2, lambda_=1e-1, gamma=3e-2, iterations=60_000)
    model.fit(dataset=dataset)
    print(*model.predict(dataset))


if __name__ == '__main__':
    cf()
