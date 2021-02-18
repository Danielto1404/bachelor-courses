import datetime
import random
from copy import deepcopy

import numpy as np
from matplotlib import pyplot as plt


def clear_screen():
    print(chr(27) + "[2J")


def get_rest_time_in_seconds(i, n):
    TIME_CONST_1000_ITERATIONS = 2.7
    return int(TIME_CONST_1000_ITERATIONS * (n - i) / 1000)


def print_rest_time(seconds_left):
    conversion = datetime.timedelta(seconds=seconds_left)
    print(conversion)


class Dataset:
    def __init__(self, X: np.array, y: np.array, n: int, features_cnt: int):
        self.X = X
        self.y = y
        self.n = n
        self.features_cnt = features_cnt


class RidgeSmapeCV:
    def __init__(self, train: Dataset, test: Dataset, hs, lambdas, initial_weight):
        self.hs = hs
        self.lambdas = lambdas
        self.n_hs = len(hs)
        self.n_lambdas = len(lambdas)
        self.initial_weight = initial_weight
        self.train = train
        self.test = test

    def get_models(self) -> []:
        scores, iteration = [], 0
        amount_of_it = self.n_hs * self.n_lambdas
        for h in self.hs:
            for lambda_ in self.lambdas:
                model = RidgeStochasticLinearRegression(h=h, lambda_=lambda_)
                model.fit(self.train, initial_weight=self.initial_weight)
                predicted = model.predict(self.test)
                score = smape_loss(predicted, self.test.y, self.test.n)
                scores.append((model, score))

                iteration += 1
                clear_screen()
                print_rest_time(get_rest_time_in_seconds(iteration, amount_of_it))
                print('''
====>>> iteration: {} / {}
        lambda   : {}
        h        : {}
        score    : {}
'''.format(iteration, amount_of_it, lambda_, h, score))

        return scores


class RidgeStochasticLinearRegression:
    def __init__(self, h, lambda_, iterations=2500):
        self.h = h
        self.lambda_ = lambda_
        self.iterations = iterations
        self.w = None
        self.initial_weights = None

    def fit(self, dataset: Dataset, initial_weight=None):

        if initial_weight is None:
            self.w = random_weights(n=dataset.n, features_cnt=dataset.features_cnt)
        else:
            self.w = deepcopy(initial_weight)

        self.initial_weights = deepcopy(self.w)

        for _ in range(self.iterations):
            i = np.random.randint(dataset.n)
            x = dataset.X[i]
            y = dataset.y[i]
            gradient = self.smape_gradient(x=x, y=y, predicted=np.dot(self.w, x))
            self.w = self.w * (1 - self.h * self.lambda_) - self.h * gradient

            if len(self.w[self.w is not None]) < dataset.features_cnt:
                break

    def fit_predict(self, train: Dataset, test: Dataset):
        self.fit(train)
        return self.predict(test)

    def predict(self, dataset: Dataset):
        return np.dot(dataset.X, self.w)

    def __str__(self):
        return '''
--- Ridge Stochastic Linear Regression ---
   h              : {}
   lambda         : {}
   initial weights: {}
'''.format(self.h, self.lambda_, self.initial_weights)

    @staticmethod
    def smape_gradient(x, y, predicted):
        def smape_partial_derivative(xi, yi, pred):
            abs_predicted = np.abs(pred)
            abs_y = np.abs(yi)
            diff = yi - pred
            abs_diff = np.abs(diff)
            derivative = -xi * diff / (abs_diff * (abs_predicted + abs_y)) \
                         - xi * pred * abs_diff / (abs_predicted * (abs_predicted + abs_y) ** 2)
            return derivative

        return np.fromiter(
            map(lambda xi: smape_partial_derivative(xi, y, predicted), x),
            dtype=np.float)


def random_weights(n, features_cnt):
    def random_weight():
        divisor = random.randint(-2 * n, 2 * n)
        if divisor == 0:
            return random_weight()
        return 1 / divisor

    return np.fromiter(map(lambda _: random_weight(),
                           range(features_cnt)),
                       dtype=np.float)


def smape_loss(predicted, actual, n) -> np.float:
    loss = np.float(0)
    for (pred_y, actual_y) in zip(predicted, actual):
        pred_abs_y, actual_abs_y = np.abs(pred_y), np.abs(actual_y)
        div = pred_abs_y + actual_abs_y
        if div != 0:
            loss += np.abs(pred_y - actual_y) / div

    return loss / n


def read(fl, n, features_cnt):
    X = []
    y = []

    for _ in range(n):
        line = fl.readline()
        x = np.fromstring(line, sep=' ', dtype=np.float, count=features_cnt)
        X.append(np.insert(x, features_cnt, 1))  # add bias
        y.append(np.float(line.split()[-1]))

    return Dataset(X=np.array(X), y=np.array(y), n=n, features_cnt=features_cnt + 1)


def read_file(path) -> (Dataset, Dataset):
    with open(path, 'r') as fl:
        features_cnt = int(fl.readline())
        train_cnt = int(fl.readline())
        _train = read(fl, train_cnt, features_cnt)
        test_cnt = int(fl.readline())
        _test = read(fl, test_cnt, features_cnt)

        return _train, _test


def train_model(file_path, initial_weight):
    train, test = read_file(file_path)

    hs = np.arange(1e-3, 1, 1e-2)
    lambdas = np.linspace(1e-1, 1, 300)
    models = RidgeSmapeCV(train=train,
                          test=test,
                          hs=hs,
                          lambdas=lambdas,
                          initial_weight=initial_weight
                          ).get_models()
    return min(models, key=lambda o: o[1])


def draw_graphic(predicted, actual):
    xs = range(len(predicted))
    plt.plot(xs, actual, color='green')
    plt.plot(xs, predicted, color='red')
    plt.show()


def graphics():
    train, test = read_file('data/0.62_0.80.txt')
    model = RidgeStochasticLinearRegression(h=0.83,
                                            lambda_=0.78,
                                            iterations=2500)
    model.fit(train)
    predicted = model.predict(test)

    print(smape_loss(predicted, test.y, test.n))


def cf():
    n, m = map(int, input().split())
    X, y = [], []
    for _ in range(n):
        data = np.fromiter(input().split(), dtype=np.float)
        X.append(np.insert(data[:-1], m, 1))
        y.append(data[-1])

    dataset = Dataset(X=np.array(X), y=np.array(y), n=n, features_cnt=m + 1)
    model = RidgeStochasticLinearRegression(h=0.83, lambda_=0.85, iterations=2500)
    model.fit(dataset)
    print(*model.w)


def save_random_weights():
    weights = []
    for _ in range(20):
        weights.append(random_weights(n=4096, features_cnt=129))

    np.save("data/initial_weights", np.array(weights))


def get_initial_weights():
    return np.load("data/initial_weights.npy")


def find_best_params(file_path):
    weights = get_initial_weights()
    m = min(
        map(lambda w: train_model(file_path, initial_weight=w),
            weights),
        key=lambda o: o[1])
    with open('data/results.txt'.format(file_path), 'a') as file:
        file.write('''
{}
File name  : {}
SMAPE score: {}

==================================================='''.format(m[0], file_path, m[1]))


if __name__ == '__main__':
    save_random_weights()
    find_best_params('data/0.40_0.65.txt')
    find_best_params('data/0.52_0.70.txt')
    find_best_params('data/0.62_0.80.txt')

    # cf()
