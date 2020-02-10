#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

int main() {
    c_boost;
    string s;
    cin >> s;
    vector <int> prefix(s.length(), 0);

    for (size_t i = 1; i < s.length(); ++i) {
        int k = prefix[i - 1];
        while (k > 0) {
            if (s[k] == s[i]) break;
            k = prefix[k - 1];
        }

        if (s[k] == s[i]) ++k;
        prefix[i] = k;
    }

    for (int p: prefix)
        cout << p << ' ';
}
