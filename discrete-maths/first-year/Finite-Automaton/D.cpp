#include <iostream>
#include <vector>
#include <map>
#include <array>

using namespace std;

#define FILE freopen ("problem4.in", "r", stdin); freopen ("problem4.out", "w", stdout)
#define IS_NOT_VISITED -1

const int MOD = 1000000007;

using namespace std;

struct State {
    bool isTerminal = false;
    vector <int> connectedStates;
};

array <array <int, 10001>, 101> dp;
vector <State> states;

int cntOfWords(int state, int length) {
    if (dp[state][length] != IS_NOT_VISITED) {
        return dp[state][length];
    } else {
        if (length != 0) {
            dp[state][length] = 0;
            for (int &nextState : states[state].connectedStates)
                dp[state][length] = (dp[state][length] + cntOfWords (nextState, length - 1)) % MOD;
            return dp[state][length];
        } else {
            dp[state][0] = states[state].isTerminal ? 1 : 0;
            return dp[state][length];
        }
    }
}

int main() {
    FILE;
    int n, m, k, l, terminalIndex, from, to;
    char symbol;
    cin >> n >> m >> k >> l;
    states.resize (n + 1);
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j <= l; j++) {
            dp[i][j] = -1;
        }
    }
    for (int i = 0; i < k; i++) {
        cin >> terminalIndex;
        states[terminalIndex].isTerminal = true;
    }
    for (auto i = 0; i < m; ++i) {
        cin >> from >> to >> symbol;
        states[from].connectedStates.push_back (to);
    }
    cout << cntOfWords (1, l);
    return 0;
}
