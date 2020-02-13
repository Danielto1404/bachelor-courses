#include <iostream>
#include <vector>
#include <algorithm>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

struct Edge;

using namespace std;
using graph = vector <Edge>;

graph edges;

struct Edge {
    int from, to, cost;
};

class DisjointSet {
public:
    explicit DisjointSet(int n) : m_size(n) {
        rank.assign(n, 1);
        parents.resize(n);
        for (int i = 0; i < n; ++i)
            parents[i] = i;
    }

    int getId(int x) {
        if (parents[x] != x) {
            parents[x] = getId(parents[x]);
        }
        return parents[x];
    }

    void join(int x, int y) {
        int x_id = getId(x);
        int y_id = getId(y);
        if (x_id != y_id) {
            if (rank[x_id] < rank[y_id]) {
                parents[x_id] = y_id;
            } else {
                if (rank[x_id] == rank[y_id])
                    rank[x_id]++;

                parents[y_id] = x_id;
            }
        }
    }
private:
    int m_size;
    vector <int> parents = vector <int>();
    vector <int> rank = vector <int>();
};

void readGraph(int E) {
    int from, to, cost;
    for (int i = 0; i < E; ++i) {
        cin >> from >> to >> cost;
        edges.push_back({from - 1, to - 1, cost});
    }
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    long long cost = 0;
    DisjointSet ds = DisjointSet(n);
    readGraph(m);

    // Сортируем по стоимости ребра,
    // мы действуем жадно и на каждом шаге берем минимальные ребра
    sort(edges.begin(), edges.end(), [](Edge e1, Edge e2) {
        return e1.cost < e2.cost;
    });

    // Если у нас ребро соед. вершины из разных компонент,
    // то добавляем его и объеденяем компоненты
    // Иначе такое ребро добавить нельзя, так как мы сделаем цикл => не будет дерева
    for (const auto& e: edges) {
        if (ds.getId(e.from) != ds.getId(e.to)) {
            ds.join(e.from, e.to);
            cost += e.cost;
        }
    }
    cout << cost;
}
