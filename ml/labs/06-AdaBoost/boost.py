import random
from enum import Enum

import matplotlib.patches as mpatches
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from matplotlib.colors import ListedColormap
from sklearn.metrics import accuracy_score
from sklearn.tree import DecisionTreeClassifier

#
#
# Constants and enums
INF = float('inf')


class DatasetName(Enum):
    GEYSER = 'geyser'
    CHIPS = 'chips'


def read_dataset(filename):
    data = pd.read_csv(filename)
    X = data.values[:, :-1]
    Y = np.vectorize(lambda x: 1 if x == 'P' else -1)(data.values[:, -1])
    indices = np.arange(Y.shape[0])

    return X[indices], Y, data.values[:, -1]


#
#
#
colors_for_class = {
    'N': 'bo',
    'P': 'ro'
}


#
#
# Stump AddaBoost algorithm implementation
class AdaBoost:
    def __init__(self, iterations=20, trees_count=20):
        self.trees_count = trees_count

        self.prediction_trees: [DecisionTreeClassifier] = []
        self.tree_vote_amounts: [np.float64] = []
        self.iterations = iterations

    def fit(self, train_x, train_y, iterations):
        #
        # Reset values
        self.iterations = iterations
        self.prediction_trees = []
        self.tree_vote_amounts = []

        n = len(train_y)
        eps = 1e-300
        #
        # Fitting pool of trees
        trees = get_random_trees(n=self.trees_count)
        for tree in trees:
            tree.fit(train_x, train_y)

        #
        # Init weights for dataset elements
        weights = np.array([1 / n for _ in range(n)])

        for i in range(self.iterations):
            error, tree = self.__get_least_error_tree__(weights=weights,
                                                        trees=trees,
                                                        train_x=train_x,
                                                        train_y=train_y)

            tree_vote_amount = 0.5 * np.log(1 / (error + eps) - 1)
            weights = self.__update_weights__(weights=weights,
                                              vote_amount=tree_vote_amount,
                                              tree=tree,
                                              train_x=train_x,
                                              train_y=train_y)

            self.tree_vote_amounts.append(tree_vote_amount)
            self.prediction_trees.append(tree)

    def predict(self, x) -> int:
        value = 0
        for i in range(self.iterations):
            value += self.prediction_trees[i].predict([x]) * self.tree_vote_amounts[i]

        class_mark = np.int(np.sign(value)[0])

        return class_mark

    def predict_vector(self, X):
        return list(map(self.predict, X))

    @staticmethod
    def __update_weights__(weights: np.array,
                           vote_amount: np.float64,
                           tree: DecisionTreeClassifier,
                           train_x: np.array,
                           train_y: np.array) -> np.array:

        predicted = tree.predict(X=train_x)
        powers = -vote_amount * predicted * train_y
        exps = np.exp(powers)
        n_weights = exps * weights
        normalize_coefficient = sum(n_weights)

        return n_weights / normalize_coefficient

    @staticmethod
    def __get_least_error_tree__(weights: np.array,
                                 trees: [DecisionTreeClassifier],
                                 train_x: np.array,
                                 train_y: np.array) -> (float, DecisionTreeClassifier, int):

        least_error, best_tree = INF, None

        for (i, tree) in enumerate(trees):
            predicted = tree.predict(X=train_x)
            error = sum(weights[predicted != train_y])

            if error < least_error:
                least_error = error
                best_tree = tree

        return least_error, best_tree


#
# Random trees generation block
def get_random_trees(n: int) -> [DecisionTreeClassifier]:
    criterion = random.choice(['gini', 'entropy'])

    return [DecisionTreeClassifier(criterion=criterion,
                                   splitter='random',
                                   max_depth=4)
            for _ in range(n)]


#
# Drawing
def draw(model: AdaBoost, train_x, train_y, step, title):
    plt.figure(figsize=(16, 9)).suptitle(title, fontsize=18)
    plt.grid(linestyle='--')

    step_x = step
    step_y = step
    x_min, y_min = np.amin(train_x, 0)
    x_max, y_max = np.amax(train_x, 0)

    x_min -= step_x
    x_max += step_x
    y_min -= step_y
    y_max += step_y

    xy, yx = np.meshgrid(np.arange(x_min, x_max, step_x),
                         np.arange(y_min, y_max, step_y))

    mesh_dots = np.c_[xy.ravel(), yx.ravel()]
    zz = np.apply_along_axis(lambda point: model.predict(point), 1, mesh_dots)
    zz = np.array(zz).reshape(xy.shape)

    second_class_x, second_class_y = train_x[train_y == 1].T
    first_class_x, first_class_y = train_x[train_y == -1].T

    plt.pcolormesh(xy, yx, zz, cmap=ListedColormap(['#FFAAAA', '#AAAAFF']), shading='auto')
    plt.scatter(first_class_x, first_class_y, color='red', s=20)
    plt.scatter(second_class_x, second_class_y, color='blue', s=20)

    plt.show()


# Show initial data
def make_initial_data_plot(dataset_x, class_marks):
    plt.figure(figsize=(16, 9))
    plt.grid(linestyle='--')

    patches = []

    for c in colors_for_class:
        patches.append(mpatches.Patch(color=colors_for_class[c][:-1], label=c))

    for (i, c) in enumerate(class_marks):
        plt.plot(dataset_x[i][0],
                 dataset_x[i][1],
                 colors_for_class[c])

    plt.legend(handles=patches)


#
#
# Processing datasets
def process_dataset(name: DatasetName):
    boost = AdaBoost(trees_count=150)

    dataset_name = name.value
    dataset_path = "datasets/{}.csv".format(dataset_name)

    dataset_x, dataset_y, class_marks = read_dataset(dataset_path)

    make_initial_data_plot(dataset_x=dataset_x, class_marks=class_marks)
    # plt.show()

    fib_array = [1, 2, 3, 5, 8, 13, 21, 34, 55]

    for it in fib_array:
        boost.fit(train_x=dataset_x, train_y=dataset_y, iterations=it)
        accuracy = accuracy_score(y_pred=boost.predict_vector(X=dataset_x),
                                  y_true=dataset_y)

        print("Iteration:= {:02d}  |  accuracy:= {:06f}".format(it, accuracy))

        # TODO: Uncomment for graphics plot.
        # draw(boost, train_x=dataset_x,
        #      train_y=dataset_y,
        #      step=0.1,
        #      title="Dataset:= {}  |  iterations:= {}".format(dataset_name, it))

# TODO: Uncomment for processing datasets.
# process_dataset(DatasetName.GEYSER)
# process_dataset(DatasetName.CHIPS)
