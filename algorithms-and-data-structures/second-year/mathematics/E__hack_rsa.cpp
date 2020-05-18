#include <iostream>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using LL = long long;

LL modMultiply(LL a, LL b, LL mod) {
    LL result = 0;
    while (b) {
        if (b % 2 == 1) result = (result + a) % mod;
        a = (2 * a) % mod;
        b /= 2;
    }
    return result;
}

LL fastPower(LL x, LL p, LL mod) {
    if (p == 1) return x % mod;
    if (p % 2 == 0) {
        LL half = fastPower(x, p / 2, mod);
        return modMultiply(half, half, mod);
    }
    return modMultiply(x, fastPower(x, p - 1, mod), mod);
}

struct triple {
    LL d, x, y;
};

triple gcd(LL a, LL b) {
    if (a == 0)
        return triple{.d = b, .x = 0, .y = 1};

    triple coefficients = gcd(b % a, a);
    return triple{.d = coefficients.d, .x = coefficients.y - (b / a) * coefficients.x, .y = coefficients.x};
}

pair<LL, LL> findDecomposition(LL n) {
    LL i;
    for (i = 2; i * i < n; ++i) {
        if (n % i == 0)
            return {i, n / i};
    }
    return {i, i};
}

int main() {
    c_boost;
    LL n, e, C;
    cin >> n >> e >> C;
    pair<LL, LL> decomposition = findDecomposition(n);
    LL p = decomposition.first;
    LL q = decomposition.second;
    LL mod = (p - 1) * (q - 1);
    LL d = gcd(e, mod).x;
    LL M = fastPower(C, d < 0 ? d + mod : d, n);
    cout << M;
}
