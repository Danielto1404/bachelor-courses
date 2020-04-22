#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

using LL = long long;
using polynom = vector<LL>;
using matrix = vector<polynom>;

LL power(LL number, int power) {
    LL powered = 1;
    for (int i = 0; i < power; ++i)
        powered *= number;

    return powered;
}

polynom expand(LL n, LL value, matrix const &choose) {
    polynom result;
    for (int k = 0; k <= n; ++k) {
        // C(n,0) + C(n,1) * (value * t) ^ 1 + C(n,2) * (value * t) ^ 2 + C(n,3) * (value * t) ^ 3 + ...
        int sign = k % 2 == 0 ? 1 : -1;
        result.push_back(sign * choose[n][k] * power(value, k));
    }
    return move(result);
}

LL recurrent_value(polynom &initial, polynom &coefficients, int k) {
    LL value = 0;
    for (int i = 0; i < k; ++i)
        value += coefficients[i] * initial[k - i - 1];
    return value;
}

LL calculate_initial(int index, polynom &p, polynom &r_powers) {
    LL value = 0;
    for (int to_power = 0; to_power < p.size(); ++to_power)
        value += p[to_power] * power(index, to_power);

    return value * r_powers[index];
}

void println_polynom(polynom const &p) {
    cout << p.size() - 1 << '\n';
    for (LL coefficient: p)
        cout << coefficient << ' ';
    cout << '\n';
}

int main() {
    c_boost;

    LL d, r;
    cin >> r >> d;
    polynom p(d + 1);
    for (LL &coefficient : p)
        cin >> coefficient;

    vector<LL> r_powers(d + 2, 1);
    for (int i = 1; i < r_powers.size(); ++i)
        r_powers[i] = r_powers[i - 1] * r;

    // Pre-calculate C(i, j)
    int MAX_DEGREE = 11;
    matrix choose(MAX_DEGREE + 1, vector<LL>(MAX_DEGREE + 1, 0));

    polynom first_row(MAX_DEGREE + 1, 0);
    first_row.front() = 1;
    choose.front() = first_row;

    for (int i = 1; i < choose.size(); ++i) {
        choose[i][0] = 1;
        choose[i][i] = 1;
        for (int j = 1; j < choose.size(); ++j) {
            choose[i][j] = choose[i - 1][j] + choose[i - 1][j - 1];
        }
    }

    polynom Q = expand(d + 1, r, choose);
    polynom coefficients;
    for (int i = 1; i < Q.size(); ++i)
        coefficients.push_back(-Q[i]);

    polynom P(coefficients.size(), 0);
    polynom initial;
    for (int i = 0; i < P.size(); ++i)
        initial.push_back(calculate_initial(i, p, r_powers));

    for (int i = 0; i < P.size(); ++i)
        P[i] = initial[i] - recurrent_value(initial, coefficients, i);

    println_polynom(P);
    println_polynom(Q);
}
