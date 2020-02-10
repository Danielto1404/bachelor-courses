#include <iostream>
#include <vector>
#include <algorithm>
#include <map>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector <vector <int>>;

map <pair <int, int>, int> edges;

int T = 0;
graph g;
vector <int> bridges;
vector <bool> used;
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
        edges[make_pair(v, u)] = i + 1;
        edges[make_pair(u, v)] = i + 1;
    }
}

void find_bridges(int v, int prev) {
    time_in[v] = up[v] = T++;
    used[v] = true;
    for (int u: g[v]) {
        if (u == prev) {
            continue;
        }
        if (!used[u]) {
            find_bridges(u, v);
            up[v] = min(up[v], up[u]);
        } else
            up[v] = min(up[v], time_in[u]);
    }
    if (prev != -1 && up[v] > time_in[prev]) {
        bridges.push_back(edges[make_pair(prev, v)]);
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
    readGraph(m);

    for (int i = 0; i < n; ++i)
        if (!used[i])
            find_bridges(i, -1);

    sort(bridges.begin(), bridges.end());
    cout << bridges.size() << '\n';
    for (int bridge: bridges)
        cout << bridge << " ";
}
