import matplotlib.patches as mpatches
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from matplotlib.colors import ListedColormap
from sklearn.metrics import f1_score
from sklearn.model_selection import KFold


#
#
# Reading data
def read_dataset(filename):
    data = pd.read_csv(filename)
    X = data.values[:, :-1]
    Y = np.vectorize(lambda x: 1 if x == 'P' else -1)(data.values[:, -1])
    indices = np.arange(Y.shape[0])
    return X[indices], Y[indices], data.values[:, -1]


dataset_features, dataset_classes, class_marks = read_dataset("datasets/chips.csv")
#
#
#
colors_for_class = {
    'N': 'bo',
    'P': 'ro'
}


#
#
# Show initial data
def initPlot():
    plt.xlabel('X')
    plt.ylabel('Y')

    patches = []

    for c in colors_for_class:
        patches.append(mpatches.Patch(color=colors_for_class[c][:-1], label=c))

    for (i, c) in enumerate(class_marks):
        plt.plot(dataset_features[i][0],
                 dataset_features[i][1],
                 colors_for_class[c])

    plt.legend(handles=patches)


initPlot()

plt.show()


#
#
# Kernel types
def kernel_linear(a, b):
    return np.dot(a, b)


def kernel_sigmoid(a, b, gamma=1, k0=0):
    return np.tanh(gamma * np.dot(a, b) + k0)


def kernel_rbf(a, b, gamma=0.5):
    return np.exp(-gamma * np.power(np.linalg.norm(a - b), 2))


def kernel_poly(a, b, gamma=1, k0=1, degree=3):
    return np.power(gamma * np.dot(a, b) + k0, degree)


#
#
# Generate kernels:
regularization_constants = [0.001, 0.1, 1.0, 10.0, 25.0, 50.0, 100.0, 250.0, 500.0]

linear_params = [{"name": "linear", "C": C} for C in regularization_constants]

rbf_params = [{"name": "rbf", "gamma": gamma, "C": C}
              for gamma in [0.10, 0.25, 0.5, 0.75, 1.0]
              for C in regularization_constants]

sigmoid_params = [{"name": "sigmoid", "gamma": gamma, "k0": k0, "C": C}
                  for gamma in [0.5, 0.75, 1.0]
                  for k0 in [0.0, 0.25, 0.5, 1.0]
                  for C in regularization_constants]

poly_params = [{"name": "poly", "gamma": gamma, "k0": k0, "C": C, "degree": degree}
               for gamma in [0.01, 0.1, 0.25, 0.5, 0.75, 1.0]
               for k0 in [0, 0.1, 0.25, 0.5, 0.75, 0.8, 1]
               for degree in range(3, 10)
               for C in regularization_constants]

all_params = np.array(linear_params + rbf_params + sigmoid_params + poly_params)


#
#
#
def kernel_matrix(kernel_func, A, B):
    n, *_ = A.shape
    m, *_ = B.shape
    func = lambda i, j: kernel_func(A[i], B[j])
    return np.fromfunction(np.vectorize(func), (n, m), dtype=int)


def get_kernel_func(name, gamma=1, k0=1, degree=3):
    if name == "linear":
        return lambda a, b: kernel_linear(a, b)
    elif name == "sigmoid":
        return lambda a, b: kernel_sigmoid(a, b, gamma, k0)
    elif name == "rbf":
        return lambda a, b: kernel_rbf(a, b, gamma)
    elif name == "poly":
        return lambda a, b: kernel_poly(a, b, gamma, k0, degree)
    else:
        return lambda a, b: kernel_linear(a, b)


#
#
#
# Main SVM class
class SVM:
    def __init__(self, kernel_function, features, values, C=1.0):
        n, *_ = values.shape
        self.N = n
        self.kernel_function = kernel_function
        self.values = values
        self.kernel_matrix = kernel_matrix(kernel_function, features, features)
        self.features = features
        self.C = C

        self.lambdas = np.zeros(n)
        self.shift_value = 0

        self.EPS = 1e-6
        self.MAX_ITERATIONS = 1000

    def predict(self, x):
        kernel = kernel_matrix(self.kernel_function, np.array([x]), self.features)
        res = int(np.sign(np.sum(self.lambdas * self.values * kernel[0]) + self.shift_value))
        return res if res != 0 else 1

    def get_random_int(self, i):
        res = np.random.randint(0, self.N - 1)
        return res if res < i else res + 1

    def calculate_U_V(self, i, j):
        a_i, a_j = self.lambdas[i], self.lambdas[j]
        if self.values[i] == self.values[j]:
            U = max(0, a_i + a_j - self.C)
            V = min(self.C, a_i + a_j)
        else:
            U = max(0, a_j - a_i)
            V = min(self.C, self.C + a_j - a_i)
        return U, V

    def calculate_errors(self, i):
        return np.dot(self.lambdas * self.values, self.kernel_matrix[i]) - self.values[i]

    def get_shift_value(self, i):
        return 1 / self.values[i] - np.dot(self.lambdas * self.values, self.kernel_matrix[i])

    def calculate_shift_value(self):
        self.shift_value = 0
        idx = None
        for i in range(self.N):
            if self.EPS < self.lambdas[i] and self.lambdas[i] + self.EPS < self.C:
                idx = i
                break
        if idx is None:
            cnt = 0
            for i in range(self.N):
                if self.EPS < self.lambdas[i]:
                    self.shift_value += self.get_shift_value(i)
                    cnt += 1
            if cnt != 0:
                self.shift_value /= cnt
        else:
            self.shift_value = self.get_shift_value(idx)

    def fit(self):
        indices = np.arange(self.N)
        for iter in range(self.MAX_ITERATIONS):
            print("Iteration:", iter)
            np.random.shuffle(indices)
            for i_fake in range(self.N):
                i = indices[i_fake]
                j = indices[self.get_random_int(i_fake)]
                E_i = self.calculate_errors(i)
                E_j = self.calculate_errors(j)
                prev_a_j = self.lambdas[j]
                U, V = self.calculate_U_V(i, j)
                if V - U < self.EPS:
                    continue
                eta = 2 * self.kernel_matrix[i][j] - (self.kernel_matrix[i][i] + self.kernel_matrix[j][j])
                if eta > -self.EPS:
                    continue
                possible_new_a_j = prev_a_j + self.values[j] * (E_j - E_i) / eta
                new_a_j = min(max(U, possible_new_a_j), V)
                if abs(new_a_j - prev_a_j) < self.EPS:
                    continue
                self.lambdas[j] = new_a_j
                self.lambdas[i] += self.values[i] * self.values[j] * (prev_a_j - new_a_j)
        self.calculate_shift_value()

    def get_indices(self):
        return np.where(np.logical_and(self.EPS < self.lambdas,
                                       self.lambdas + self.EPS < self.C))


#
#
def get_model(params, features, values):
    name = params["name"]
    C = params["C"]
    gamma = params.get("gamma", 1)
    k0 = params.get("k0", 1)
    degree = params.get("degree", 3)

    kernel_f = get_kernel_func(name, gamma, k0, degree)
    model = SVM(kernel_f, features, values, C)
    model.fit()
    return model


def get_cross_validation_score(params, features, values):
    kf = KFold(4)
    f_scores = []
    for train_index, test_index in kf.split(features):
        print("Train index:", train_index)
        X_train, y_train = features[train_index], values[train_index]
        X_test, y_test = features[test_index], values[test_index]
        model = get_model(params, X_train, y_train)
        y_pred = np.apply_along_axis(lambda x: model.predict(x), 1, X_test)
        f_scores.append(f1_score(y_test, y_pred))
    return np.average(np.array(f_scores))


def get_best_params(features, values):
    return all_params[
        np.argmax(
            np.array(
                list(
                    map(lambda params: get_cross_validation_score(params, features, values), all_params)
                )
            )
        )
    ]


#
#
#
# Drawing
def draw(model, features, values, step, title):
    plt.title(title)

    stepx = step
    stepy = step
    x_min, y_min = np.amin(features, 0)
    x_max, y_max = np.amax(features, 0)

    x_min -= stepx
    x_max += stepx
    y_min -= stepy
    y_max += stepy

    xy, yx = np.meshgrid(np.arange(x_min, x_max, stepx),
                         np.arange(y_min, y_max, stepy))

    mesh_dots = np.c_[xy.ravel(), yx.ravel()]
    zz = np.apply_along_axis(lambda point: model.predict(point), 1, mesh_dots)
    zz = np.array(zz).reshape(xy.shape)

    second_class_x, second_class_y = features[values == 1].T
    first_class_x, first_class_y = features[values == -1].T

    plt.pcolormesh(xy, yx, zz, cmap=ListedColormap(['#FFAAAA', '#AAAAFF']))
    plt.scatter(first_class_x, first_class_y, color='red', s=10)
    plt.scatter(second_class_x, second_class_y, color='blue', s=10)

    plt.show()


best_params_for_chips = [
    {'name': 'linear', 'C': 50},
    {'name': 'poly', 'C': 20, 'degree': 4},
    {'name': 'rbf', 'C': 10, 'gamma': 1},
    {'name': 'sigmoid', 'C': 50, 'gamma': 3},
]

best_params_for_geyser = [
    {'name': 'linear', 'C': 50},
    {'name': 'poly', 'C': 10, 'degree': 4},
    {'name': 'rbf', 'C': 10, 'gamma': 1},
    {'name': 'sigmoid', 'C': 650, 'gamma': 0.2},
]

X, Y, _ = read_dataset('datasets/chips.csv')

for params in best_params_for_chips:
    draw(get_model(params, X, Y), X, Y, 0.1, "Chips " + params['name'])

X, Y, _ = read_dataset('datasets/geyser.csv')

for params in best_params_for_geyser:
    draw(get_model(params, X, Y), X, Y, 0.1, "Geyser " + params['name'])
