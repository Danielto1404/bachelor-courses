#include <iostream>
#include <vector>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

using LLI = long long int;
using polynom = vector<LLI>;
const LLI MOD = 998244353;

LLI get_coefficient(polynom const &p, int i) {
    return i < p.size() ? p[i] : 0;
}

LLI apply_mod(LLI number) {
    LLI result = number % MOD;
    return result + (result >= 0 ? 0 : MOD);
}

polynom sum(polynom const &p, polynom const &q) {
    polynom result;
    for (int i = 0; i <= max(p.size(), q.size()); ++i) {
        result.push_back(apply_mod(get_coefficient(p, i) + get_coefficient(q, i)));
    }

    return result;
}

polynom multiply(polynom const &p, polynom const &q, int m) {
    polynom result;
    for (int i = 0; i < m; ++i) {
        LLI value = 0;
        for (int j = 0; j <= i; ++j)
            value = apply_mod(value + get_coefficient(p, j) * get_coefficient(q, i - j));
        result.push_back(value);
    }

    return result;
}

LLI modular_multiplicative_inverse(LLI numerator, LLI divisor) {
    numerator = apply_mod(numerator);
    divisor = apply_mod(divisor);
    while (numerator % divisor != 0)
        numerator += MOD;

    return numerator / divisor;
}

polynom scalar(LLI k, polynom const &p) {
    polynom result;
    for (LLI value : p)
        result.push_back(apply_mod(k * value));

    return result;
}

void print_answer(polynom const &coefficients, vector<polynom> &powers) {
    polynom result;
    for (int i = 0; i < coefficients.size(); ++i)
        result = sum(result, scalar(coefficients[i], powers[i]));

    for (int i = 0; i < powers.size(); ++i)
        cout << result[i] << ' ';

    cout << '\n';
}

int main() {
    c_boost;

    int n, m;
    cin >> n >> m;

    polynom coefficients(n + 1);
    polynom ans(m, 0);
    vector<polynom> powers(m);

    for (int i = 0; i < n + 1; ++i) {
        cin >> coefficients[i];
    }

    // Make table of powers of the given polynom
    polynom simple_one(m, 0);
    simple_one.front() = 1;
    powers.front() = simple_one;
    for (int i = 1; i < m; ++i)
        powers[i] = multiply(powers[i - 1], coefficients, m);

    // SQRT(1+x) = 1 + x * Choose(1/2, 1) + x^2 * Choose(1/2, 2) + ...
    polynom sqrt_coefficients(m);
    sqrt_coefficients.front() = 1;
    for (int i = 1; i < m; ++i) {
        sqrt_coefficients[i] = apply_mod(sqrt_coefficients[i - 1] *
                                         modular_multiplicative_inverse(
                                                 3 - 2 * i, 2 * i
                                         ));
    }

    // EXP(P(X)) = 1 + x / 1! + x^2 / 2! + x^3 / 3! + ...
    polynom exp_coefficients(m);
    exp_coefficients.front() = 1;
    for (int i = 1; i < m; ++i) {
        exp_coefficients[i] = apply_mod(exp_coefficients[i - 1] *
                                        modular_multiplicative_inverse(1, i));
    }

    // LN(1+X) = x - x^2 / 2 + x^3 / 3 - x^4 / 4 + ...
    polynom ln_coefficients(m);
    ln_coefficients.front() = 0;
    for (int i = 1; i < m; ++i) {
        LLI numerator = i % 2 == 0 ? -1 : 1;
        ln_coefficients[i] = modular_multiplicative_inverse(numerator, i);
    }

    print_answer(sqrt_coefficients, powers);
    print_answer(exp_coefficients, powers);
    print_answer(ln_coefficients, powers);
}
