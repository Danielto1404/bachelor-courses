#include <iostream>
#include <vector>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

using LL = long long;
const LL mod = 1e9 + 7;

int main() {
    c_boost;
    int k, m;
    LL min_ci = mod, x;
    cin >> k >> m;

    ++m;
    vector<int> can_be_root(m, false);
    vector<LL> sub_trees(m, 0);
    vector<LL> tree_cnt(m, 0);

    for (int i = 0; i < k; ++i) {
        cin >> x;
        min_ci = min(min_ci, x);
        can_be_root[x] = true;
    }

    sub_trees[0] = 1;
    sub_trees[min_ci] = 2;

    tree_cnt[0] = 1;
    tree_cnt[min_ci] = 1;

    for (LL i = min_ci + 1; i < m; ++i) {
        for (LL j = 1; j <= i; ++j)
            if (can_be_root[j])
                tree_cnt[i] = (tree_cnt[i] + sub_trees[i - j]) % mod;

        for (LL j = 0; j <= i; ++j)
            sub_trees[i] = (sub_trees[i] +
                            (tree_cnt[j] *     // LEFT  SUBTREE
                             tree_cnt[i - j])  // RIGHT SUBTREE
                            % mod) % mod;
    }
    for (LL i = 1; i < m; ++i)
        cout << tree_cnt[i] << ' ';
}
