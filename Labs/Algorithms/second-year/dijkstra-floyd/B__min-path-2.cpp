#include <iostream>
#include <vector>
#include <queue>

using namespace std;
#define to first
#define weight second
#define INF LONG_LONG_MAX

using PLLI = pair <long long, int>;
using graph = vector <vector <PLLI>>;

int main() {
    int n, m, start, end, w;
    cin >> n >> m;
    graph g = graph(n);
    priority_queue <PLLI, vector <PLLI>, greater <>> notUsed;
    vector <long long> d(n, INF);
    for (int i = 0; i < m; ++i) {
        cin >> start >> end >> w;
        --start;
        --end;

        g[start].emplace_back(end, w);
        g[end].emplace_back(start, w);
    }

    d[0] = 0;
    for (auto& edge: g[0])
        d[edge.to] = edge.weight;

    for (int i = 1; i < n; ++i)
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

    for (long long minWay: d)
        cout << minWay << ' ';
}
