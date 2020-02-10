#include <iostream>
#include <vector>
#include <set>
#include <string>

#define FILE freopen("epsilon.in", "r", stdin), freopen("epsilon.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

int n, offset = 4;
set <char> eps_states;
vector <string> rules;

bool isEpsState (const char c) {
    return eps_states.count(c) != 0;
}

void find_eps_states () {
    for (const string &grammar : rules) {
        bool is_all_eps = true;
        for (int j = (offset + 1); j < grammar.size(); j++) {
            if (!isEpsState(grammar[j]))
                is_all_eps = false;
        }
        char first_symbol = grammar[0];
        if (is_all_eps && (!isEpsState(first_symbol))) {
            eps_states.insert(first_symbol);
            find_eps_states();
        }
    }
}

int main () {
    FILE;
    c_boost;
    char S;
    cin >> n >> S >> ws;
    rules.resize(n);
    for (string &grammar : rules) {
        getline(cin, grammar);
        if (grammar.size() == offset) {
            eps_states.insert(grammar[0]);
        }
    }
    find_eps_states();
    for (const char eps : eps_states) {
        cout << eps << " ";
    }
}
