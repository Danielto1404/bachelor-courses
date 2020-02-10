#include <iostream>
#include <vector>
#include <queue>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector <vector <int>>;
vector <int> cnt;

void readGraph(int E, graph& g) {
    for (int i = 0; i < E; ++i) {
        int v, u;
        cin >> v >> u;
        g[v - 1].push_back(u - 1);
        cnt[u - 1]++;
    }
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    graph g = vector <vector <int>>(n);
    cnt.resize(n);
    vector <int> top_sort;
    queue <int> q;
    readGraph(m, g);

    for (int i = 0; i < n; ++i)
        if (cnt[i] == 0)
            q.push(i);

    while (!q.empty()) {
        int to_delete = q.front();
        q.pop();
        for (int u: g[to_delete]) {
            cnt[u]--;
            if (cnt[u] == 0)
                q.push(u);
        }
        top_sort.push_back(to_delete);
    }
    if (top_sort.size() != n) {
        cout << -1;
    } else
        for (int v: top_sort)
            cout << v + 1 << " ";
}
