#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

int main() {
    c_boost;

    string s;
    int left = 0, right = 0;
    cin >> s;
    int SZ = (int) s.length();
    vector <int> zFunction(SZ, 0);

    for (int i = 1; i < SZ; ++i) {
        auto fst = right - i;
        auto snd = zFunction[i - left];
        zFunction[i] = max(0, min(fst, snd));

        while (i + zFunction[i] < SZ and s[zFunction[i]] == s[i + zFunction[i]]) {
            zFunction[i]++;
        }
        if (i + zFunction[i] > right) {
            left = i;
            right = i + zFunction[i];
        }
    }
    for (size_t i = 1; i < zFunction.size(); ++i)
        cout << zFunction[i] << ' ';
}
