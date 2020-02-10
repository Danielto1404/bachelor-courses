//#include <bits/stdc++.h>
#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

#define c_boost ios_base::sync_with_stdio(false); cin.tie(nullptr)

const int MAX_N = 100001;
const int MAX_LOG = 20;
int dp[MAX_N][MAX_LOG];
int n, log2n;

void set_dp (vector <int> &parent_arr) {
    for (int i = 1; i <= n; i++) {
        dp[i][0] = parent_arr[i];
    }
    for (int j = 1; j <= log2n; j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
}

int main () {
    c_boost;
    cin >> n;
    log2n = static_cast<int>(log2(n) + 1);
    vector <int> parent_arr(n + 1);
    for (int i = 1; i <= n; i++) {
        cin >> parent_arr[i];
    }
    set_dp(parent_arr);
    for (int cur_node = 1; cur_node <= n; cur_node++) {
        cout << cur_node << ": ";
        for (const int v: dp[cur_node]) {
            if (v == 0) break;
            cout << v << " ";
        }
        cout << '\n';
    }
}
