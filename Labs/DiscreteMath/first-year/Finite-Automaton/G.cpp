#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <queue>

using namespace std;

#define FILE freopen ("equivalence.in", "r", stdin); freopen ("equivalence.out", "w", stdout)

using namespace std;

struct State {
    map <char, int> transitions;
    bool isTerminal;

    State () : isTerminal(false) {}
};

vector <State*> u_aut, v_aut;

void readAutomaton (vector <State*> &automaton) {
    int n, m, k, terminalIndex, from, to;
    char symbol;
    cin >> n >> m >> k;
    automaton.resize(n + 1);
    for (auto &node : automaton) {
        node = new State();
    }
    for (int i = 0; i < k; i++) {
        cin >> terminalIndex;
        automaton[terminalIndex]->isTerminal = true;
    }
    for (int i = 0; i < m; i++) {
        cin >> from >> to >> symbol;
        automaton[from]->transitions[symbol] = to;
    }
}

bool bfsEquivalence () {
    queue <pair <int, int>> Q;
    set <pair <int, int>> states;
    Q.emplace(1, 1);
    while (!Q.empty()) {
        pair <int, int> vertexes = Q.front();
        Q.pop();
        int u = vertexes.first;
        int v = vertexes.second;
        if (u_aut[u]->isTerminal != v_aut[v]->isTerminal) {
            return false;
        }
        for (char c = 'a'; c <= 'z'; ++c) {
            int u_next = u_aut[u]->transitions[c];
            int v_next = v_aut[v]->transitions[c];
            auto next_states = make_pair(u_next, v_next);
            if (states.count(next_states) == 0) {
                states.insert(next_states);
                Q.emplace(u_next, v_next);
            }
        }
    }
    return true;
}

int main () {
    FILE;
    readAutomaton(u_aut);
    readAutomaton(v_aut);
    cout << (bfsEquivalence() ? "YES" : "NO");
}
