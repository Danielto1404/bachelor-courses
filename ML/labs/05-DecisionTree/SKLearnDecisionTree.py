from collections import defaultdict
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score
from DT import get_file_path, plot_data_random_forest_accuracy_graphic

from matplotlib import pyplot as plt

import random
import pandas
import sys


#
# Reading data
def read_data(dataset_index: int) -> ([], [], [], []):
    train = pandas.read_csv(get_file_path(index=dataset_index, suffix='train'))
    test = pandas.read_csv(get_file_path(index=dataset_index, suffix='test'))

    train_x, train_y = train.iloc[:, :-1].to_numpy(), train.iloc[:, -1]
    test_x, test_y = test.iloc[:, :-1].to_numpy(), test.iloc[:, -1]

    return train_x, train_y, test_x, test_y


#
# Hyper params settings.
class TreeHyperParams:
    def __init__(self, criterion_value: str, splitter_value: str, depth_value: int):
        self.accuracy = 0
        self.criterion = criterion_value
        self.splitter = splitter_value
        self.depth = depth_value

    def set_accuracy(self, accuracy):
        self.accuracy = accuracy

    def __str__(self):
        return "Accuracy:= {:06f} | Depth:= {} | Criterion:= {} | Splitter:= {}".format(
            self.accuracy,
            self.depth,
            self.criterion,
            self.splitter)


criterion_values = ['entropy', 'gini']
splitter_values = ['random', 'best']
depth_range = range(1, 21)

tree_hyper_params = []
#
#
# Making set of hyper params
for __criterion__ in criterion_values:
    for __splitter__ in splitter_values:
        for __depth__ in depth_range:
            thp = TreeHyperParams(criterion_value=__criterion__,
                                  splitter_value=__splitter__,
                                  depth_value=__depth__)

            tree_hyper_params.append(thp)


#
#
# Function for find best hyper params, working to slow..
def find_best_param(dataset_index) -> TreeHyperParams:
    print("\n~~~~~~~~~~\nStart processing [Dataset <{:02d}>]:\n".format(dataset_index))

    train_x, train_y, test_x, test_y = read_data(dataset_index=dataset_index)

    best_hyper_params = None
    best_accuracy = 0

    for param in tree_hyper_params:
        tree = DecisionTreeClassifier(criterion=param.criterion,
                                      splitter=param.splitter,
                                      max_depth=param.depth)

        tree.fit(X=train_x, y=train_y)

        predicted = tree.predict(X=test_x)

        accuracy = accuracy_score(y_pred=predicted, y_true=test_y)

        if accuracy > best_accuracy:
            print("Accuracy:= {:08f} | Amount of leaves {}".format(accuracy, tree.get_n_leaves()))
            best_accuracy = accuracy
            best_hyper_params = param

    best_hyper_params.set_accuracy(best_accuracy)
    print()
    print("Best params:= {}".format(best_hyper_params))

    return best_hyper_params


#
# Function for finding best TreeHyperParam value for given comparator function.
def find_dataset(compare_params, params: []) -> (TreeHyperParams, int):
    best_hyper_params = params[0]
    dataset_index = 1

    for (idx, hyper_params) in zip(range(2, sys.maxsize), params[1:]):
        if compare_params(hyper_params, best_hyper_params):
            best_hyper_params = hyper_params
            dataset_index = idx

    return best_hyper_params, dataset_index


#
# Find graphic accuracy for given hyper params and variable depth
def get_graphic_values(params: TreeHyperParams, dataset_index: int) -> ([], [], []):
    #
    train_x, train_y, test_x, test_y = read_data(dataset_index=dataset_index)

    train_accuracy_value = []
    test_accuracy_value = []

    for depth_value in depth_range:
        tree = DecisionTreeClassifier(criterion=params.criterion, splitter=params.splitter, max_depth=depth_value)

        tree.fit(train_x, train_y)

        train_score = accuracy_score(y_pred=tree.predict(X=train_x), y_true=train_y)
        test_score = accuracy_score(y_pred=tree.predict(X=test_x), y_true=test_y)

        train_accuracy_value.append(train_score)
        test_accuracy_value.append(test_score)

    return depth_range, train_accuracy_value, test_accuracy_value


#
# Drawing graphic
def plot_graphic(xs, train_ys, test_ys, title):
    plt.figure(figsize=(16, 9))
    plt.title(title)
    plt.grid(linestyle='--')
    plt.plot(xs, train_ys, linestyle='-', marker='.', color='purple', label='Train dataset')
    plt.plot(xs, test_ys, linestyle='-', marker='.', color='green', label='Test dataset')
    plt.xlabel('Decision tree height')
    plt.ylabel('Accuracy')
    plt.legend()
    plt.show()


#
#
#
# Main process function
def process_min_max_tree_heights_block():
    print("\n\n----- Decision tree processing block -----")
    #
    # Finding best TreeHyperParams for each dataset.
    best_dataset_params = list(map(find_best_param, range(1, 22)))

    #
    # Finding min / max tree depth
    min_height_dataset_params, min_height_dataset_idx = find_dataset(lambda x, y: x.depth < y.depth,
                                                                     best_dataset_params)
    max_height_dataset_params, max_height_dataset_idx = find_dataset(lambda x, y: x.depth > y.depth,
                                                                     best_dataset_params)

    #
    # Plot graphics
    #
    #
    # Min optimal height
    plot_graphic(*get_graphic_values(params=min_height_dataset_params,
                                     dataset_index=min_height_dataset_idx),
                 title="[Dataset <{}>]   |   Min optimal height:= {}".format(min_height_dataset_idx,
                                                                             min_height_dataset_params.depth))
    #
    #
    # Max optimal height
    plot_graphic(*get_graphic_values(params=max_height_dataset_params,
                                     dataset_index=max_height_dataset_idx),
                 title="[Dataset <{}>]   |   Max optimal height:= {}".format(max_height_dataset_idx,
                                                                             max_height_dataset_params.depth))


#
#
#
#
# Random forest block
#
#
def get_random_tree() -> DecisionTreeClassifier:
    criterion = random.choice(criterion_values)
    splitter = random.choice(splitter_values)
    depth = random.choice(depth_range)

    return DecisionTreeClassifier(criterion=criterion, splitter=splitter, max_depth=depth)


def get_random_index(length):
    return random.randint(0, length - 1)


def get_random_data(train_x, train_y):
    n_train_x = []
    n_train_y = []

    length = len(train_x)

    for _ in train_x:
        random_idx = get_random_index(length=length)
        n_train_x.append(train_x[random_idx])
        n_train_y.append(train_y[random_idx])

    return n_train_x, n_train_y


class RandomForest:
    def __init__(self, number_of_trees):
        self.number_of_trees = number_of_trees
        self.trees = []

    def reset(self):
        self.trees = []

    def fit(self, train_x, train_y):
        for _ in range(self.number_of_trees):
            tree = get_random_tree()

            n_train_x, n_train_y = get_random_data(train_x=train_x, train_y=train_y)

            tree.fit(n_train_x, n_train_y)

            self.trees.append(tree)

    def predict(self, xs):
        vote_count_map_list = [defaultdict(lambda: 0) for _ in xs]

        for tree in self.trees:
            predicted_vector = tree.predict(xs)
            for (obj_index, prediction) in enumerate(predicted_vector):
                vote_count_map_list[obj_index][prediction] += 1

        return [max(vote_count_map, key=vote_count_map.get) for vote_count_map in vote_count_map_list]

    def accuracy(self, test_xs, y_true):
        predicted = self.predict(xs=test_xs)
        return accuracy_score(y_pred=predicted, y_true=y_true)


#
#
# Main process function for random forest algorithm
def process_random_forest_block(number_of_trees=20):
    print("\n----- Random forest processing block -----\n")
    print("Numbers of trees in forest:= {}\n".format(number_of_trees))

    accuracy_values = []

    for dataset_index in range(1, 22):
        forest = RandomForest(number_of_trees=number_of_trees)

        train_x, train_y, test_x, test_y = read_data(dataset_index=dataset_index)

        forest.fit(train_x=train_x, train_y=train_y)

        accuracy = forest.accuracy(test_xs=test_x, y_true=test_y)

        accuracy_values.append(accuracy)
        print("[Dataset <{:02d}>]   |   accuracy score:= {}".format(dataset_index, accuracy))

    #
    # Plot graphic
    plot_data_random_forest_accuracy_graphic(data_indexes=range(1, 22), accuracy_values=accuracy_values)


process_min_max_tree_heights_block()
process_random_forest_block(number_of_trees=50)

#
#
#
#
#
#
#
# Log:
#
# ----- Decision tree processing block -----
#
# ~~~~~~~~~~
# Start processing [Dataset <01>]:
#
# Accuracy:= 0.396813 | Amount of leaves 2
# Accuracy:= 0.401182 | Amount of leaves 4
# Accuracy:= 0.496016 | Amount of leaves 8
# Accuracy:= 0.528656 | Amount of leaves 16
# Accuracy:= 0.745053 | Amount of leaves 31
# Accuracy:= 0.763043 | Amount of leaves 59
# Accuracy:= 0.905166 | Amount of leaves 152
# Accuracy:= 0.999743 | Amount of leaves 8
#
# Best params:= Accuracy:= 0.999743 | Depth:= 3 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <02>]:
#
# Accuracy:= 0.226718 | Amount of leaves 2
# Accuracy:= 0.306870 | Amount of leaves 16
# Accuracy:= 0.364122 | Amount of leaves 127
# Accuracy:= 0.428753 | Amount of leaves 8
# Accuracy:= 0.505852 | Amount of leaves 16
# Accuracy:= 0.548601 | Amount of leaves 32
# Accuracy:= 0.617812 | Amount of leaves 64
# Accuracy:= 0.658524 | Amount of leaves 127
# Accuracy:= 0.684224 | Amount of leaves 244
# Accuracy:= 0.698473 | Amount of leaves 411
#
# Best params:= Accuracy:= 0.698473 | Depth:= 9 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <03>]:
#
# Accuracy:= 0.908390 | Amount of leaves 2
# Accuracy:= 0.964802 | Amount of leaves 4
# Accuracy:= 0.972517 | Amount of leaves 17
# Accuracy:= 0.979749 | Amount of leaves 56
# Accuracy:= 1.000000 | Amount of leaves 2
#
# Best params:= Accuracy:= 1.000000 | Depth:= 1 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <04>]:
#
# Accuracy:= 0.636522 | Amount of leaves 2
# Accuracy:= 0.656696 | Amount of leaves 4
# Accuracy:= 0.740522 | Amount of leaves 8
# Accuracy:= 0.832000 | Amount of leaves 73
# Accuracy:= 0.851826 | Amount of leaves 169
# Accuracy:= 0.852522 | Amount of leaves 4
# Accuracy:= 0.877217 | Amount of leaves 8
# Accuracy:= 0.963478 | Amount of leaves 16
# Accuracy:= 0.995130 | Amount of leaves 30
#
# Best params:= Accuracy:= 0.995130 | Depth:= 5 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <05>]:
#
# Accuracy:= 0.995671 | Amount of leaves 2
#
# Best params:= Accuracy:= 0.995671 | Depth:= 1 | Criterion:= entropy | Splitter:= random
#
# ~~~~~~~~~~
# Start processing [Dataset <06>]:
#
# Accuracy:= 0.590508 | Amount of leaves 2
# Accuracy:= 0.609272 | Amount of leaves 4
# Accuracy:= 0.660596 | Amount of leaves 7
# Accuracy:= 0.800221 | Amount of leaves 13
# Accuracy:= 0.860927 | Amount of leaves 22
# Accuracy:= 0.866998 | Amount of leaves 59
# Accuracy:= 0.871965 | Amount of leaves 103
# Accuracy:= 0.919426 | Amount of leaves 140
# Accuracy:= 0.927152 | Amount of leaves 144
# Accuracy:= 0.962472 | Amount of leaves 4
# Accuracy:= 0.998896 | Amount of leaves 8
#
# Best params:= Accuracy:= 0.998896 | Depth:= 3 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <07>]:
#
# Accuracy:= 0.385116 | Amount of leaves 2
# Accuracy:= 0.406047 | Amount of leaves 4
# Accuracy:= 0.407442 | Amount of leaves 8
# Accuracy:= 0.513953 | Amount of leaves 16
# Accuracy:= 0.557674 | Amount of leaves 26
# Accuracy:= 0.622326 | Amount of leaves 48
# Accuracy:= 0.790233 | Amount of leaves 84
# Accuracy:= 0.803256 | Amount of leaves 132
# Accuracy:= 0.864651 | Amount of leaves 144
# Accuracy:= 0.996744 | Amount of leaves 8
#
# Best params:= Accuracy:= 0.996744 | Depth:= 3 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <08>]:
#
# Accuracy:= 0.970894 | Amount of leaves 2
# Accuracy:= 0.972973 | Amount of leaves 24
# Accuracy:= 0.997921 | Amount of leaves 4
#
# Best params:= Accuracy:= 0.997921 | Depth:= 2 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <09>]:
#
# Accuracy:= 0.290196 | Amount of leaves 2
# Accuracy:= 0.366667 | Amount of leaves 4
# Accuracy:= 0.386275 | Amount of leaves 16
# Accuracy:= 0.576471 | Amount of leaves 30
# Accuracy:= 0.756863 | Amount of leaves 8
# Accuracy:= 0.805882 | Amount of leaves 16
# Accuracy:= 0.843137 | Amount of leaves 32
#
# Best params:= Accuracy:= 0.843137 | Depth:= 5 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <10>]:
#
# Accuracy:= 0.266801 | Amount of leaves 2
# Accuracy:= 0.494165 | Amount of leaves 4
# Accuracy:= 0.524748 | Amount of leaves 16
# Accuracy:= 0.646278 | Amount of leaves 32
# Accuracy:= 0.686922 | Amount of leaves 46
# Accuracy:= 0.828571 | Amount of leaves 95
# Accuracy:= 0.830986 | Amount of leaves 409
# Accuracy:= 0.853521 | Amount of leaves 457
# Accuracy:= 0.993561 | Amount of leaves 8
# Accuracy:= 0.997988 | Amount of leaves 16
#
# Best params:= Accuracy:= 0.997988 | Depth:= 4 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <11>]:
#
# Accuracy:= 0.782696 | Amount of leaves 2
# Accuracy:= 0.787123 | Amount of leaves 4
# Accuracy:= 0.788330 | Amount of leaves 4
# Accuracy:= 0.798793 | Amount of leaves 15
# Accuracy:= 0.933199 | Amount of leaves 31
# Accuracy:= 0.989537 | Amount of leaves 47
# Accuracy:= 0.999195 | Amount of leaves 2
#
# Best params:= Accuracy:= 0.999195 | Depth:= 1 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <12>]:
#
# Accuracy:= 0.399102 | Amount of leaves 2
# Accuracy:= 0.426887 | Amount of leaves 8
# Accuracy:= 0.528487 | Amount of leaves 31
# Accuracy:= 0.561886 | Amount of leaves 54
# Accuracy:= 0.595285 | Amount of leaves 109
# Accuracy:= 0.677800 | Amount of leaves 179
# Accuracy:= 0.720180 | Amount of leaves 754
# Accuracy:= 0.736739 | Amount of leaves 8
# Accuracy:= 0.779119 | Amount of leaves 16
# Accuracy:= 0.834129 | Amount of leaves 32
# Accuracy:= 0.867808 | Amount of leaves 63
# Accuracy:= 0.873141 | Amount of leaves 117
# Accuracy:= 0.874263 | Amount of leaves 183
#
# Best params:= Accuracy:= 0.874263 | Depth:= 8 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <13>]:
#
# Accuracy:= 0.311009 | Amount of leaves 2
# Accuracy:= 0.329358 | Amount of leaves 8
# Accuracy:= 0.333028 | Amount of leaves 16
# Accuracy:= 0.384404 | Amount of leaves 30
# Accuracy:= 0.392661 | Amount of leaves 105
# Accuracy:= 0.411009 | Amount of leaves 311
# Accuracy:= 0.499083 | Amount of leaves 16
# Accuracy:= 0.614679 | Amount of leaves 31
# Accuracy:= 0.641284 | Amount of leaves 61
# Accuracy:= 0.646789 | Amount of leaves 108
#
# Best params:= Accuracy:= 0.646789 | Depth:= 7 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <14>]:
#
# Accuracy:= 0.447704 | Amount of leaves 2
# Accuracy:= 0.553068 | Amount of leaves 8
# Accuracy:= 0.576997 | Amount of leaves 32
# Accuracy:= 0.604786 | Amount of leaves 60
# Accuracy:= 0.692783 | Amount of leaves 166
# Accuracy:= 0.694327 | Amount of leaves 250
# Accuracy:= 0.735623 | Amount of leaves 433
# Accuracy:= 0.765728 | Amount of leaves 476
# Accuracy:= 0.850251 | Amount of leaves 8
# Accuracy:= 0.941335 | Amount of leaves 16
# Accuracy:= 0.990351 | Amount of leaves 31
#
# Best params:= Accuracy:= 0.990351 | Depth:= 5 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <15>]:
#
# Accuracy:= 0.950150 | Amount of leaves 2
# Accuracy:= 0.954354 | Amount of leaves 8
# Accuracy:= 0.971171 | Amount of leaves 31
# Accuracy:= 0.976577 | Amount of leaves 52
# Accuracy:= 1.000000 | Amount of leaves 2
#
# Best params:= Accuracy:= 1.000000 | Depth:= 1 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <16>]:
#
# Accuracy:= 0.994393 | Amount of leaves 2
# Accuracy:= 0.995950 | Amount of leaves 31
# Accuracy:= 0.997196 | Amount of leaves 48
# Accuracy:= 1.000000 | Amount of leaves 2
#
# Best params:= Accuracy:= 1.000000 | Depth:= 1 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <17>]:
#
# Accuracy:= 0.226689 | Amount of leaves 2
# Accuracy:= 0.256281 | Amount of leaves 4
# Accuracy:= 0.270240 | Amount of leaves 8
# Accuracy:= 0.308208 | Amount of leaves 16
# Accuracy:= 0.365717 | Amount of leaves 32
# Accuracy:= 0.407594 | Amount of leaves 114
# Accuracy:= 0.510329 | Amount of leaves 186
# Accuracy:= 0.520380 | Amount of leaves 8
# Accuracy:= 0.677834 | Amount of leaves 16
# Accuracy:= 0.786153 | Amount of leaves 32
# Accuracy:= 0.825796 | Amount of leaves 61
# Accuracy:= 0.846454 | Amount of leaves 110
#
# Best params:= Accuracy:= 0.846454 | Depth:= 7 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <18>]:
#
# Accuracy:= 0.592703 | Amount of leaves 2
# Accuracy:= 0.689501 | Amount of leaves 16
# Accuracy:= 0.703649 | Amount of leaves 57
# Accuracy:= 0.717051 | Amount of leaves 83
# Accuracy:= 0.738645 | Amount of leaves 237
# Accuracy:= 0.811616 | Amount of leaves 8
# Accuracy:= 0.938943 | Amount of leaves 16
# Accuracy:= 0.942666 | Amount of leaves 30
#
# Best params:= Accuracy:= 0.942666 | Depth:= 5 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <19>]:
#
# Accuracy:= 0.231808 | Amount of leaves 2
# Accuracy:= 0.305326 | Amount of leaves 4
# Accuracy:= 0.408102 | Amount of leaves 8
# Accuracy:= 0.486122 | Amount of leaves 32
# Accuracy:= 0.547637 | Amount of leaves 116
# Accuracy:= 0.565641 | Amount of leaves 531
# Accuracy:= 0.609152 | Amount of leaves 8
# Accuracy:= 0.665416 | Amount of leaves 16
# Accuracy:= 0.777194 | Amount of leaves 32
# Accuracy:= 0.818455 | Amount of leaves 62
# Accuracy:= 0.834209 | Amount of leaves 107
#
# Best params:= Accuracy:= 0.834209 | Depth:= 7 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <20>]:
#
# Accuracy:= 0.501585 | Amount of leaves 2
# Accuracy:= 0.602615 | Amount of leaves 8
# Accuracy:= 0.694532 | Amount of leaves 62
# Accuracy:= 0.810222 | Amount of leaves 181
# Accuracy:= 0.860143 | Amount of leaves 180
# Accuracy:= 0.898574 | Amount of leaves 16
# Accuracy:= 0.942155 | Amount of leaves 30
# Accuracy:= 0.967512 | Amount of leaves 52
# Accuracy:= 0.967908 | Amount of leaves 79
#
# Best params:= Accuracy:= 0.967908 | Depth:= 7 | Criterion:= entropy | Splitter:= best
#
# ~~~~~~~~~~
# Start processing [Dataset <21>]:
#
# Accuracy:= 0.270133 | Amount of leaves 2
# Accuracy:= 0.311351 | Amount of leaves 8
# Accuracy:= 0.328472 | Amount of leaves 16
# Accuracy:= 0.383006 | Amount of leaves 32
# Accuracy:= 0.407736 | Amount of leaves 179
# Accuracy:= 0.465441 | Amount of leaves 291
# Accuracy:= 0.473050 | Amount of leaves 660
# Accuracy:= 0.511097 | Amount of leaves 763
# Accuracy:= 0.554851 | Amount of leaves 16
# Accuracy:= 0.605580 | Amount of leaves 32
# Accuracy:= 0.707039 | Amount of leaves 63
# Accuracy:= 0.786303 | Amount of leaves 115
# Accuracy:= 0.799620 | Amount of leaves 186
# Accuracy:= 0.809765 | Amount of leaves 243
#
# Best params:= Accuracy:= 0.809765 | Depth:= 9 | Criterion:= entropy | Splitter:= best
#
# ----- Random forest processing block -----
#
# Numbers of trees in forest:= 50
#
# [Dataset <01>]   |   accuracy score:= 0.9997429966589566
# [Dataset <02>]   |   accuracy score:= 0.7221374045801526
# [Dataset <03>]   |   accuracy score:= 1.0
# [Dataset <04>]   |   accuracy score:= 0.9965217391304347
# [Dataset <05>]   |   accuracy score:= 0.9956709956709957
# [Dataset <06>]   |   accuracy score:= 0.9988962472406181
# [Dataset <07>]   |   accuracy score:= 0.9967441860465116
# [Dataset <08>]   |   accuracy score:= 0.9916839916839917
# [Dataset <09>]   |   accuracy score:= 0.8215686274509804
# [Dataset <10>]   |   accuracy score:= 0.9979879275653923
# [Dataset <11>]   |   accuracy score:= 0.999195171026157
# [Dataset <12>]   |   accuracy score:= 0.8787538591074937
# [Dataset <13>]   |   accuracy score:= 0.7660550458715596
# [Dataset <14>]   |   accuracy score:= 0.994596680818217
# [Dataset <15>]   |   accuracy score:= 1.0
# [Dataset <16>]   |   accuracy score:= 1.0
# [Dataset <17>]   |   accuracy score:= 0.8598548297040759
# [Dataset <18>]   |   accuracy score:= 0.9337304542069993
# [Dataset <19>]   |   accuracy score:= 0.8477119279819955
# [Dataset <20>]   |   accuracy score:= 0.9805863708399366
# [Dataset <21>]   |   accuracy score:= 0.8218135700697526
