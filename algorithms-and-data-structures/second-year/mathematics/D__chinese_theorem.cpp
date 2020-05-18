#include <iostream>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using LL = long long;

struct triple {
    LL d, x, y;
};

triple gcd(LL a, LL b) {
    if (a == 0)
        return triple{.d = b, .x = 0, .y = 1};

    triple coefficients = gcd(b % a, a);
    return triple{.d = coefficients.d, .x = coefficients.y - (b / a) * coefficients.x, .y = coefficients.x};
}

int main() {
    c_boost;
    LL a, b, n, m;
    cin >> a >> b >> n >> m;
    LL M = n * m;
    LL ya = gcd(m, n).x;
    LL yb = gcd(n, m).x;
    LL x0 = m * ya * a + n * yb * b;
    LL x = x0 % M;
    if (x < 0) x += M;
    cout << x;
}
