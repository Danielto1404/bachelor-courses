//#include <bits/stdc++.h>
#include <iostream>
#include <vector>

using namespace std;

#define MAX_LOG 20
#define MAX_N 100001
#define root 1
#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr)

int n, cnt;
int p[MAX_N];
int h[MAX_N];
int pow2[MAX_LOG + 1];
int dp[MAX_N][MAX_LOG + 1];

vector <int> graph[MAX_N];

bool visited[MAX_N];
bool dfs_visited[MAX_N];
bool on_way_to_root[MAX_N];

void dfs_tree(int parent) {
    dfs_visited[parent] = true;
    for (int child : graph[parent]) {
        if (!dfs_visited[child]) {
            p[child] = parent;
            dp[child][0] = parent;
            h[child] = h[parent] + 1;
            dfs_tree(child);
        }
    }
}

void make_tree() {
    p[root] = root;
    dp[root][0] = root;
    h[root] = 0;
    visited[root] = true;

    dfs_tree(root);

    pow2[0] = 1;
    for (int j = 1; j <= MAX_LOG; j++) {
        pow2[j] = pow2[j - 1] * 2;
        for (int i = 1; i <= n; i++)
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
    }
}

void jump_and_mark(int v, int lca) {
    while (v != lca && !on_way_to_root[v]) {
        if (lca == root)
            on_way_to_root[v] = true;

        if (!visited[v]) {
            visited[v] = true;
            cnt--;
        }
        v = p[v];
    }
}

int lca(int v, int u) {
    if (h[v] > h[u])
        swap(v, u);

    for (int i = MAX_LOG; i >= 0; i--)
        if (h[u] - h[v] >= pow2[i])
            u = dp[u][i];

    if (v == u)
        return v;

    for (int i = MAX_LOG; i >= 0; i--)
        if (dp[v][i] != dp[u][i]) {
            v = dp[v][i];
            u = dp[u][i];
        }

    return p[v];
}

int main() {
    c_boost;
    int u, v, m;
    cin >> n;
    for (int i = 0; i < n - 1; i++) {
        cin >> u >> v;
        graph[u].push_back(v);
        graph[v].push_back(u);
    }
    make_tree();
    cin >> m;
    cnt = n - 1;
    for (int i = 0; i < m; i++) {
        cin >> u >> v;
        int lca_val = lca(v, u);
        jump_and_mark(u, lca_val);
        jump_and_mark(v, lca_val);
    }
    cout << cnt;
}
