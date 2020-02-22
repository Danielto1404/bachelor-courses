#include <iostream>
#include <sstream>
#include <iterator>
#include <vector>

using namespace std;

typedef unsigned long long UL;

int main() {
    string first, second;
    getline(cin, first);
    getline(cin, second);
    UL left;
    UL right;
    istringstream fi(first);
    istringstream se(second);
    vector<string> cards((istream_iterator<string>(fi)), istream_iterator<string>());
    vector<string> boarders((istream_iterator<string>(se)), istream_iterator<string>());
    vector<vector<string>> result;
    vector<string> tmp;
    left = static_cast<UL>(stoi(boarders[0]));
    for (UL i = 0; i < left; i++) {
        tmp.push_back(cards[i]);
    }
    result.push_back(tmp);
    tmp = {};
    for (UL i = 0; i < boarders.size() - 1; ++i) {
        left = static_cast<UL>(stoi(boarders[i]));
        right = static_cast<UL>(stoi(boarders[i + 1]));
        for (UL j = left; j < right; j++) {
            tmp.push_back(cards[j]);
        }
        result.push_back(tmp);
        tmp = {};
    }
    right = static_cast<UL>(stoi(boarders[boarders.size() - 1]));
    for (UL i = right; i < cards.size(); i++) {
        tmp.push_back(cards[i]);
    }
    result.push_back(tmp);
    UL i = result.size() - 1;
    while (true) {
        for (const auto &j : result[i]) {
            cout << j << " ";
        }
        if (!i) break;
        i--;
    }
}
