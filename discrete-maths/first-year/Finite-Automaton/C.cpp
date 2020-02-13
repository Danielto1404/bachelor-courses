#include <iostream>
#include <vector>
#include <set>

using namespace std;

#define FILE freopen ("problem3.in", "r", stdin); freopen ("problem3.out", "w", stdout)
#define white 0
#define gray 1
#define black 2

struct State {
    char visited_color;
    bool has_way_to_terminal;
    long long cnt;
    vector <int> reverse_transitions, transitions;

    State () : visited_color(white), cnt(0), has_way_to_terminal(false) {}
};

const int MOD = 1000000007;

vector <State*> aut;

void mark_reachable (int v) {
    aut[v]->has_way_to_terminal = true;
    for (int i : aut[v]->reverse_transitions) {
        if (!aut[i]->has_way_to_terminal) {
            mark_reachable(i);
        }
    }
}


long long cnt_words (int v) {
    switch (aut[v]->visited_color) {
        case white :
            aut[v]->visited_color = gray;
            for (int next_state : aut[v]->transitions) {
                if (aut[next_state]->has_way_to_terminal) {
                    aut[v]->cnt = (aut[v]->cnt + cnt_words(next_state)) % MOD;
                }
            }
            aut[v]->visited_color = 2;
            return aut[v]->cnt;
        case gray :
            cout << -1;
            exit(0);
        case black:
            return aut[v]->cnt;
        default:
            exit(0);
    }
}

int main () {
    FILE;
    int n, m, k, from, to, terminalIndex;
    char c;
    cin >> n >> m >> k;
    aut.resize(n + 1);
    for (auto &state : aut) {
        state = new State();
    }
    int terminals[k];
    for (int i = 0; i < k; ++i) {
        cin >> terminalIndex;
        terminals[i] = terminalIndex;
        aut[terminalIndex]->cnt = 1;
    }
    for (int i = 0; i < m; ++i) {
        cin >> from >> to >> c;
        aut[from]->transitions.push_back(to);
        aut[to]->reverse_transitions.push_back(from);
    }
    for (int i : terminals) {
        mark_reachable(i);
    }
    cout << cnt_words(1) % MOD;
}
