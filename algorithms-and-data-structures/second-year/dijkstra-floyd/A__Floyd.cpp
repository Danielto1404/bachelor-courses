#include <iostream>
#include <vector>

using namespace std;

using graph = vector <vector <int>>;

int main() {
    int n, w;
    cin >> n;
    graph dp = graph(n, vector <int>(n));
    graph g = graph(n, vector <int>(n));

    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j) {
            cin >> w;
            g[i][j] = w;
            dp[i][j] = w;
        }

    for (int k = 0; k < n; ++k)
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                dp[i][j] = min(dp[i][j], dp[i][k] + dp[k][j]);

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j)
            cout << dp[i][j] << ' ';
        cout << '\n';
    }
}
