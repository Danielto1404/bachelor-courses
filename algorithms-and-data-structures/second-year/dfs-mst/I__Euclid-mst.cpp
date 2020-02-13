
#include <iostream>
#include <vector>
#include <math.h>
#include <set>

using namespace std;

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define INF 50000

vector <long double> xs, ys;

long double distance(int i, int j) {
    return sqrt(pow(xs[i] - xs[j], 2) + pow(ys[i] - ys[j], 2));
}

int main() {
    c_boost;
    int n;
    long double x, y, min_edge, min_cost = 0;
    cin >> n;
    set <int> notUsed;
    vector <long double> min_dist_to_notUsed(n);

    for (int i = 0; i < n; ++i) {
        cin >> x >> y;
        xs.push_back(x);
        ys.push_back(y);
        if (i != 0) {
            notUsed.insert(i);
            min_dist_to_notUsed[i] = distance(0, i);
        }
    }

    while (!notUsed.empty()) {
        min_edge = INF;
        int cur_min_vertex = 0;

        // Ищем текущее минимальное ребро в множество не использованных вершин
        for (int u: notUsed) {
            if (min_edge > min_dist_to_notUsed[u]) {
                cur_min_vertex = u;
                min_edge = min_dist_to_notUsed[u];
            }
        }

        min_cost += min_edge;
        notUsed.erase(cur_min_vertex);

        // Пересчитываем ребра, которые входят в множетсво не использованных вершин
        for (int u: notUsed) {
            long double dist = distance(cur_min_vertex, u);
            min_dist_to_notUsed[u] = min(dist, min_dist_to_notUsed[u]);
        }
    }

    cout << min_cost;
}
