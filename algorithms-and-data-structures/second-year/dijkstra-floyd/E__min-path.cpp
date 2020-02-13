#include <iostream>
#include <vector>
#include <set>

using namespace std;
using graph =  vector <vector <pair <int, long long>>>;

#define to first
#define w second

const long long INF = 3e18; // MAX sum E in Edges = 2000 * 10^15 = 2 * 10^18 < 3 * 10^18

graph g;
vector <bool> usedInNegativeCycle;
vector <bool> reachable;

void dfsVisitTour(int from, vector <bool>& used) {
    used[from] = true;
    for (auto const& e: g[from])
        if (!used[e.to])
            dfsVisitTour(e.to, used);
}

int main() {
    int n, m, s, start, finish;
    long long weight;
    cin >> n >> m >> s;
    g = graph(n);
    vector <long long> d(n, INF);
    for (int i = 0; i < m; ++i) {
        cin >> start >> finish >> weight;
        --start;
        --finish;
        g[start].emplace_back(finish, weight);
    }

    --s;
    d[s] = 0;
    set <int> vertexesAffectedByCycle;

    for (int k = 0; k < n; ++k)
        for (int from = 0; from < n; ++from)
            for (const auto& e: g[from])
                if (d[from] != INF)    // То есть from достижима раньше
                    if (d[e.to] > d[from] + e.w) {
                        d[e.to] = d[from] + e.w;

                        if (k == n - 1) // Проверяем что n-ая фаза
                            vertexesAffectedByCycle.insert(e.to);
                    }

    usedInNegativeCycle.assign(n, false);
    reachable.assign(n, false);

    for (int v: vertexesAffectedByCycle)
        if (!usedInNegativeCycle[v])
            dfsVisitTour(v, usedInNegativeCycle);

    dfsVisitTour(s, reachable);

    for (int v = 0; v < n; ++v) {
        if (reachable[v]) {
            if (usedInNegativeCycle[v]) {
                cout << '-';
            } else {
                cout << d[v];
            }
        } else {
            cout << '*';
        }
        cout << '\n';
    }
}
