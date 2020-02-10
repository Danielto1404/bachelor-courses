#include <iostream>
#include <vector>
#include <set>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

bool checkSubSet(unsigned superSet, vector<vector<unsigned>> &sets) {
    for (unsigned subSet = superSet; subSet > 0; subSet = (subSet - 1) & superSet) {
        bool isSubSetFound = false;
        unsigned sz = __builtin_popcount(subSet);
        for (unsigned set: sets[sz])
            if (set == subSet) {
                isSubSetFound = true;
                break;
            }
        if (!isSubSetFound) return false;
    }
    return true;
}

bool existChangingElement(unsigned smallSet, unsigned bigSet, vector<vector<unsigned>> &sets) {
    set<unsigned> difference;
    unsigned smallSz = __builtin_popcount(smallSet);

    for (unsigned bits = bigSet, index = 0; bits > 0; ++index, bits >>= 1u)
        if (bits & 1u) difference.insert(index);

    for (unsigned bits = smallSet, index = 0; bits > 0; ++index, bits >>= 1u)
        if (bits & 1u) difference.erase(index);

    for (unsigned x: difference)
        for (unsigned set: sets[smallSz + 1])
            if (set == (smallSet | (1u << x)))
                return true;

    return false;
}

int main() {
    c_boost;
    file_open("check");
    unsigned n, m, sz, x;
    cin >> n >> m;
    vector<vector<unsigned>> sets(n + 1);
    for (int i = 0; i < m; ++i) {
        cin >> sz;
        sets[sz].push_back(0);
        for (int j = 0; j < sz; ++j) {
            cin >> x;
            sets[sz].back() |= (1u << (x - 1));
        }
    }
    // 1st theorem (∅ ∈ I)
    if (sets.front().empty()) {
        cout << "NO";
        return 0;
    }
    // 2nd theorem (if A ∈ I, B ⊂ A => B ∈ I)
    for (int i = 1; i < sets.size(); ++i)
        for (int set: sets[i])
            if (!checkSubSet(set, sets)) {
                cout << "NO";
                return 0;
            }
    // 3td theorem (if A,B ∈ I, |A| > |B| => ∃x ∈ A\B : B ∪ {x} ∈ I)
    for (int i = 0; i < sets.size() - 1; ++i)
        for (unsigned smallSet: sets[i])
            for (int j = i + 1; j < sets.size(); ++j)
                for (unsigned bigSet: sets[j])
                    if (!existChangingElement(smallSet, bigSet, sets)) {
                        cout << "NO";
                        return 0;
                    }

    cout << "YES";
}
