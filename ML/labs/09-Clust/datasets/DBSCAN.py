import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn.cluster import DBSCAN
from sklearn.metrics import accuracy_score, silhouette_score

from clust import read_dataset, DatasetName, draw_clusters

dataset = DatasetName.WINE

train_xs, train_ys = read_dataset(dataset)
frame = pd.read_csv('/Users/da.korolev/Documents/GitHub/3-rd-year/ml/labs/09-Clust/datasets/wine.csv')


def transpose(m, size):
    return [[m[j][i] for j in range(size)] for i in range(size)]


mx_score = 0
algo = None

# TODO: Uncomment to find best algo.
# Finds best algo
#
# for eps in np.linspace(0.001, 10, 1000):
#     for neighbours in range(1, 10):
#         dbscan = DBSCAN(eps=eps, min_samples=neighbours, metric='canberra', algorithm='brute')
#         y_predicted = dbscan.fit_predict(frame) + 1
#
#         score = accuracy_score(y_predicted, train_ys)
#         if score > mx_score:
#             mx_score = score
#             algo = dbscan
#
# print(mx_score)
# print(algo.get_params(True))

best_algo = DBSCAN(eps=1.0119099099099098, min_samples=9, metric='canberra', algorithm='brute')
pred = best_algo.fit_predict(frame) + 1

draw_clusters(train_xs, train_ys, 'Original dataset: Wine', save_name='original.png')
draw_clusters(train_xs, pred, 'Clusterized by DBSCAN dataset: Wine   |   Accuracy: 0.78', save_name='clusterized.png')

eps_xs = []
f_scores = []
silhouette_scores = []

for eps in np.linspace(0.001, 3, 500):
    algo = DBSCAN(eps=eps, min_samples=4, metric='canberra', algorithm='brute')
    predicted = algo.fit_predict(frame) + 1
    eps_xs.append(eps)
    f_score = accuracy_score(train_ys, predicted)
    f_scores.append(f_score)
    if len(set(algo.labels_ + 1)) == 1:
        silhouette_scores.append(0)
    else:
        silhouette_scores.append(silhouette_score(train_ys.reshape(178, 1), predicted))

plt.figure(figsize=(16, 9))
plt.grid('--')
plt.title("Distance: canberra   |   neighbours: 4")
plt.xlabel("Eps")
plt.ylabel("Measure")
plt.plot(eps_xs, f_scores, label='F-score')
plt.plot(eps_xs, silhouette_scores, label="Silhouette score")
plt.legend()
plt.show()
# plt.savefig("scores.png")
