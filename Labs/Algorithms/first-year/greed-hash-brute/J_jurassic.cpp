#include <vector>
#include <iostream>

#define FILE freopen("jurassic.in", "r", stdin), freopen("jurassic.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

unsigned get_mask(char c) {
    return (1u << static_cast<unsigned>(c - 'A'));
}

int main() {
    c_boost;
    FILE;
    unsigned n, max_mask = 0, max_cnt = 0;
    std::string letters;
    std::cin >> n;
    std::vector <unsigned> lets(n);
    for (unsigned& mask : lets) {
        std::cin >> letters;
        for (char c : letters)
            mask |= get_mask(c);
    }
    for (int mask = 1; mask < (1u << n); ++mask) {
        unsigned cur_lets = 0;
        unsigned bits = mask;
        unsigned cnt = __builtin_popcount(mask);  // Кол-во не нулевых битов //
        for (int index = 0; bits > 0; ++index, bits = bits >> 1u)
            if (bits & 1u)
                // Должно быть четное кол-во букв //
                cur_lets ^= lets[index];

        if (cur_lets == 0 && cnt > max_cnt) {
            max_cnt = cnt;
            max_mask = mask;
        }
    }
    std::cout << max_cnt << '\n';
    for (int index = 0; max_mask > 0; ++index, max_mask = max_mask >> 1u)
        if (max_mask & 1u)
            std::cout << index + 1 << " ";
}
