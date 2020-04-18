#include <iostream>
#include <vector>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

using LLI = long long int;
using polynom = vector<LLI>;
const LLI MOD = 104857601;

LLI get_coefficient(polynom const &p, int i) {
    return i < p.size() ? p[i] : 0;
}

LLI apply_mod(LLI number) {
    LLI result = number % MOD;
    return result + (result >= 0 ? 0 : MOD);
}

polynom multiply(polynom const &p, polynom const &q) {
    polynom result;
    for (int i = 0; i <= (int) p.size() + q.size(); ++i) {
        LLI temp = 0;
        for (int j = 0; j <= i; ++j)
            temp = apply_mod(temp + get_coefficient(p, j) * get_coefficient(q, i - j));
        result.push_back(temp);
    }

    return result;
}

polynom get_negative(polynom const &p) {
    polynom result;
    for (int i = 0; i < p.size(); ++i)
        result.push_back(i % 2 == 0 ? p[i] : -p[i]);

    return result;
}

polynom sqrt(polynom const &p) {
    polynom result;
    for (int i = 0; i <= p.size(); i += 2)
        result.push_back(p[i]);

    return result;
}

polynom save(polynom const &p, LLI shift) {
    polynom result(p.size());
    for (int i = 0; i < p.size(); ++i)
        result[i] = (i < p.size() / 2 ? p[2 * i + shift] : 0);

    return result;
}

int main() {
    c_boost;
    LLI k, n;

    cin >> k >> n;

    --n;

    polynom initial_values(2 * k, 0);
    polynom denominator(k + 1);

    for (int i = 0; i < k; ++i)
        cin >> initial_values[i];

    denominator.front() = 1;
    for (int i = 1; i <= k; ++i) {
        cin >> denominator[i];
        denominator[i] *= -1;
    }

    while (n >= k) {

        for (int i = k; i < 2 * k; ++i)
            for (int j = 1; j <= k; ++j)
                initial_values[i] = apply_mod(
                        initial_values[i] - denominator[j] * initial_values[i - j]
                );

        initial_values = save(initial_values, n % 2);
        polynom negative = get_negative(denominator);
        polynom product = multiply(negative, denominator);
        denominator = sqrt(product);

        n = n / 2;
    }
    cout << initial_values[n];
}
