#include <iostream>
#include <vector>
#include <map>
#include <string>

#define FILE freopen("automaton.in", "r", stdin), freopen("automaton.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

struct State {
    vector <char> end_letters;
    map <char, vector <int>> transitions;

    State () = default;
};

vector <State*> aut;
string word;

bool contains (char c, vector <char> &letters) {
    return find(letters.begin(), letters.end(), c) != letters.end();
}

bool is_in_grammar (int state, int rest_distance, int index) {
    if (rest_distance == 1) {
        return contains(word[index], aut[state]->end_letters);
    } else {
        vector <int> next_states = aut[state]->transitions[word[index]];
        if (next_states.empty()) {
            return false;
        } else {
            for (int next : next_states) {
                if (is_in_grammar(next, rest_distance - 1, index + 1))
                    return true;
            }
            return false;
        }
    }
}


int main () {
    FILE;
    c_boost;
    int n, m, from, to;
    char S, symbol;
    string first, arrow, last;
    cin >> n >> S;
    int start_index = S - 'A';
    aut.resize(26);
    for (auto &state : aut) {
        state = new State();
    }
    for (int i = 0; i < n; i++) {
        cin >> first >> arrow >> last;
        from = first[0] - 'A';
        symbol = last[0];
        if (last.length() == 2) {
            to = last[1] - 'A';
            aut[from]->transitions[symbol].push_back(to);
        } else {
            aut[from]->end_letters.push_back(symbol);
        }
    }
    cin >> m;
    for (int i = 0; i < m; i++) {
        cin >> word;
        cout << ((is_in_grammar(start_index, word.length(), 0)) ? "yes" : "no") << '\n';
    }
}

// Run with 'Microsoft Visual C++ 2017' //
