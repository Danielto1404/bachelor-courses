#include <vector>
#include <algorithm>
#include <iterator>
#include <complex>
#include <iostream>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

using LL = long long;
using complex_double = complex<double>;
using complex_poly = vector<complex_double>;

const double PI = atan(1) * 4;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

struct Number {
    vector<int> digits;

    Number() = default;

    explicit Number(const string &str_number) {
        digits.assign(str_number.size(), 0);
        for (size_t i = 0; i < str_number.size(); ++i)
            digits[i] = str_number[i] - '0';
    }
};

void forward_transformation(complex_poly &poly, int n, bool inverse) {
    if (n == 1)
        return;

    complex_poly even = complex_poly(n / 2);
    complex_poly odd = complex_poly(n / 2);

    for (int i = 0; i < n / 2; ++i) {
        even[i] = poly[2 * i];
        odd[i] = poly[2 * i + 1];
    }

    forward_transformation(even, n / 2, inverse);
    forward_transformation(odd, n / 2, inverse);

    double angle = 2 * PI / n;
    angle = inverse ? -angle : angle;
    complex_double point(1, 0);
    complex_double multiplier(cos(angle), sin(angle));
    for (size_t i = 0; i < n; ++i, point *= multiplier)
        poly[i] = even[i % (n / 2)] + point * odd[i % (n / 2)];
}

Number multiply(Number &a, Number &b) {
    Number product;
    int degree = pow(2, static_cast<int>((ceil(log2(max(a.digits.size(), b.digits.size()) * 2)))));
    a.digits.resize(degree);
    b.digits.resize(degree);
    product.digits.resize(degree);

    complex_poly fst_poly = complex_poly(a.digits.begin(), a.digits.end());
    complex_poly snd_poly = complex_poly(b.digits.begin(), b.digits.end());

    forward_transformation(fst_poly, degree, false);
    forward_transformation(snd_poly, degree, false);

    complex_poly c;
    for (int i = 0; i < degree; ++i)
        c.push_back(fst_poly[i] * snd_poly[i]);

    forward_transformation(c, degree, true);
    for (size_t i = 0; i < degree; ++i)
        product.digits[i] = (int) (c[i].real() / degree + 0.5);
    
    while (product.digits[degree - 1] == 0 && degree > 1)
        --degree;

    product.digits.resize(degree);
    return product;
}

int main() {
    c_boost;
    file_open("duel");
    string s;
    cin >> s;
    Number s_num = Number(s);
    Number answer = multiply(s_num, s_num);
    LL result = 0;
    for (int i = 0; i < s.size(); ++i)
        if (s[i] == '1')
            result += answer.digits[2 * i] - 1;

    cout << result / 2;
}
