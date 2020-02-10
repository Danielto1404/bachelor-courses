#include <iostream>
#include <vector>
#include <set>
#include <string>

#define FILE freopen("useless.in", "r", stdin), freopen("useless.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

int n, offset = 4;
set <char> reachable;
set <char> generating;
set <char> states;
set <char> useless;
vector <bool> cycle;

vector <string> rules;

bool isUpper (char c) {
    return c >= 'A' && c <= 'Z';
}

bool isAllTerminals (const string &grammar) {
    // Check empty transition
    if (offset == grammar.size()) {
        return true;
    }
    bool isAllTerminal = true;
    for (int i = (offset + 1); i < grammar.size(); i++) {
        char symbol = grammar[i];
        if (isUpper(symbol)) {
            states.insert(symbol);
            isAllTerminal = false;
        }
    }
    return isAllTerminal;
}

bool is_in_reachable (char c) {
    return reachable.count(c) != 0;
}

bool is_in_generating (char c) {
    return generating.count(c) != 0;
}

void find_generating () {
    for (const string &grammar : rules) {
        bool is_all_generating = true;
        for (int i = (offset + 1); i < grammar.size(); i++)
            if (isUpper(grammar[i]))
                if (!is_in_generating(grammar[i])) {
                    is_all_generating = false;
                    break;
                }

        char first_symbol = grammar[0];
        if (is_all_generating && (!is_in_generating(first_symbol))) {
            generating.insert(first_symbol);
            find_generating();
        }
    }
}

void find_reachable () {
    for (const string &grammar : rules) {
        bool isChanged = false;
        if (is_in_reachable(grammar[0]))
            for (int i = (offset + 1); i < grammar.size(); i++) {
                char symbol = grammar[i];
                if (isUpper(symbol)) {
                    if (grammar[0] == symbol) {
                        reachable.erase(symbol);
                        cycle[symbol - 'A'] = true;
                        break;
                    }
                    if (!is_in_reachable(symbol) && !cycle[symbol - 'A']) {
                        isChanged = true;
                        reachable.insert(symbol);
                    }
                }
            }
        if (isChanged) {
            find_reachable();
        }
    }
}

int main () {
    //  FILE;
    c_boost;
    char S;
    cin >> n >> S >> ws;
    rules.resize(n);
    cycle.resize(26);
    reachable.insert(S);
    for (string &grammar : rules) {
        getline(cin, grammar);
        states.insert(grammar[0]);
        if (isAllTerminals(grammar)) {
            generating.insert(grammar[0]);
        }
    }

    find_generating();
    find_reachable();

    for (char c : states) {
        if (!is_in_reachable(c) || !is_in_generating(c)) {
            useless.insert(c);
        }
    }
    for (char c : useless) {
        cout << c << " ";
    }
}
