#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

void build_z_function(const string& s, vector <long>& z_function) {
    long SZ = (long) z_function.size();
    long left = 0, right = 0;
    for (long i = 1; i < SZ; ++i) {
        long fst = right - i;
        long snd = z_function[i - left];
        z_function[i] = max(0L, min(fst, snd));

        while (i + z_function[i] < SZ and s[z_function[i]] == s[i + z_function[i]]) {
            z_function[i]++;
        }
        if (i + z_function[i] > right) {
            left = i;
            right = i + z_function[i];
        }
    }
}

int main() {
    c_boost;

    string s, t;
    cin >> s >> t;
    const int S_LENGTH = s.length(), T_LENGTH = t.length();
    vector <long> z_function(S_LENGTH + T_LENGTH + 1, 0);
    build_z_function(s + "#" + t, z_function);

    vector <long> ans;
    for (size_t i = S_LENGTH; i < z_function.size(); ++i)
        if (z_function[i] == S_LENGTH) ans.push_back(i - S_LENGTH);

    cout << ans.size() << '\n';
    for_each(ans.begin(), ans.end(), [](int value) { cout << value << ' '; });
}
