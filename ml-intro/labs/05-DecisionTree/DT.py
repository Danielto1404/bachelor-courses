import copy
import random

import pandas as pd
import matplotlib.pyplot as plt

from collections import defaultdict
from enum import Enum
from math import log2
from sklearn.metrics import accuracy_score

inf = float('inf')
minus_inf = -inf


def get_file_path(index, suffix):
    return "DT_csv/{:02d}_{}.csv".format(index, suffix)


#
#
# Enum class for stopping tree
class StopCondition(Enum):
    EPS_STOP = 0
    MAX_HEIGHT_STOP = 1
    ONE_BRANCH_STOP = 2


#
#
# Dataset representation class
class DatasetObject:
    def __init__(self, file_index):
        train_frame = pd.read_csv(get_file_path(index=file_index, suffix='train'))
        test_frame = pd.read_csv(get_file_path(index=file_index, suffix='test'))

        self.train_object_marks = list(train_frame.iloc[:, -1])
        self.train_objects_length = len(self.train_object_marks)
        self.train_indexed_objects = list(zip(train_frame.iloc[:, :-1].to_numpy(),
                                              range(self.train_objects_length)))
        self.train_features_count = len(self.train_indexed_objects[0][0])

        self.test_object_marks = list(test_frame.iloc[:, -1])
        self.test_objects_length = len(self.test_object_marks)
        self.test_indexed_objects = list(zip(test_frame.iloc[:, :-1].to_numpy(),
                                             range(self.test_objects_length)))

    #
    #
    # Train helper functions
    def train_index_range(self):
        return range(self.train_objects_length)

    def train_feature_index_range(self):
        return range(self.train_features_count)

    #
    #
    # Helper function for accessing object features for given index
    def get_train_features(self, index):
        return self.train_indexed_objects[index][0]

    #
    #
    # Helper function for accessing object mark for given index
    def get_train_mark(self, index):
        return self.train_object_marks[index]

    def get_test_mark(self, index):
        return self.test_object_marks[index]


def count_classes(indexed_objects, dataset: DatasetObject):
    class_cnt = defaultdict(lambda: 0)
    for obj in indexed_objects:
        object_index = obj[1]
        clazz = dataset.get_train_mark(object_index)
        class_cnt[clazz] += 1

    return class_cnt


def get_most_common_class(indexed_objects, dataset: DatasetObject):
    class_cnt = count_classes(indexed_objects=indexed_objects, dataset=dataset)
    return max(class_cnt, key=class_cnt.get)


def extract_classes(indexed_objects, dataset: DatasetObject):
    return [dataset.get_train_mark(obj[1]) for obj in indexed_objects]


def calculate_entropy(dataset: DatasetObject, indexed_objects):
    class_cnt = count_classes(indexed_objects=indexed_objects, dataset=dataset)

    if len(indexed_objects) == 0:
        return 0

    class_probabilities = [class_cnt[c] / len(indexed_objects) for c in class_cnt]

    entropy = -sum(p * log2(p) for p in class_probabilities)
    return entropy


def delta_entropy_for_split(left_objects, right_objects, dataset: DatasetObject, eps=1e-5) -> (float, bool, bool):
    left_len = len(left_objects)
    right_len = len(right_objects)

    left_entropy = calculate_entropy(dataset=dataset, indexed_objects=left_objects)
    right_entropy = calculate_entropy(dataset=dataset, indexed_objects=right_objects)

    if (left_len + right_len) == 0:
        return 0

    return (
        -(left_entropy * left_len + right_entropy * right_len) / (left_len + right_len)
        , left_entropy < eps
        , right_entropy < eps
    )


def calculate_avg_feature(feature_index, indexed_objects):
    if len(indexed_objects) == 0:
        return 0

    return sum(obj[0][feature_index] for obj in indexed_objects) / len(indexed_objects)


#
#
# Tree node class
class Node:
    def __init__(self, indexed_objects, height=1):
        self.left = None
        self.right = None
        self.predicate_index = None
        self.predicate_value = None
        self.isLeaf = False
        self.class_mark = None
        self.height = height
        self.indexed_objects = indexed_objects

    def set_left_subtree(self, left):
        self.left = left

    def set_right_subtree(self, right):
        self.right = right

    def set_predicate_index(self, index):
        self.predicate_index = index

    def set_predicate_value(self, value):
        self.predicate_value = value


#
#
# Decision tree classifier class.
class DecisionTree:
    def __init__(self, max_height=inf, max_samples_leaf=1):
        self.root = None
        self.__height__ = None
        self.max_height = max_height
        self.max_samples_leaf = max_samples_leaf

    #
    # Private height function
    def __inner_height__(self, node: Node):

        if node is None:
            return 0

        if node.isLeaf:
            return 1

        return 1 + max(self.__inner_height__(node.left), self.__inner_height__(node.right))

    #
    # Public height function
    def height(self):
        if self.__height__ is None:
            self.__height__ = self.__inner_height__(self.root)

        return self.__height__

    #
    # Public recursive predict function
    def predict(self, features):
        return self.__predict_walk__(node=self.root, parent=self.root, features=features)

    #
    # Public vector data predict function
    def predict_vector(self, objects):
        return [self.predict(obj[0]) for obj in objects]

    #
    # Private recursive predict function
    def __predict_walk__(self, node: Node, parent: Node, features):
        if node is None:
            return parent.class_mark

        if node.isLeaf:
            return node.class_mark

        if features[node.predicate_index] <= node.predicate_value:
            return self.__predict_walk__(node=node.left, parent=node, features=features)
        else:
            return self.__predict_walk__(node=node.right, parent=node, features=features)

    #
    # Public fit function
    def fit(self, dataset: DatasetObject):
        self.root = Node(indexed_objects=dataset.train_indexed_objects)
        self.__height__ = None
        self.__fit_node__(node=self.root, dataset=dataset)

    #
    # Private fit function
    def __fit_node__(self, node: Node, dataset: DatasetObject):

        feature_index, avg_value, left_subtree_objects, right_subtree_objects, isLeftLeaf, isRightLeaf = \
            self.find_best_feature_partition(
                indexed_objects=node.indexed_objects,
                dataset=dataset)

        node.set_predicate_index(feature_index)
        node.set_predicate_value(avg_value)

        node.class_mark = get_most_common_class(indexed_objects=node.indexed_objects, dataset=dataset)
        #
        #
        # TODO: Stop condition needs update for fixed max_height
        # TODO: Make a constraint for cut-off small amount of data in leaf.
        #
        # Stop building conditions
        if node.height == self.max_height:
            return

        if len(node.indexed_objects) <= self.max_samples_leaf:
            return

        #
        # Continue building conditions
        if not isLeftLeaf:
            left_node = Node(indexed_objects=left_subtree_objects, height=node.height + 1)
            node.set_left_subtree(left=left_node)
            self.__fit_node__(node=node.left, dataset=dataset)

        if not isRightLeaf:
            right_node = Node(indexed_objects=right_subtree_objects, height=node.height + 1)
            node.set_right_subtree(right=right_node)
            self.__fit_node__(node=node.right, dataset=dataset)

        else:
            node.isLeaf = True

    @staticmethod
    def get_avg_features(indexed_objects, dataset: DatasetObject) -> (int, float, [], [], bool, bool):
        return [calculate_avg_feature(feature_index=index, indexed_objects=indexed_objects)
                for index in dataset.train_feature_index_range()]

    @staticmethod
    def get_is_leaf_value(value: bool, continue_grow: bool = False) -> bool:
        # TODO: Uncomment code below if you want tree to grow as big as possible.
        # if continue_grow:
        #     return False

        return value

    def find_best_feature_partition(self, indexed_objects, dataset: DatasetObject):
        least_entropy = minus_inf
        best_feature_index = -1

        leftObjects = []
        rightObjects = []

        isLeftLeaf = False
        isRightLeaf = False

        avg_features = self.get_avg_features(indexed_objects=indexed_objects, dataset=dataset)

        for feature_index in dataset.train_feature_index_range():
            left = []
            right = []

            for obj in indexed_objects:
                if obj[0][feature_index] <= avg_features[feature_index]:
                    left.append(obj)
                else:
                    right.append(obj)

            cur_entropy, _isLeftLeaf, _isRightLeaf = delta_entropy_for_split(left_objects=left,
                                                                             right_objects=right,
                                                                             dataset=dataset)
            if cur_entropy > least_entropy:
                least_entropy = cur_entropy
                best_feature_index = feature_index
                isLeftLeaf = _isLeftLeaf
                isRightLeaf = _isRightLeaf
                leftObjects = left
                rightObjects = right

                # print(
                #     "Left objs classes:= {}\n"
                #     "Right objs classes:= {}\n"
                #     "isLeft:= {}, isRight:= {}, index:= {}, entropy:= {}\n".format(
                #         extract_classes(indexed_objects=leftObjects,
                #                         dataset=dataset),
                #         extract_classes(indexed_objects=rightObjects,
                #                         dataset=dataset),
                #         isLeftLeaf,
                #         isRightLeaf,
                #         feature_index,
                #         least_entropy))

        return (
            best_feature_index,
            avg_features[best_feature_index],
            leftObjects,
            rightObjects,
            self.get_is_leaf_value(value=isLeftLeaf, continue_grow=True),  # TODO: @See get_is_leaf_value(bool, bool)
            self.get_is_leaf_value(value=isRightLeaf, continue_grow=True)
        )


#
#
#
# Random forest class object
class RandomForest:
    def __init__(self, number_of_trees):
        self.number_of_trees = number_of_trees
        self.trees = []

    def fit(self, dataset: DatasetObject):
        self.trees = []
        for tree_idx in range(self.number_of_trees):
            fit_dataset = copy.deepcopy(dataset)

            tree = DecisionTree(max_samples_leaf=25)

            features, marks = self.__get_random_dataset__(dataset=fit_dataset,
                                                          size=fit_dataset.train_objects_length)

            fit_dataset.train_indexed_objects = features
            fit_dataset.train_object_marks = marks

            tree.fit(dataset=fit_dataset)

            self.trees.append(tree)

    def predict(self, features):
        vote_count = defaultdict(lambda: 0)
        for tree in self.trees:
            predicted_value = tree.predict(xs=features)
            vote_count[predicted_value] += 1

        return max(vote_count, key=vote_count.get)

    def predict_vector(self, objects):
        return [self.predict(obj[0]) for obj in objects]

    @staticmethod
    def __get_random_features__(dataset: DatasetObject, number_of_features):
        indices = []
        for _ in range(number_of_features):
            indices.append(random.randint(0, dataset.train_features_count - 1))

        return [
            ([obj[i] for i in indices], idx)
            for (obj, idx) in dataset.train_indexed_objects
        ]

    @staticmethod
    def __get_random_dataset__(dataset: DatasetObject, size) -> ([], []):
        train_features = []
        train_marks = []
        for idx in range(size):
            rand_index = random.randint(0, dataset.train_objects_length - 1)
            train_features.append(
                (dataset.get_train_features(rand_index), idx)
            )
            train_marks.append(dataset.get_train_mark(rand_index))

        return train_features, train_marks


#
#
#
# TODO:= Best decision tree heights. Decision tree predictions.
def find_decision_tree_accuracy():
    print("Decision tree accuracy:")
    tree = DecisionTree(max_height=20, max_samples_leaf=25)
    for data_index in range(1, 22):
        data = DatasetObject(data_index)

        tree.fit(data)

        predicted = tree.predict_vector(data.test_indexed_objects)
        actual = data.test_object_marks

        accuracy = accuracy_score(predicted, actual)

        print(
            "[Dataset <{:02d}>]: accuracy:= {:10f}  |  tree height:= {}".format(data_index, accuracy, tree.height()))

    print('~~~~~~~~~~~~~~~~~~~~~~~~~~~\n')


#
#
#
# TODO:= Plot decision tree graphic for given dataset. xs = [heights from 0 to 20].
def plot_height_accuracy_graphic(dataset_index):
    heights = range(1, 21)

    dataset = DatasetObject(dataset_index)

    test_accuracy_values = []
    train_accuracy_values = []

    for max_height in heights:
        print("height:= {}".format(max_height))
        tree = DecisionTree(max_height=max_height, max_samples_leaf=25)
        tree.fit(dataset)

        test_predicted = tree.predict_vector(dataset.test_indexed_objects)
        test_actual = dataset.test_object_marks

        test_accuracy = accuracy_score(test_predicted, test_actual)
        test_accuracy_values.append(test_accuracy)

        train_predicted = tree.predict_vector(dataset.train_indexed_objects)
        train_actual = dataset.train_object_marks

        train_accuracy = accuracy_score(train_predicted, train_actual)
        train_accuracy_values.append(train_accuracy)

    plt.figure(figsize=(16, 9))
    plt.grid(linestyle='--')
    plt.plot(heights, train_accuracy_values, linestyle='-', marker='.', color='purple', label='Train dataset')
    plt.plot(heights, test_accuracy_values, linestyle='-', marker='.', color='green', label='Test dataset')
    plt.title("My tree  |  Dataset index:= {}".format(dataset_index))
    plt.xlabel('Decision tree height')
    plt.ylabel('Accuracy')
    plt.legend()
    plt.show()


#
#
#
# TODO:= Random forest predictions.
def find_random_forest_accuracy(numbers_of_trees) -> ([], []):
    print("Random forest accuracy:")
    forest = RandomForest(number_of_trees=numbers_of_trees)

    accuracy_values = []
    data_indexes = range(1, 22)

    for data_index in data_indexes:
        data = DatasetObject(data_index)
        forest.fit(data)

        predicted = forest.predict_vector(data.test_indexed_objects)
        actual = data.test_object_marks

        accuracy = accuracy_score(predicted, actual)
        accuracy_values.append(accuracy)

        print(
            "[Dataset <{:02d}>]: accuracy:= {:10f}".format(data_index, accuracy))

    print('~~~~~~~~~~~~~~~~~~~~~~~~~~~\n')

    return data_indexes, accuracy_values


#
#
#
# TODO:= Plot random forest bar graphic.
def plot_data_random_forest_accuracy_graphic(data_indexes: [], accuracy_values: []):
    width = 0.2

    plt.grid(linestyle='--')

    plt.bar(data_indexes,
            accuracy_values,
            label='N random objects',
            color='purple',
            width=width)

    plt.xlabel('Dataset index')
    plt.ylabel('Accuracy')
    plt.legend()

    plt.show()


if __name__ == '__main__':
    # Decision tree
    find_decision_tree_accuracy()
    # plot_height_accuracy_graphic(dataset_index=1)
    # plot_height_accuracy_graphic(dataset_index=1)

    #
    #
    # Random forest
    # xs, ys = find_random_forest_accuracy(numbers_of_trees=25)

    # plot_data_random_forest_accuracy(data_indexes=xs, accuracy_values=ys)

#
#
#
#
# OUTPUT:
#
# Decision tree accuracy:
# [Dataset <01>]: accuracy:=   0.962478  |  tree height:= 10
# [Dataset <02>]: accuracy:=   0.560483  |  tree height:= 10
# [Dataset <03>]: accuracy:=   1.000000  |  tree height:= 9
# [Dataset <04>]: accuracy:=   0.925913  |  tree height:= 9
# [Dataset <05>]: accuracy:=   0.991342  |  tree height:= 4
# [Dataset <06>]: accuracy:=   0.950883  |  tree height:= 9
# [Dataset <07>]: accuracy:=   0.954884  |  tree height:= 9
# [Dataset <08>]: accuracy:=   0.948025  |  tree height:= 6
# [Dataset <09>]: accuracy:=   0.509804  |  tree height:= 5
# [Dataset <10>]: accuracy:=   0.941650  |  tree height:= 10
# [Dataset <11>]: accuracy:=   0.998793  |  tree height:= 10
# [Dataset <12>]: accuracy:=   0.717654  |  tree height:= 10
# [Dataset <13>]: accuracy:=   0.366972  |  tree height:= 8
# [Dataset <14>]: accuracy:=   0.844848  |  tree height:= 10
# [Dataset <15>]: accuracy:=   0.992793  |  tree height:= 9
# [Dataset <16>]: accuracy:=   0.991900  |  tree height:= 9
# [Dataset <17>]: accuracy:=   0.534338  |  tree height:= 9
# [Dataset <18>]: accuracy:=   0.780343  |  tree height:= 9
# [Dataset <19>]: accuracy:=   0.603901  |  tree height:= 9
# [Dataset <20>]: accuracy:=   0.856973  |  tree height:= 10
# [Dataset <21>]: accuracy:=   0.540266  |  tree height:= 9
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~

# Random forest accuracy:
# [Dataset <01>]: accuracy:=   0.975842
# [Dataset <02>]: accuracy:=   0.445547
# [Dataset <03>]: accuracy:=   0.998071
# [Dataset <04>]: accuracy:=   0.929043
# [Dataset <05>]: accuracy:=   0.980519
# [Dataset <06>]: accuracy:=   0.954194
# [Dataset <07>]: accuracy:=   0.963721
# [Dataset <08>]: accuracy:=   0.970894
# [Dataset <09>]: accuracy:=   0.639216
# [Dataset <10>]: accuracy:=   0.957746
# [Dataset <11>]: accuracy:=   0.996781
# [Dataset <12>]: accuracy:=   0.754701
# [Dataset <13>]: accuracy:=   0.466055
# [Dataset <14>]: accuracy:=   0.882671
# [Dataset <15>]: accuracy:=   0.998198
# [Dataset <16>]: accuracy:=   0.996573
# [Dataset <17>]: accuracy:=   0.594082
# [Dataset <18>]: accuracy:=   0.801191
# [Dataset <19>]: accuracy:=   0.674419
# [Dataset <20>]: accuracy:=   0.858162
# [Dataset <21>]: accuracy:=   0.554851
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~
