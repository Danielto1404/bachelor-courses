#include <vector>
#include <string>
#include <iostream>

using namespace std;

#define FILE freopen("nfc.in", "r", stdin), freopen("nfc.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

struct State {
    int ind = 0;
    vector <char> end_letters;
    vector <pair <int, int>> transitions;

    State() = default;
};

const int MOD = 1000000007;

unsigned long long dp[26][100][100];

int main() {
    FILE;
    c_boost;
    int n, ind, left_sym_ind, right_sym_ind;
    char S;
    vector <State*> grammar_rules(26);
    for (auto& state: grammar_rules) {
        state = new State();
    }
    cin >> n >> S;
    for (int i = 0; i < n; i++) {
        char first;
        string arrow, last;
        cin >> first >> arrow >> last;
        ind = first - 'A';
        grammar_rules[ind]->ind = ind;
        if (last.size() == 1) {
            grammar_rules[ind]->end_letters.push_back(last[0]);
        } else {
            grammar_rules[ind]->transitions.emplace_back(last[0] - 'A', last[1] - 'A');
        }
    }
    string word;
    cin >> word;
    for (const auto& letter_params : grammar_rules)
        for (char end_sym : letter_params->end_letters)
            for (int i = 0; i < word.size(); i++)
                if (word[i] == end_sym)
                    dp[letter_params->ind][i][i]++;

    for (int i = 1; i < word.size(); i++)
        for (const auto& letter_gram : grammar_rules)
            for (int j = 0; j < word.size() - i; j++)
                for (const auto& pair : letter_gram->transitions)
                    for (int k = j; k < j + i; k++) {
                        left_sym_ind = pair.first;
                        right_sym_ind = pair.second;
                        ind = letter_gram->ind;
                        dp[ind][j][j + i] =
                                (dp[ind][j][j + i] + dp[left_sym_ind][j][k] * dp[right_sym_ind][k + 1][j + i]) % MOD;
                    }

    cout << dp[S - 'A'][0][word.size() - 1];
}
