#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using LL = long long;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

struct Edge {
    int from, to, idx;
    LL weight;
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
    vector<int> parents = vector<int>();
    vector<int> rank = vector<int>();
};

int main() {
    c_boost;
    file_open("destroy");
    int n, m, from, to;
    LL s, weight;
    cin >> n >> m >> s;

    vector<Edge> edges(m);
    vector<Edge> notUsed;
    DisjointSet ds = DisjointSet(n);

    for (int i = 0; i < m; ++i) {
        cin >> from >> to >> weight;
        edges[i] = {from - 1, to - 1, i + 1, weight};
    }
    sort(edges.begin(), edges.end(), [](Edge e1, Edge e2) {
        return e1.weight > e2.weight;
    });
    for (const auto &e: edges) {
        if (ds.getId(e.from) != ds.getId(e.to)) {
            ds.join(e.from, e.to);
        } else {
            notUsed.push_back(e);
        }
    }
    set<int> correctIdx; // automatically sorts items
    for (auto e = notUsed.rbegin(); e != notUsed.rend(); e++) {
        if ((s -= e->weight) >= 0) {
            correctIdx.insert(e->idx);
        } else break;
    }
    cout << correctIdx.size() << '\n';
    for (int idx: correctIdx)
        cout << idx << ' ';
}
