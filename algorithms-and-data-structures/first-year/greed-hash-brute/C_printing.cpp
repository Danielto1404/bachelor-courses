#include <vector>
#include <iostream>
#include <algorithm>

#define FILE freopen("printing.in", "r", stdin), freopen("printing.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

struct Paper;
std::vector <int> pow10;
std::vector <Paper*> lists;

struct Paper {
    Paper(long long price, int cnt) : price(price), cnt(cnt) {}
    long long price;
    int cnt;
};

long long find_cheapest_price(int index, int n) {
    if (n == 0) {
        return 0;
    }
    Paper* paper = lists[index];
    long long cur_min_price = paper->price * (n / paper->cnt);
    return cur_min_price + std::min(paper->price, find_cheapest_price(index + 1, n % paper->cnt));
}

int main() {
    c_boost;
    FILE;
    int n, current_cost;
    lists.resize(7);
    int pow = 1;
    for (int i = 0; i < 7; ++i, pow *= 10) {
        pow10.push_back(pow);
    }
    std::cin >> n;
    for (int i = 0; i < lists.size(); ++i) {
        std::cin >> current_cost;
        lists[i] = new Paper(current_cost, pow10[i]);
    }
    std::sort(lists.begin(), lists.end(), [](const Paper* const a, const Paper* const b) {
        return a->price * b->cnt < b->price * a->cnt;
    });
    std::cout << find_cheapest_price(0, n);
}
