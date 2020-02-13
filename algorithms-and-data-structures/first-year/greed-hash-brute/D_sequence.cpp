#include <vector>
#include <iostream>
#include <algorithm>

#define FILE freopen("sequence.in", "r", stdin), freopen("sequence.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define num first
#define index second

int main() {
    c_boost;
    FILE;
    int n, number, one_half_sum, sum = 0;
    std::cin >> n;
    std::vector <std::pair <int, int>> seq;
    for (int i = 0; i < n; ++i) {
        std::cin >> number;
        sum += number;
        seq.emplace_back(number, i + 1);
    }
    one_half_sum = sum / 2;
    if (sum % 2) {
        std::cout << "-1";
    } else {
        std::sort(seq.begin(), seq.end(), [](std::pair <int, int> a, std::pair <int, int> b) { return a.num > b.num; });
        int second_half_sum = one_half_sum;
        int cnt = 0;
        std::string ans;
        for (auto& element : seq) {
            if (second_half_sum >= element.num) {
                second_half_sum -= element.num;
                ans += std::to_string(element.index) + " ";
                ++cnt;
            } else
                one_half_sum -= element.num;
        }
        std::cout << (one_half_sum == 0 && second_half_sum == 0 ? (std::to_string(cnt) + '\n' + ans) : "-1");
    }
}
