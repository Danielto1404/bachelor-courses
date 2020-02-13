//#include <bits/stdc++.h>
#include <iostream>
#include <vector>
#include <cmath>
#include <map>
#include <set>

using namespace std;

#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr)

int n, m, log2n;
const int MAX_N = 200001;
const int MAX_LOG = 20;
const int root = 1;

int dp[MAX_N][MAX_LOG];
vector <int> p;
vector <int> h;
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
    for (int i = 2; i <= n; i++) {
        cin >> p[i];
        parent_children[p[i]].insert(i);
        dp[i][0] = p[i];
    }
    for (int j = 1; j <= log2n; j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
    // Set heights
    h[root] = 0;
    dfs_heights(root, 1);
}

int lca (int v, int u) {
    if (h[v] > h[u]) {
        swap(u, v);
    }
    for (int i = log2n; i >= 0; i--) {
        if (h[u] >= get2Pow(i) + h[v]) {
            u = dp[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = log2n; i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            u = dp[u][i];
            v = dp[v][i];
        }
    }
    return p[v];
}

int main () {
    c_boost;
    int u, v;
    cin >> n;
    p.resize(n + 1);
    h.resize(n + 1);
    log2n = static_cast<int>(log2(n) + 1);
    set_params();
    cin >> m;
    for (int i = 0; i < m; i++) {
        cin >> v >> u;
        cout << lca(v, u) << '\n';
    }
}
