#include <iostream>
#include <vector>
#include <queue>

using namespace std;
#define to first
#define weight second

const long long INF = 2e15;

using PLLI = pair <long long, int>;
using graph = vector <vector <PLLI>>;

graph g;

pair <long long, long long> dijkstra(int start, int fst, int snd) {
    int n = g.size();
    vector <long long> d(n, INF);
    priority_queue <PLLI, vector <PLLI>, greater <>> notUsed;

    d[start] = 0;
    for (auto& edge: g[start])
        d[edge.to] = edge.weight;

    for (int i = 0; i < n; ++i)
        if (i != start)
            notUsed.push({d[i], i});

    while (!notUsed.empty()) {
        PLLI way = notUsed.top();
        notUsed.pop();
        long long curMinPath = way.first;
        int v = way.second;

        // Проверяем клали ли мы раньше эту вершину в очередь //
        if (d[v] < curMinPath) continue;

        for (auto& edge: g[v]) {
            long long curMinWay = d[v] + edge.weight;
            if (d[edge.to] > curMinWay) {
                d[edge.to] = curMinWay;
                notUsed.push({curMinWay, edge.to});
            }
        }
    }
    return {d[fst], d[snd]};
}

int main() {
    int n, m, a, b, c, start, end, w;
    cin >> n >> m;
    g = graph(n);
    for (int i = 0; i < m; ++i) {
        cin >> start >> end >> w;
        --start;
        --end;

        g[start].emplace_back(end, w);
        g[end].emplace_back(start, w);
    }

    cin >> a >> b >> c;
    --a;
    --b;
    --c;

    auto a_bc = dijkstra(a, b, c);
    auto b_c_ = dijkstra(b, c, 0);

    long long ab = a_bc.first;
    long long ac = a_bc.second;
    long long bc = b_c_.first;

    // Хотя бы две вершины недостижимы из из какой-то вершины => сумма двух любых должна быть >= 2 * INF //
    if (ab + ac + bc >= 2 * INF) {
        cout << -1;
    } else {
        cout << min(ab + ac, min(ab + bc, ac + bc));
    }
}
