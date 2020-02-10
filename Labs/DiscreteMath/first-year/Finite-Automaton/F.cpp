#include <iostream>
#include <vector>
#include <map>
#include <set>

using namespace std;

#define FILE freopen ("isomorphism.in", "r", stdin); freopen ("isomorphism.out", "w", stdout)

using namespace std;

struct State {
    map <char, int> transitions;
    set <char> lets;
    bool isTerminal;

    State () : isTerminal(false) {}
};

int MAX_N = 100000;
vector <State*> u_aut, v_aut;
vector <bool> isVisited;

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
        automaton[from]->lets.insert(symbol);
    }
}

bool dfsIsomorphism (int u, int v) {
    isVisited[u] = true;
    if (u_aut[u]->isTerminal != v_aut[v]->isTerminal) {
        return false;
    }
    bool res = true;
    for (const char c : u_aut[u]->lets) {
        int u_next = u_aut[u]->transitions[c];
        int v_next = v_aut[v]->transitions[c];
        if (!isVisited[u_next]) {
            res = res && dfsIsomorphism(u_next, v_next);
        }
    }
    return res;
}

int main () {
    FILE;
    readAutomaton(u_aut);
    readAutomaton(v_aut);
    isVisited.assign(MAX_N + 1, false);
    cout << (dfsIsomorphism(1, 1) ? "YES" : "NO");
}
