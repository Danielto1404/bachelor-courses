#include <iostream>
#include <algorithm>
#include <random>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

using LL = unsigned long long;

LL modMultiply(LL a, LL b, LL mod) {
    LL result = 0;
    while (b) {
        if (b % 2 == 1) result = (result + a) % mod;
        a = (2 * a) % mod;
        b /= 2;
    }
    return result;
}

pair<LL, LL> getMax2Pow(LL n) {
    LL cnt = 0;
    while (n % 2 == 0) {
        cnt += 1;
        n /= 2;
    }
    return {n, cnt};
}

LL fastPower(LL x, LL p, LL mod) {
    if (p == 1) return x % mod;
    if (p % 2 == 0) {
        LL half = fastPower(x, p / 2, mod);
        return modMultiply(half, half, mod);
    }
    return modMultiply(x, fastPower(x, p - 1, mod), mod);
}

bool fermatsTest(LL n) {
    if (n == 2 || n == 3) return true;
    if (n % 2 == 0 || n == 1) return false;

    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<LL> dis(2, n - 2);

    pair<LL, LL> odd_powers = getMax2Pow(n - 1);
    LL S = odd_powers.first;
    LL powers = odd_powers.second;
    int test_cnt = 10;

    for (int i = 0; i < test_cnt; ++i) {
        LL x = dis(gen);
        x = fastPower(x, S, n);
        if (x == 1 || x == n - 1) continue;

        bool goNext = false;
        for (int j = 0; j < powers; ++j) {
            x = modMultiply(x, x, n);
            if (x == 1) return false;
            if (x == n - 1) {
                goNext = true;
                break;
            }
        }
        if (!goNext) return false;
    }
    return true;
}

int main() {
    c_boost;
    int n;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        LL x;
        cin >> x;
        cout << (fermatsTest(x) ? "YES" : "NO") << '\n';
    }
}
