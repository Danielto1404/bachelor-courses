//#include <bits/stdc++.h>
#include <iostream>
#include <vector>
#include <cmath>
#include <map>
#include <set>

using namespace std;

#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr)
#define FILE freopen ("minonpath.in", "r", stdin); freopen ("minonpath.out", "w", stdout)
#define INF INT32_MAX

int n, m, log2n;
const int MAX_N = 200001;
const int MAX_LOG = 20;
const int root = 1;

int dp[MAX_N][MAX_LOG];
int min_dp[MAX_N][MAX_LOG];
vector <int> p;
vector <int> h;
vector <int> cost_to_parent;
map <int, set <int>> parent_children;

unsigned int get2Pow (int i) {
    return (1u << static_cast<unsigned int>(i));
}

void dfs_heights (int u, int height) {
    for (const int v: parent_children[u]) {
        h[v] = height;
        dfs_heights(v, height + 1);
    }
}

void set_params () {
    p[root] = root;
    dp[root][0] = root;
    cost_to_parent[root] = INF;
    min_dp[root][0] = INF;
    for (int i = 2; i <= n; i++) {
        cin >> p[i];
        cin >> cost_to_parent[i];
        dp[i][0] = p[i];
        min_dp[i][0] = cost_to_parent[i];
        parent_children[p[i]].insert(i);
    }
    for (int j = 1; j <= log2n; j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
            min_dp[i][j] = min(min_dp[dp[i][j - 1]][j - 1], min_dp[i][j - 1]);
        }
    }
    // Set heights
    h[root] = 0;
    dfs_heights(root, 1);
}

int min_on_way (int v, int u) {
    int cur_min = INF;
    if (h[v] > h[u]) {
        swap(u, v);
    }
    for (int i = log2n; i >= 0; i--) {
        if (h[u] >= get2Pow(i) + h[v]) {
            cur_min = min(cur_min, min_dp[u][i]);
            u = dp[u][i];
        }
    }
    if (v == u) {
        return cur_min;
    }
    for (int i = log2n; i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            cur_min = min(cur_min, min(min_dp[u][i], min_dp[v][i]));
            u = dp[u][i];
            v = dp[v][i];
        }
    }
    return min(cur_min, min(cost_to_parent[v], cost_to_parent[u]));
}

int main () {
    c_boost;
    FILE;
    int u, v;
    cin >> n;
    p.resize(n + 1);
    h.resize(n + 1);
    cost_to_parent.resize(n + 1);
    log2n = static_cast<int>(log2(n) + 1);
    set_params();
    cin >> m;
    for (int i = 0; i < m; i++) {
        cin >> v >> u;
        cout << min_on_way(v, u) << '\n';
    }
}
