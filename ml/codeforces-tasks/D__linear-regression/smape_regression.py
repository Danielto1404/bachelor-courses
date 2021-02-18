import datetime
import sys
import random

import numpy as np


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
    def __init__(self, train: Dataset, test: Dataset, hs: np.array, lambdas: np.array):
        self.hs = hs
        self.lambdas = lambdas
        self.n_hs = len(hs)
        self.n_lambdas = len(lambdas)
        self.train = train
        self.test = test

    def get_models(self) -> []:
        scores, iteration = [], 0
        amount_of_it = self.n_hs * self.n_lambdas
        for h in self.hs:
            for lambda_ in self.lambdas:
                model = RidgeStochasticLinearRegression(h=h, lambda_=lambda_)
                model.fit(self.train)
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

    def fit(self, dataset: Dataset):

        def random_weight(n):
            divisor = random.randint(-2 * n, 2 * n)
            if divisor == 0:
                return random_weight(n)
            return 1 / divisor

        self.w = np.fromiter(map(lambda _: random_weight(dataset.n),
                                 range(dataset.features_cnt)),
                             dtype=np.float)

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
   h      : {}
   lambda : {}
'''.format(self.h, self.lambda_)

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


def smape_loss(predicted, actual, n) -> np.float:
    loss = np.float(0)
    for (pred_y, actual_y) in zip(predicted, actual):
        pred_abs_y, actual_abs_y = np.abs(pred_y), np.abs(actual_y)
        div = pred_abs_y + actual_abs_y
        if div != 0:
            loss += np.abs(pred_y - actual_y) / div

    return loss / n


def least_squares_gradient(x, y, predicted):
    return np.fromiter(map(lambda xi: -2 * xi * (y - predicted), x),
                       dtype=np.float)


def save(weights):
    np.save("weights", weights)


def load() -> np.array:
    return np.load("weights.npy")


def train_model(file_path):
    def read(file, n, features_cnt):
        X = []
        y = []

        for _ in range(n):
            line = file.readline()
            x = np.fromstring(line, sep=' ', dtype=np.float, count=features_cnt)
            X.append(np.insert(x, features_cnt, 1))  # add bias
            y.append(np.float(line.split()[-1]))

        return Dataset(X=np.array(X), y=np.array(y), n=n, features_cnt=features_cnt + 1)

    def read_file(path) -> (Dataset, Dataset):
        with open(path, 'r') as file:
            features_cnt = int(file.readline())
            train_cnt = int(file.readline())
            _train = read(file, train_cnt, features_cnt)
            test_cnt = int(file.readline())
            _test = read(file, test_cnt, features_cnt)

            return _train, _test

    train, test = read_file(file_path)

    hs = np.arange(1e-3, 1, 1e-2)
    lambdas = np.linspace(1e-1, 1, 1000)
    models = RidgeSmapeCV(train=train, test=test, hs=hs, lambdas=lambdas).get_models()
    m = min(models, key=lambda o: o[1])
    with open('results.txt'.format(file_path), 'a') as file:
        file.write('''        
{}
File name  : {}
SMAPE score: {}

==================================================='''.format(m[0], file_path, m[1]))

#
#
# def print_loss(dataset, w):
#     predicted = np.dot(dataset.X, w)
#     print(smape_loss(predicted, dataset.y, dataset.n))
#
#
# def draw_graphic(dataset, w):
#     predicted = np.dot(dataset.X, w)
#     actual = dataset.y
#     xs = range(dataset.n)
#     plt.plot(xs, actual, color='green')
#     plt.plot(xs, predicted, color='red')
#     plt.show()
#
#
# def sklearn_process():
#     train, test = read_file("data/0.40_0.65.txt")
#
#     model = lm.LinearRegression()
#     model.fit(train.X, train.y)
#     xs = range(test.n)
#     plt.plot(xs, test.y, color='red')
#     plt.plot(xs, model.predict(test.X), color='green')
#     plt.show()
#
#
# # sklearn_process()
# fit("data/0.60_0.73.txt")
# # cf()


if __name__ == '__main__':
    file_name = sys.argv[1]
    train_model(file_path=file_name)
