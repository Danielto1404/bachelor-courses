import enum
from collections import defaultdict

import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from sklearn.decomposition import PCA
from sklearn.metrics import accuracy_score


#
# Available datasets
class DatasetName(enum.Enum):
    WINE = "datasets/wine.csv"


#
#
# Main distance metrics functions.
class Distance(enum.Enum):
    EUCLIDEAN = lambda xs, ys: sum((x - y) ** 2 for (x, y) in zip(xs, ys))
    MANHATTAN = lambda xs, ys: sum(abs(x - y) for (x, y) in zip(xs, ys))
    CANBERRA = lambda xs, ys: sum(
        abs(x - y) / (abs(x) + abs(y))
        if (abs(x) + abs(y) != 0)
        else 0
        for (x, y) in zip(xs, ys))

    def __call__(self, *args):
        self.value(*args)


#
#
# Dataset reading with normalization operation.
def read_dataset(name: DatasetName):
    frame = pd.read_csv(name.value)

    feature_columns = filter(lambda column_name: column_name != 'class',
                             frame.columns.values)

    for feature_name in frame:
        if feature_name == 'class':
            continue
        frame[feature_name] /= frame[feature_name].max()
        frame[feature_name] -= frame[feature_name].min()

    return [tuple(x) for x in frame[feature_columns].to_numpy()], frame['class'].values


#
#
# Class that implement DBSCAN algorithm.
class DBSCAN:

    @staticmethod
    def get_color(cluster_number):
        colors = 'rgbcpmykgrcmykgrcmykgrcmykgrcmykgrcmyk'
        return colors[cluster_number % len(colors)]

    def __init__(self):
        self.NOISE = 0
        self.cluster_number = 0
        self.visited_points = set()
        self.clustered_points = set()
        self.clusters = {self.NOISE: []}
        self.dataset = []

    def clusterize(self, dataset, radius, minNeighbours, distance: Distance):
        self.dataset = dataset

        def region_query(pivot_element):
            return [q for q in dataset if distance(pivot_element, q) < radius]

        def expand_cluster(pivot_point, pivot_neighbours):

            if self.cluster_number not in self.clusters:
                self.clusters[self.cluster_number] = [pivot_point]

            self.clustered_points.add(pivot_point)
            while pivot_neighbours:
                q = pivot_neighbours.pop()
                if q not in self.visited_points:
                    self.visited_points.add(q)
                    neighbours = region_query(q)
                    if len(neighbours) > minNeighbours:
                        pivot_neighbours.extend(neighbours)
                if q not in self.clustered_points:
                    self.clustered_points.add(q)
                    self.clusters[self.cluster_number].append(q)
                    if q in self.clusters[self.NOISE]:
                        self.clusters[self.NOISE].remove(q)

        for point in dataset:
            if point in self.visited_points:
                continue

            self.visited_points.add(point)
            neighbours = region_query(point)

            if len(neighbours) < minNeighbours:
                self.clusters[self.NOISE].append(point)
            else:
                self.cluster_number += 1
                expand_cluster(point, neighbours)

    def get_clusters(self):
        clusters = []
        for x in self.dataset:
            for (c, values) in self.clusters.items():
                if x in values:
                    clusters.append(c)
        return clusters


#
#
# Draw method for original dataset
def draw_clusters(xs, ys, message, save_name="rename.png"):
    plt.figure(figsize=(16, 9))
    plt.grid("--")
    plt.title(message)

    for point_2D, y_mark in zip(PCA(n_components=2).fit_transform(xs), ys):
        x = point_2D[0]
        y = point_2D[1]
        plt.scatter(x, y, color=DBSCAN.get_color(cluster_number=y_mark))
    plt.show()
    # plt.savefig(save_name)


#
#
# Accuracy
def dbscan_accuracy(r, _neighbours, _distance: Distance):
    dbscan = DBSCAN()
    dbscan.clusterize(xs_train, r, _neighbours, _distance)

    ys_predicted = dbscan.get_clusters()
    # print(ys_predicted)

    return accuracy_score(ys_predicted, ys_train)


#
#
# Find best params and read data.
dataset = DatasetName.WINE
xs_train, ys_train = read_dataset(dataset)


def find_best_params(distance: Distance = Distance.EUCLIDEAN):
    score = 0
    best_radius = 0.01
    best_neighbours_value = 1
    iteration = 1

    radius_range = np.linspace(0.01, 3, 100)
    neighbours_range = range(1, 15)

    print("\nNumber of all iterations: {}\n".format(len(radius_range) * len(neighbours_range)))

    for radius in radius_range:
        for neighbours_value in neighbours_range:
            current_score = dbscan_accuracy(r=radius, _neighbours=neighbours_value, _distance=distance)
            print(iteration)
            iteration += 1
            #
            # print(
            #     "iteration: {} | radius {}, number of nearest points {} | accuracy {}".format(iteration,
            #                                                                                   radius,
            #                                                                                   neighbours_value,
            #                                                                                   current_score))
            # iteration += 1

            if current_score > score:
                score = current_score
                best_radius = radius
                best_neighbours_value = neighbours_value

    print("Best radius:= {} | Best neighbours value:= {} | accuracy:= {}".format(best_radius,
                                                                                 best_neighbours_value,
                                                                                 score))


def f_score(labels, clusters):
    n = np.array([[0] * len(clusters) for _ in range(len(labels))])
    for j in range(len(clusters)):
        for i in clusters[j]:
            n[labels[i]][j] += 1

    p = n / np.sum(n)

    result = 0
    for j in range(len(clusters)):
        row_sum = np.sum(p, axis=1)
        col_sum = np.sum(p, axis=0)
        if np.isclose(col_sum[j], 0):
            continue
        result += col_sum[j] * np.max([(2 * p[i][j] ** 2 / (row_sum[i] * col_sum[j]))
                                       / (p[i][j] / row_sum[i] + p[i][j] / col_sum[j])
                                       for i in range(len(labels))
                                       if not np.isclose(row_sum[i], 0) and not np.isclose(p[i][j], 0)])


def RAND_measure(ys_predicted, ys_actual):
    classNumbers = ys_predicted.max() - ys_predicted.min() + 1
    unique_ys = set(ys_actual)

    size = max(classNumbers, len(unique_ys)) + 1

    actual = [0] * size
    confusion_matrix = [[0] * size for _ in range(size)]

    for y_predicted, y_actual in zip(ys_predicted + 1, ys_actual):
        confusion_matrix[y_actual][y_predicted] += 1
        actual[y_actual] += 1

    true_positive = [confusion_matrix[i][i] for i in range(size)]
    false_negative = [actual[i] - true_positive[i] for i in range(size)]

    return (sum(true_positive) + sum(false_negative)) / sum(actual)


def draw_cluster_graphic():
    draw_clusters(xs_train, ys_train, dataset.name)

    dbscan = DBSCAN()
    dbscan.clusterize(xs_train, radius=1.912727272727273, minNeighbours=3, distance=Distance.CANBERRA)

    cluster_marks = defaultdict(lambda: 0)

    for (c, cluster_elements) in dbscan.clusters.items():
        for e in cluster_elements:
            cluster_marks[e] = c

    print(dbscan.clusters.keys())
    plt.figure(figsize=(16, 9))
    plt.grid("--")
    plt.title("Clusterized:")

    pca_2D_cluster_points = []
    marks = []
    for (c, points) in dbscan.clusters.items():
        pca_2D_cluster_points.extend(points)
        marks.append(c)

    pca_2D_cluster_points = PCA(n_components=2).fit_transform(xs_train)

    for (x_original, point) in zip(xs_train, pca_2D_cluster_points):
        c = cluster_marks.get(x_original)
        x = point[0]
        y = point[1]
        plt.scatter(x, y, color=DBSCAN.get_color(cluster_number=c))

    plt.show()


if __name__ == '__main__':
    # find_best_params(Distance.CANBERRA)
    # find_best_params(Distance.MANHATTAN)
    # find_best_params(Distance.EUCLIDEAN)
    draw_cluster_graphic()
