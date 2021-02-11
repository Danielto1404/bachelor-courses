import random

from collections import defaultdict
from enum import Enum
from math import log2

inf = float('inf')
minus_inf = -inf


class ALGO(Enum):
    AVG = "average"
    MEDIAN = "median"


class TrainElement:
    def __init__(self, features, mark):
        self.features = features
        self.mark = mark


features_count, classes_count, max_depth = map(int, input().split())
n_train = int(input())

train_dataset = []

for _ in range(n_train):
    data = list(map(int, input().split()))
    y = data.pop()
    element = TrainElement(features=data, mark=y)
    train_dataset.append(element)


def random_index():
    return random.randint(0, n_train - 1)


def count_classes(elements: [TrainElement]):
    class_cnt = defaultdict(lambda: 0)
    for e in elements:
        class_cnt[e.mark] += 1

    return class_cnt


def get_most_common_class(elements: [TrainElement]) -> int:
    class_cnt = count_classes(elements=elements)
    return max(class_cnt, key=class_cnt.get)


def calculate_entropy(elements: [TrainElement]):
    class_cnt = count_classes(elements=elements)
    length = len(elements)

    class_probabilities = [count_value / length for (_, count_value) in class_cnt.items()]

    entropy = -sum(p * log2(p) for p in class_probabilities)

    return entropy


def delta_entropy_for_split(left_elements: [TrainElement], right_elements: [TrainElement]) -> (float, float, float):
    left_len = len(left_elements)
    right_len = len(right_elements)

    left_entropy = calculate_entropy(elements=left_elements)
    right_entropy = calculate_entropy(elements=right_elements)

    elements_amount = left_len + right_len

    return (
        left_len / elements_amount * left_entropy + right_len / elements_amount * right_entropy
        , left_entropy
        , right_entropy
    )


def extract_feature_vector(feature_index, elements: [TrainElement]):
    return [e.features[feature_index] for e in elements]


def calculate_avg_feature(feature_index, elements: [TrainElement]):
    return sum(extract_feature_vector(feature_index=feature_index,
                                      elements=elements)) / len(elements)


def calculate_median_feature(feature_index, elements: [TrainElement]):
    length = len(elements)

    features = extract_feature_vector(feature_index=feature_index, elements=elements)
    features.sort()

    if length % 2 == 0:
        return (features[length // 2 - 1] + features[length // 2]) / 2

    return features[length // 2]


#
#
# Tree node class
class Node:
    def __init__(self, elements, height=0):
        self.left = None
        self.right = None
        self.predicate_index = None
        self.predicate_value = None
        self.isLeaf = False
        self.class_mark = None
        self.height = height
        self.elements = elements

    def set_left_subtree(self, left):
        self.left = left

    def set_right_subtree(self, right):
        self.right = right

    def set_predicate_index(self, index):
        self.predicate_index = index

    def set_predicate_value(self, value):
        self.predicate_value = value

    @staticmethod
    def traverse(node, index) -> (str, int):
        if node.isLeaf:
            return "C {}".format(node.class_mark), index

        left_log, left_count_index = Node.traverse(node.left, index + 1)
        right_log, right_count_index = Node.traverse(node.right, left_count_index + 1)

        return "Q {} {} {} {}\n{}\n{}".format(node.predicate_index + 1,
                                              node.predicate_value,
                                              index + 1,
                                              left_count_index + 1,
                                              left_log,
                                              right_log), right_count_index


# Decision tree classifier class.
class DecisionTree:
    def __init__(self, max_height=inf):
        self.root = None
        self.max_height = max_height

    # Public fit function
    def fit(self, dataset: [TrainElement]):
        self.root = Node(elements=dataset)
        self.__fit_node__(entropy=calculate_entropy(elements=dataset), node=self.root)

    #
    # Private fit function
    def __fit_node__(self, entropy: float, node: Node):

        if node.height == self.max_height or len(node.elements) == 1:
            node.class_mark = get_most_common_class(elements=node.elements)
            node.isLeaf = True
            return

        index, value, left_elements, right_elements, left_entropy, right_entropy, IG = \
            self.find_best_feature_partition(entropy=entropy,
                                             dataset=node.elements)

        if len(left_elements) == 0 or len(right_elements) == 0 or IG < 1e-4:
            node.class_mark = get_most_common_class(elements=node.elements)
            node.isLeaf = True
            return

        node.set_predicate_index(index=index)
        node.set_predicate_value(value=value)

        left_node = Node(elements=left_elements, height=node.height + 1)
        right_node = Node(elements=right_elements, height=node.height + 1)

        self.__fit_node__(entropy=left_entropy, node=left_node)
        self.__fit_node__(entropy=right_entropy, node=right_node)

        node.set_left_subtree(left=left_node)
        node.set_right_subtree(right=right_node)

    @staticmethod
    def get_function_features(function, dataset: [TrainElement]):
        return [function(index, dataset)
                for index in range(features_count)]

    def find_best_feature_partition(self, entropy: float, dataset: [TrainElement]):
        best_IG, best_feature_index, left_entropy, right_entropy = 0, 0, 0, 0

        left_elements, right_elements = [], []

        algo = ALGO.AVG

        avg_features = self.get_function_features(function=calculate_avg_feature,
                                                  dataset=dataset)

        median_features = self.get_function_features(function=calculate_median_feature,
                                                     dataset=dataset)

        for feature_index in range(features_count):

            avg_left = []
            avg_right = []

            median_left = []
            median_right = []

            for e in dataset:
                if e.features[feature_index] <= avg_features[feature_index]:
                    avg_left.append(e)
                else:
                    avg_right.append(e)

                if e.features[feature_index] <= median_features[feature_index]:
                    median_left.append(e)
                else:
                    median_right.append(e)

            avg_entropy, left_avg_entropy, right_avg_entropy = delta_entropy_for_split(left_elements=avg_left,
                                                                                       right_elements=avg_right)

            median_entropy, left_median_entropy, right_median_entropy = delta_entropy_for_split(
                left_elements=median_left,
                right_elements=median_right)

            IG = max(entropy - avg_entropy, entropy - median_entropy)

            if IG > best_IG:
                best_IG = IG
                best_feature_index = feature_index

                algo = ALGO.AVG if avg_entropy < median_entropy else ALGO.MEDIAN

                left_elements = avg_left if algo == ALGO.AVG else median_left
                right_elements = avg_right if algo == ALGO.AVG else median_right

                left_entropy = left_avg_entropy if algo == ALGO.AVG else left_median_entropy
                right_entropy = right_avg_entropy if algo == ALGO.AVG else right_median_entropy

        return (
            best_feature_index,
            avg_features[best_feature_index] if algo == ALGO.AVG else median_features[best_feature_index],
            left_elements,
            right_elements,
            left_entropy,
            right_entropy,
            best_IG
        )

    def log_and_count(self) -> (str, int):
        return Node.traverse(self.root, 1)


tree = DecisionTree(max_height=min(8, max_depth))

tree.fit(dataset=train_dataset)
tree_log, count = tree.log_and_count()
print(count, tree_log, sep='\n')
