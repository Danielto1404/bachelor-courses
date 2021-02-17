import random

from sklearn import linear_model as lm
import numpy as np
from matplotlib import pyplot as plt


class Dataset:
    def __init__(self, X: np.array, y: np.array, n: int, features_cnt: int):
        self.X = X
        self.y = y
        self.n = n
        self.features_cnt = features_cnt


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


def smape_loss(predicted, actual, n) -> np.float:
    loss = np.float(0)
    for (pred_y, actual_y) in zip(predicted, actual):
        pred_abs_y, actual_abs_y = np.abs(pred_y), np.abs(actual_y)
        div = pred_abs_y + actual_abs_y
        if div != 0:
            loss += np.abs(pred_y - actual_y) / div

    return loss / n


def smape_partial_derivative(xi, y, predicted):
    abs_predicted = np.abs(predicted)
    abs_y = np.abs(y)
    abs_diff = np.abs(y - predicted)
    derivative = -xi * (y - predicted) / ((abs_predicted + abs_y) * abs_diff) \
                 - xi * predicted * abs_diff / (abs_predicted * np.square(abs_predicted + abs_y))
    return derivative


def smape_gradient(x, y, predicted):
    vector = np.fromiter(map(lambda xi: smape_partial_derivative(xi, y, predicted), x),
                         dtype=np.float)

    return vector / np.dot(vector, vector)


def least_squares_gradient(x, y, predicted):
    vector = np.fromiter(map(lambda xi: -2 * xi * (y - predicted), x),
                         dtype=np.float)

    return vector


def stochastic_gradient_descent(iterations, h, dataset: Dataset):
    w = np.fromiter(map(lambda _: 1 / dataset.n,
                        range(dataset.features_cnt)),
                    dtype=np.float)

    for _ in range(iterations):
        i = np.random.randint(dataset.n)
        x = dataset.X[i]
        y = dataset.y[i]
        grad = least_squares_gradient(x=x, y=y, predicted=np.dot(w, x))
        w -= h * (grad + 1 * w)

    return w


def save(weights):
    np.save("weights", weights)


def load() -> np.array:
    return np.load("weights.npy")


def cf():
    n, m = map(int, input().split())
    X = []
    y = []
    for _ in range(n):
        line = input()
        x = np.fromstring(line, sep=' ', dtype=np.float, count=m)
        X.append(np.insert(x, m, 1))  # add bias
        y.append(np.float(line.split()[-1]))

    train = Dataset(X=np.array(X), y=np.array(y), n=n, features_cnt=m + 1)

    w = stochastic_gradient_descent(iterations=4000, h=0.001, dataset=train)
    print(*w, sep='\n')
    print(*np.dot(train.X, w))


def fit(file_path):
    train, test = read_file(file_path)
    w = stochastic_gradient_descent(iterations=4000, h=0.01, dataset=train)
    # print(w)
    draw_graphic(test, w)
    # print_loss(dataset=train, w=w)
    # print_loss(dataset=test, w=w)


def print_loss(dataset, w):
    predicted = np.dot(dataset.X, w)
    print(smape_loss(predicted, dataset.y, dataset.n))


def draw_graphic(dataset, w):
    predicted = np.dot(dataset.X, w)
    actual = dataset.y
    xs = range(dataset.n)
    plt.plot(xs, actual, color='green')
    plt.plot(xs, predicted, color='red')
    plt.show()


def sklearn_process():
    train, test = read_file("data/0.40_0.65.txt")

    model = lm.LinearRegression()
    model.fit(train.X, train.y)
    xs = range(test.n)
    plt.plot(xs, test.y, color='red')
    plt.plot(xs, model.predict(test.X), color='green')
    plt.show()


# sklearn_process()
fit("data/0.60_0.73.txt")
# cf()
