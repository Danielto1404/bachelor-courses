#include <iostream>
#include <vector>
#include <map>
#include <set>

using namespace std;

#define FILE freopen ("problem2.in", "r", stdin); freopen ("problem2.out", "w", stdout)

struct State {
    map <char, set <State*>> connectedStates;
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
        states[from]->connectedStates[symbol].insert (states[to]);
    }
    set <State*> curStates;
    curStates.insert (states[1]);
    for (const char &c : word) {
        if (!curStates.empty ()) {
            set <State*> newStates;

            // Перебираем текущее множество состояний
            for (auto const &state : curStates) {

                // Делаем переходы по каждому состоянию и символу в новые состояния
                for (auto const &trState : state->connectedStates[c]) {
                    if (trState != nullptr) {
                        newStates.insert (trState);
                    }
                }
            }
            curStates = newStates;
        } else {
            cout << "Rejects";
            return 0;
        }
    }
    for (auto const &state : curStates) {
        if (state->isTerminal) {
            cout << "Accepts";
            return 0;
        }
    }
    cout << "Rejects";
}
