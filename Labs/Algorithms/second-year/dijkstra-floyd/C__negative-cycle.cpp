#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct edge {
    int from, to, w;
    edge(int from, int to, int w) : from(from), to(to), w(w) {}
};

int main() {
    int n, weight;
    cin >> n;
    auto edges = vector <edge>();
    vector <long long> d(n, 0);
    vector <int> prev(n, -1);
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j) {
            cin >> weight;
            if (weight == 100000) continue;
            edges.emplace_back(i, j, weight);
        }

    for (int k = 0; k < n; ++k)
        for (auto const& e: edges)
            if (d[e.to] > d[e.from] + e.w) {
                d[e.to] = d[e.from] + e.w;
                prev[e.to] = e.from;
            }

    int vertexAffectedByCycle = -1;
    for (auto const& e: edges)
        if (d[e.to] > d[e.from] + e.w)
            vertexAffectedByCycle = e.to;

    if (vertexAffectedByCycle == -1) {
        cout << "NO\n";
    } else {
        vector <int> cycle;
        int start = vertexAffectedByCycle;
        for (int i = 0; i < n; ++i)
            start = prev[start];

        int curVertex = prev[start];
        cycle.push_back(start);
        while (curVertex != start) {
            cycle.push_back(curVertex);
            curVertex = prev[curVertex];
        }

        reverse(cycle.begin(), cycle.end());
        cout << "YES\n" << cycle.size() << '\n';
        for (int v: cycle)
            cout << v + 1 << ' ';
    }
}
