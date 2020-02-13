#include <iostream>
#include <vector>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define vertex first
#define edge_index second

using namespace std;
using graph = vector <vector <pair <int, int>>>;

int T = 0;
int mainColor = 0;
graph g;

vector <int> colors;
vector <bool> used;
vector <int> up;
vector <int> time_in;

void readGraph(int E) {
    for (int i = 0; i < E; ++i) {
        int v, u;
        cin >> v >> u;
        --v;
        --u;
        g[v].emplace_back(u, i);
        g[u].emplace_back(v, i);
    }
}

void set_time(int from, int parent) {
    time_in[from] = up[from] = T++;
    used[from] = true;
    for (auto to: g[from]) {
        if (to.vertex == parent) continue;

        if (!used[to.vertex]) {
            set_time(to.vertex, from);
            up[from] = min(up[from], up[to.vertex]);
        } else
            up[from] = min(up[from], time_in[to.vertex]);
    }
}

void paintEdges(int from, int curColor) {
    used[from] = true;
    for (auto to : g[from]) {
        if (used[to.vertex]) {
            // То есть мы были в вершине но из другой КС, но тогда нам нужно пометить ребро из текущей КС
            if (colors[to.edge_index] == 0) {
                colors[to.edge_index] = curColor;
            }

            // Не были в этоц вершине => либо ТС либо в компоненте
        } else {
            if (up[to.vertex] >= time_in[from]) {
                // Нашли ТС => мост (то есть помечаем его в новую КС)
                colors[to.edge_index] = ++mainColor;
                paintEdges(to.vertex, mainColor);
            } else {
                // Мы в текущей КС => помечаем ее таким же цеветом
                colors[to.edge_index] = curColor;
                paintEdges(to.vertex, curColor);
            }
        }
    }
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    g = graph(n);
    used.resize(n);
    time_in.resize(n);
    up.resize(n);
    colors.resize(m);
    readGraph(m);

    for (int i = 0; i < n; ++i)
        if (!used[i])
            set_time(i, -1);

    used.assign(n, false);
    for (int i = 0; i < n; ++i)
        if (!used[i])
            paintEdges(i, 0);

    cout << mainColor << '\n';
    for (int edge_color: colors) {
        cout << edge_color << ' ';
    }
}
