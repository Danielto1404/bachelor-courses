//#include <bits/stdc++.h>
#include <iostream>

using namespace std;

#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr)
#define root 1

int cnt = 1;
const int MAX_N = 200001;
const int MAX_LOG = 20;

int dp[MAX_N + 1][MAX_LOG + 1];
int alive_parents[MAX_N];
bool isDead[MAX_N];
int h[MAX_N];
int pow2[MAX_LOG + 1];

void init () {
    int pow = 1;
    for (int i = 0; i <= MAX_LOG; i++, pow *= 2) {
        dp[root][i] = root;
        pow2[i] = pow;
    }
    alive_parents[root] = root;
    h[root] = 1;
}

void add (int parent) {
    dp[++cnt][0] = parent;
    for (int i = 1; i <= MAX_LOG; i++) {
        dp[cnt][i] = dp[dp[cnt][i - 1]][i - 1];
    }
    alive_parents[cnt] = parent;
    h[cnt] = h[parent] + 1;
}

int find_nearest_alive (int v) {
    if (isDead[v]) {
        return alive_parents[v] = find_nearest_alive(alive_parents[v]);
    } else {
        return v;
    }
}

void remove (int v) {
    isDead[v] = true;
}

int lca (int v, int u) {
    if (h[v] > h[u]) {
        swap(u, v);
    }
    for (int i = MAX_LOG; i >= 0; i--) {
        if (h[u] >= pow2[i] + h[v]) {
            u = dp[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = MAX_LOG; i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            u = dp[u][i];
            v = dp[v][i];
        }
    }
    int parent = dp[v][0];
    return find_nearest_alive(parent);
}

int main () {
    c_boost;
    char op;
    int m, u, v;
    init();
    cin >> m;
    for (int i = 0; i < m; i++) {
        cin >> op;
        switch (op) {
            case '+':
                cin >> v;
                add(v);
                break;
            case '-':
                cin >> v;
                remove(v);
                break;
            default:
                cin >> v >> u;
                cout << lca(v, u) << '\n';
                break;
        }
    }
}
