#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

class Polynom {
public:
    using LL = long long;
    static const LL mod = 998244353;

    explicit Polynom(int deg) : deg(deg), coefficients(vector<LL>(deg + 1, 0)) {};

    LL &operator[](int index) {
        return coefficients[index];
    }

    [[nodiscard]] LL get_coefficient(int index) const {
        if (index > deg) return 0;
        return coefficients[index];
    }

    [[nodiscard]] Polynom add(const Polynom &other) const {
        Polynom res(max(deg, other.deg));
        for (int i = 0; i <= res.deg; ++i)
            res[i] = mod_add(get_coefficient(i), other.get_coefficient(i));

        return res;
    }

    [[nodiscard]] Polynom mul(const Polynom &other) const {
        Polynom res(deg + other.deg);
        for (int k = 0; k <= res.deg; ++k)
            for (int i = 0; i <= k; ++i) {
                LL mul = mod_mul(get_coefficient(i), other.get_coefficient(k - i));
                res[k] = mod_add(res[k], mul);
            }
        return res;
    }

    [[nodiscard]] Polynom div(const Polynom &other) const {
        Polynom res(999);
        for (int k = 0; k <= res.deg; ++k) {
            int sum = 0;
            for (int i = 0; i < k; ++i) {
                LL mul = mod_mul(res[i], other.get_coefficient(k - i));
                sum = mod_add(sum, mul);
            }
            LL sub = get_coefficient(k) - sum;
            res[k] = (sub < 0 ? (sub + mod) : sub) / other.get_coefficient(0);
        }
        return res;
    }

    friend std::ostream &operator<<(std::ostream &out, const Polynom &p) {
        for (int i = 0; i <= p.deg; ++i)
            out << p.coefficients[i] << ' ';

        return out;
    }

    int deg;
private:
    vector<LL> coefficients;

    static LL mod_add(LL x, LL y) {
        return (x + y) % mod;
    }

    static LL mod_mul(LL x, LL y) {
        return (x * y) % mod;
    }
};

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    Polynom P(n), Q(m);
    for (int i = 0; i <= n; ++i)
        cin >> P[i];
    for (int i = 0; i <= m; ++i)
        cin >> Q[i];

    auto r = P.add(Q);
    cout << r.deg << '\n' << r << '\n';
    r = P.mul(Q);
    cout << r.deg << '\n' << r << '\n';
    r = P.div(Q);
    cout << r;
}
