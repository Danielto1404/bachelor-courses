#include <iostream>
#include <vector>
#include <algorithm>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define INF LONG_LONG_MAX

struct Edge;
using namespace std;
using graph = vector <vector <Edge>>;

graph zeroEdgesGraph;
graph gT;

vector <bool> used;
vector <int> components;
vector <int> order;
vector <long long> minEdgeInVertex;

struct Edge {
    int from;
    int to;
    long long w;
};

int countVertexDfs(int from, graph& g) {
    used[from] = true;
    int visitedCnt = 1;
    for (auto const& e: g[from])
        if (!used[e.to])
            visitedCnt += countVertexDfs(e.to, g);

    return visitedCnt;
}

void makeOrderTourDfs(int from, graph& g) {
    used[from] = true;
    for (const auto& e: g[from])
        if (!used[e.to])
            makeOrderTourDfs(e.to, g);

    order.push_back(from);
}

void findComponents(int from, int color) {
    used[from] = true;
    components[from] = color;
    for (const auto& e : gT[from])
        if (!used[e.to])
            findComponents(e.to, color);
}

long long findMST(graph& g, int V, int root) {
    long long cost = 0;
    gT = graph(V);
    minEdgeInVertex.assign(V, INF);

    // Находим минимальные ребра //
    for (auto const& edgesFromVertex: g)
        for (auto const& e: edgesFromVertex)
            minEdgeInVertex[e.to] = min(e.w, minEdgeInVertex[e.to]);


    // Добавляем к стоимости минимальные веса //
    for (int i = 0; i < V; ++i)
        if (i != root)
            cost += minEdgeInVertex[i];

    // Заполнили нулевые ребра
    zeroEdgesGraph = graph(V);
    for (auto const& edgesFromVertex: g)
        for (auto const& e: edgesFromVertex)
            if (e.w == minEdgeInVertex[e.to]) {
                zeroEdgesGraph[e.from].push_back({e.from, e.to, 0});
                gT[e.to].push_back({e.to, e.from, 0});
            }

    // Смотрим можно ли из корня достичь все вершины //
    used.assign(V, false);
    int visitedCnt = countVertexDfs(root, zeroEdgesGraph);
    if (visitedCnt == V)
        return cost;

    // Находим порядок посещения вершин при выделении компонент сильной свзяности
    used.assign(V, false);
    order = vector <int>();
    for (int i = 0; i < V; ++i)
        if (!used[i])
            makeOrderTourDfs(i, zeroEdgesGraph);

    reverse(order.begin(), order.end());

    // Выделяем компоненты сильной связности
    used.assign(V, false);
    components = vector <int>(V);
    int color = 0;
    for (int u: order)
        if (!used[u]) {
            findComponents(u, color);
            color++;
        }

    // Создаем новый граф, на основего сконденсированного
    int n_size = color;
    int n_root = components[root];
    graph n_graph(n_size);
    for (const auto& edgeFromVertex: g)
        for (const auto& e: edgeFromVertex) {
            int n_from = components[e.from];
            int n_to = components[e.to];
            if (n_from != n_to)
                n_graph[n_from].push_back({n_from, n_to, e.w - minEdgeInVertex[e.to]});
        }

    cost += findMST(n_graph, n_size, n_root);
    return cost;
}

int main() {
    c_boost;
    int n, m, from, to;
    long long w;
    cin >> n >> m;
    graph g(n);
    used.resize(n);

    for (int i = 0; i < m; ++i) {
        cin >> from >> to >> w;
        --from;
        --to;
        g[from].push_back({from, to, w});
    }

    int visitedCnt = countVertexDfs(0, g);
    if (visitedCnt != n) {
        cout << "NO";
    } else {
        long long cost = findMST(g, n, 0);
        cout << "YES" << '\n' << cost;
    }
}
