#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;
using graph = vector <vector <int>>;

#define FILE freopen ("game.in", "r", stdin); freopen ("game.out", "w", stdout)

void topSort(graph& g, vector <int>& sorted, vector <int>& cnt) {
    queue <int> q;
    int n = g.size();

    for (int i = 0; i < n; ++i)
        if (cnt[i] == 0)
            q.push(i);

    while (!q.empty()) {
        int to_delete = q.front();
        q.pop();
        for (int u: g[to_delete]) {
            --cnt[u];
            if (cnt[u] == 0)
                q.push(u);
        }
        sorted.push_back(to_delete);
    }
}

int main() {
    FILE;
    int n, m, s, from, to;
    cin >> n >> m >> s;

    graph g = graph(n);
    vector <int> cnt(n, 0);
    vector <bool> win(n, false);
    vector <int> top_sorted;

    for (int i = 0; i < m; ++i) {
        cin >> from >> to;
        --from;
        --to;
        g[from].push_back(to);
        cnt[to]++;
    }

    topSort(g, top_sorted, cnt);
    reverse(top_sorted.begin(), top_sorted.end());

    for (int u: top_sorted)
        for (int v: g[u]) {
            win[u] = win[u] || !win[v];
            if (win[u]) break;
        }

    string name = win[--s] ? "First" : "Second";
    cout << name << " player wins";
}
