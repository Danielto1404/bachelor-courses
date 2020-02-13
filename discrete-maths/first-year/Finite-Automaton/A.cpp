#include <iostream>
#include <vector>
#include <map>

using namespace std;

#define FILE freopen ("problem1.in", "r", stdin); freopen ("problem1.out", "w", stdout)

struct State {
    map <char, State*> connectedStates;
    bool isTerminal;

    State() : isTerminal (false) { }
};

int main() {
    FILE;
    string word, res;
    int n, m, k, terminalIndex, from, to;
    char symbol;
    cin >> word >> n >> m >> k;
    vector <State*> states (n + 1);
    for (int i = 1; i <= n; i++) {
        states[i] = new State ();
    }
    for (int i = 0; i < k; i++) {
        cin >> terminalIndex;
        states[terminalIndex]->isTerminal = true;
    }
    for (int i = 0; i < m; i++) {
        cin >> from >> to >> symbol;
        states[from]->connectedStates[symbol] = states[to];
    }
    State* current = states[1];
    for (const char &c : word) {
        if (current != nullptr) {
            current = current->connectedStates[c];
        } else {
            break;
        }
    }
    res = current == nullptr ? "Rejects" : (current->isTerminal ? "Accepts" : "Rejects");
    cout << res;
}
