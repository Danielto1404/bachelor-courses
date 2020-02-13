#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector <vector <int>>;

int color = 0;
graph g, complement_g;

set <pair <int, int>> bridges;
vector <int> components;
vector <bool> used;
vector <int> order;

void readGraph(int E) {
    for (int i = 0; i < E; ++i) {
        int v, u;
        cin >> v >> u;
        --v;
        --u;
        g[v].push_back(u);
        complement_g[u].push_back(v);
    }
}

void make_order(int from) {
    used[from] = true;
    for (int to: g[from])
        if (!used[to])
            make_order(to);

    order.push_back(from);
}

void find_components(int from) {
    used[from] = true;
    components[from] = color;
    for (int to : complement_g[from])
        if (!used[to])
            find_components(to);
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    g = graph(n);
    complement_g = graph(n);
    used.resize(n);
    components.resize(n);
    readGraph(m);

    for (int i = 0; i < n; ++i)
        if (!used[i])
            make_order(i);

    // Делаем в порядке возврастания времени
    reverse(order.begin(), order.end());
    used.assign(n, false);

    for (int v: order)
        if (!used[v]) {
            find_components(v);
            color++;
        }

    for (int v = 0; v < n; ++v)
        for (int u: g[v])
            if (components[u] != components[v]) {
                bridges.insert({(components[v], components[u]});
            }

    cout << bridges.size();
}
