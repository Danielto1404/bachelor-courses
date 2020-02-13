#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector <vector <int>>;

int T = 0;
graph g;
vector <int> articular_points;
set <int> used_articular_points;

vector <bool> used;
vector <bool> isTaken;
vector <int> up;
vector <int> time_in;

void readGraph(int E) {
    for (int i = 0; i < E; ++i) {
        int v, u;
        cin >> v >> u;
        --v;
        --u;
        g[v].push_back(u);
        g[u].push_back(v);
    }
}

void find_articular_points(int v, int prev, int root) {
    time_in[v] = up[v] = T++;
    int child_number = 0;
    used[v] = true;
    for (int u: g[v]) {
        if (u == prev) {
            continue;
        }
        if (!used[u]) {
            find_articular_points(u, v, root);
            up[v] = min(up[v], up[u]);
            child_number++;
        } else
            up[v] = min(up[v], time_in[u]);
    }

    // Запрещаем вершине быть корнем или ребенком корня
    // Так как ребенка мы уже обработали изначально //
    if (v != root && prev != root && up[v] >= time_in[prev]) {
        used_articular_points.insert(prev);
    } else if (v == root && child_number >= 2) {
        used_articular_points.insert(v);
    }
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    g = vector <vector <int>>(n);
    used.resize(n);
    time_in.resize(n);
    up.resize(n);
    isTaken.resize(n);
    readGraph(m);

    for (int i = 0; i < n; ++i)
        if (!used[i])
            find_articular_points(i, -1, i);

    articular_points = vector <int>(used_articular_points.begin(), used_articular_points.end());
    sort(articular_points.begin(), articular_points.end());
    cout << articular_points.size() << '\n';
    for (int point: articular_points)
        cout << point + 1 << " ";
}
