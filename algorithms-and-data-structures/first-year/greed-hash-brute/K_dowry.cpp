#include <vector>
#include <iostream>
#include <algorithm>

#define FILE freopen("dowry.in", "r", stdin), freopen("dowry.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define ULL unsigned long long

struct DiamondSet;

std::vector <ULL> weights;
std::vector <ULL> costs;
ULL cnt;

struct DiamondSet {
    DiamondSet(ULL w, ULL c, int mask) : weight(w), cost(c), bit_mask(mask) {};
    ULL weight, cost;
    int bit_mask;
};

int least_upper_bound(int l, int r, long long bound, std::vector <DiamondSet*>& arr) {
    int m = (l + r) / 2;
    if (arr[m]->weight == bound || m == r || m == l) { return m; }
    if (arr[m]->weight > bound) {
        return least_upper_bound(l, m, bound, arr);
    } else {
        return least_upper_bound(m, r, bound, arr);
    }
}

int least_upper_bound(long long bound, std::vector <DiamondSet*>& arr) {
    return least_upper_bound(0, arr.size(), bound, arr);
}

std::string get_str(unsigned mask, int offset) {
    std::string ans;
    for (int index = 1 + offset; mask > 0; ++index, mask >>= 1u)
        if (mask & 1u) {
            ans += std::to_string(index) + " ";
            ++cnt;
        }
    return ans;
}

int main() {
    c_boost;
    FILE;
    unsigned n;
    ULL L, R, first_mask = 0, second_mask = 0, max_cost = 0;
    std::cin >> n >> L >> R;
    unsigned half_size = n / 2;
    unsigned rest_size = n - half_size;
    weights.resize(n);
    costs.resize(n);
    std::vector <DiamondSet*> first_set(1u << half_size);
    std::vector <DiamondSet*> sorted_first_set;

    for (int i = 0; i < n; ++i)
        std::cin >> weights[i] >> costs[i];

    // Make first part of diamonds //
    for (unsigned mask = 0; mask < first_set.size(); ++mask) {
        first_set[mask] = new DiamondSet(0, 0, mask);
        unsigned bits = mask;
        for (int index = 0; bits > 0; ++index, bits >>= 1u)
            if (bits & 1u) {
                first_set[mask]->weight += weights[index];
                first_set[mask]->cost += costs[index];
            }
    }

    std::sort(first_set.begin(), first_set.end(), [](const DiamondSet* const a, const DiamondSet* const b) {
        if (a->weight < b->weight) {
            return true;
        } else if (a->weight > b->weight) {
            return false;
        }
        return a->cost > b->cost;
    });

    // Fill sorted subset (delete elements with less price and bigger weight) //
    ULL cur_max = 0;
    for (DiamondSet* const diamond : first_set)
        if (diamond->cost > cur_max || cur_max == 0) {
            cur_max = diamond->cost;
            sorted_first_set.push_back(diamond);
        }

    // Start bruce second part of diamonds //
    for (unsigned mask = 1; mask < (1u << rest_size); ++mask) {
        ULL weight = 0, cost = 0;
        unsigned bits = mask;
        for (int index = 0; bits > 0; ++index, bits >>= 1u)
            if (bits & 1u) {
                weight += weights[half_size + index];
                cost += costs[half_size + index];
            }

        unsigned index = least_upper_bound(R - weight, sorted_first_set);
        DiamondSet* added = sorted_first_set[index];
        weight += added->weight;
        cost += added->cost;

        if (weight >= L && weight <= R && cost > max_cost) {
            max_cost = cost;
            first_mask = added->bit_mask;
            second_mask = mask;
        }
    }
    std::string ans = get_str(first_mask, 0) + get_str(second_mask, half_size);
    std::cout << (ans.empty() ? "0" : (std::to_string(cnt) + '\n' + ans));
}
