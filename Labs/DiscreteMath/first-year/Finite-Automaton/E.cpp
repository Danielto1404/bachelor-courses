#include <iostream>
#include <vector>
#include <queue>
#include <set>
#include <map>

#define FILE  freopen("problem5.in", "r", stdin); freopen("problem5.out", "w", stdout)
#define MAX_N 101
#define MOD 1000000007
#define start 1

using namespace std;

struct Node {
    char symbol;
    int transit;
};

int len, dp[MAX_N * MAX_N][MAX_N * MAX_N];

// Это массив векторов
vector <Node> nka_aut[MAX_N], dka_aut[MAX_N * MAX_N];
bool nka_terminal[MAX_N], dka_terminal[MAX_N * MAX_N];

long long cnt (int node, int length) {
    if (dp[node][length] != -1) {
        return dp[node][length];
    }
    if (length != 0) {
        dp[node][length] = 0;

        // Перебераем все переходы из одного состояния в другие..
        // dka_aut[node].size() - кол-во переходов из данного состояния.
        for (int i = 0; i < dka_aut[node].size(); i++) {
            dp[node][length] = (dp[node][length] + cnt(dka_aut[node][i].transit, length - 1)) % MOD;
        }
        return dp[node][length];
    } else {
        dp[node][0] = dka_terminal[node] ? 1 : 0;
        return dp[node][0];
    }
}

void NkaToDka () {
    int index = 1;
    queue <set <int> > Q;
    map <set <int>, int> index_of_states;
    set <set <int> > used_states;
    set <int> stack;

    stack.insert(start);
    Q.push(stack);
    used_states.insert(stack);
    index_of_states[stack] = index;
    index++;
    if (nka_terminal[start]) {
        dka_terminal[start] = true;
    }
    while (!Q.empty()) {
        set <int> cur_states = Q.front();
        Q.pop();
        for (char c = 'a'; c <= 'z'; c++) {
            set <int> next_states;

            // Кладем все состояния с символом 'c'
            // -> берем текущее состояние и из него смотрим все переходы
            for (int node: cur_states) {
                for (Node &next_node : nka_aut[node]) {
                    if (next_node.symbol == c) {
                        next_states.insert(next_node.transit);
                    }
                }
            }

            if (!next_states.empty()) {
                if (used_states.count(next_states) == 0) {
                    index_of_states[next_states] = index;
                    used_states.insert(next_states);
                    Q.push(next_states);
                    for (int v: next_states) {
                        if (nka_terminal[v]) {
                            dka_terminal[index] = true;
                            break;
                        }
                    }
                    index++;
                }
                dka_aut[index_of_states[cur_states]].push_back({c, index_of_states[next_states]});
            }
        }
    }
}

int main () {
    FILE;
    int n, m, k, terminalIndex, from, to;
    char symbol;
    cin >> n >> m >> k >> len;
    for (int i = 1; i < MAX_N * MAX_N; i++) {
        dka_terminal[i] = false;
        nka_terminal[i] = false;
        for (int j = 0; j <= len; j++) {
            dp[i][j] = -1;
        }
    }
    for (int i = 0; i < k; i++) {
        cin >> terminalIndex;
        nka_terminal[terminalIndex] = true;
    }
    for (int i = 0; i < m; i++) {
        cin >> from >> to >> symbol;
        nka_aut[from].push_back({symbol, to});
    }
    NkaToDka();
    cout << cnt(1, len);
}
