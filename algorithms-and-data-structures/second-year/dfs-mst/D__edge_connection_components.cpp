#include <iostream>
#include <vector>
#include <algorithm>
#include <stack>
#include <map>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector <vector <int>>;

int T = 0;
int color = 0;

graph g;
map <pair <int, int>, int> multi_edges;
stack <int> components;
vector <int> colors;
vector <int> up;
vector <int> time_in;
vector <bool> used;

void readGraph(int E) {
    for (int i = 0; i < E; ++i) {
        int v, u;
        cin >> v >> u;
        --v;
        --u;
        g[v].push_back(u);
        g[u].push_back(v);

        // Записываем кратные ребра //
        auto vu = make_pair(v, u);
        auto uv = make_pair(u, v);
        if (multi_edges[uv]) {
            ++multi_edges[uv];
            ++multi_edges[vu];
        } else {
            multi_edges[uv] = 1;
            multi_edges[vu] = 1;
        }
    }
}

void mark_vertex() {
    colors[components.top()] = color;
    components.pop();
}

void flush_last_component() {
    color++;
    while (!components.empty())
        mark_vertex();
}

void find_components(int v, int prev) {
    time_in[v] = up[v] = T++;
    used[v] = true;
    components.push(v);
    for (int u: g[v]) {
        if (u == prev) {
            continue;
        }
        if (!used[u]) {
            find_components(u, v);
            up[v] = min(up[v], up[u]);
        } else {
            up[v] = min(up[v], time_in[u]);
        }
    }
    if (prev != -1 && up[v] > time_in[prev]) {
        if (multi_edges[make_pair(prev, v)] == 1) {
            color++;
            while (components.top() != v) {
                mark_vertex();
            }
            mark_vertex();
        }
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
    colors.resize(n);
    readGraph(m);

    for (int i = 0; i < n; ++i)
        if (!used[i]) {
            find_components(i, -1);

            // Нужно чтобы в конце освободить стэк
            flush_last_component();
        }

    cout << color << '\n';
    for (int v_color: colors)
        cout << v_color << ' ';
}
