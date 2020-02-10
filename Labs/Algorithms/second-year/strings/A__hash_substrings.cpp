#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

const int RANDOM_X = 1239;

void solve(vector <int>& hashes, vector <int>& powers) {
    int l1, r1, l2, r2;
    cin >> l1 >> r1 >> l2 >> r2;
    --l1, --r1, --l2, --r2;
    if (r1 - l1 != r2 - l2) {
        cout << "No\n";
        return;
    }

    int firstHash = hashes[r1];
    int secondHash = hashes[r2];
    if (l1 != 0) firstHash -= hashes[l1 - 1];
    if (l2 != 0) secondHash -= hashes[l2 - 1];

    bool isEqual = (firstHash * powers[l2] == secondHash * powers[l1]);
    cout << (isEqual ? "Yes" : "No") << '\n';
}

int main() {
    c_boost;
    string s;
    cin >> s;
    int SZ = s.length();
    vector <int> powers(SZ);
    vector <int> hashes(SZ);

    powers[0] = 1;
    hashes[0] = s[0];

    for (int i = 1; i < SZ; ++i) {
        powers[i] = powers[i - 1] * RANDOM_X;
        hashes[i] = hashes[i - 1] + powers[i] * s[i];
    }

    int t;
    cin >> t;
    while (t--)
        solve(hashes, powers);
}
