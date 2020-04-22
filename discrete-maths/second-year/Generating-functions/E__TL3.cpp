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

LL get_coefficient(polynom const &a, int index) {
    return index < a.size() ? a[index] : 0;
}

polynom sum(polynom const &a, polynom const &b) {
    polynom result;
    for (int i = 0; i < a.size(); ++i)
        result.push_back(get_coefficient(a, i) + get_coefficient(b, i));

    return move(result);
}

polynom scalar(LL k, polynom const &a) {
    polynom result;
    for (LL value : a)
        result.push_back(k * value);

    return move(result);
}

polynom expand(LL n, LL minus_value, matrix const &choose) {
    polynom result;
    for (int i = 0; i <= n; ++i) {
        int sign = i % 2 == 0 ? 1 : -1;
        result.push_back(sign * choose[n][i] * power(minus_value, i));
    }
    reverse(result.begin(), result.end());
    return move(result);
}

polynom expand_polynom(polynom const &a, int minus_value, matrix &choose) {
    polynom result(a.size());
    for (int i = 0; i < a.size(); ++i)
        result = sum(result, scalar(a[i], expand(i, minus_value, choose)));

    return move(result);
}

matrix transpose(const matrix &m) {
    matrix result(m.size(), polynom(m.size()));
    for (int i = 0; i < m.size(); ++i)
        for (int j = 0; j < m.size(); ++j)
            result[i][j] = m[j][i];

    return move(result);
}

polynom solve_system2(matrix &linear_system, polynom const &answer_vector) {
    return answer_vector;
}

LL determinant(matrix const &m, int n) {
    LL det = 0;
    if (n == 2) {
        return m[0][0] * m[1][1] - m[1][0] * m[0][1];
    } else {
        matrix sub_matrix(m.size(), polynom(m.size()));
        for (int x = 0; x < n; x++) {
            int sub_i = 0;
            for (int i = 1; i < n; i++) {
                int sub_j = 0;
                for (int j = 0; j < n; j++) {
                    if (j == x)
                        continue;
                    sub_matrix[sub_i][sub_j] = m[i][j];
                    sub_j++;
                }
                sub_i++;
            }
            LL sign = x % 2 == 0 ? 1 : -1;
            det += (sign * m[0][x] * determinant(sub_matrix, n - 1));
        }
    }
    return det;
}

polynom solve_system(matrix &linear_system, polynom const &answer_vector) {
    polynom result;

    // TODO ВОТ ЗДЕСЬ ЕБАНЫЙ ПИЗДЕЦ ПРОИСХОДИТ С ОПЕРДЕЛИТЕЛЕМ
    LL main_determinant = determinant(linear_system, linear_system.size());

    for (int j = 0; j < linear_system.size(); ++j) {
        polynom prev;
        for (int i = 0; i < linear_system.size(); ++i) {
            prev.push_back(linear_system[i][j]);
            linear_system[i][j] = answer_vector[i];
        }

        LL helper_determinant = determinant(linear_system, linear_system.size());
        result.push_back(helper_determinant / main_determinant);

        for (int i = 0; i < linear_system.size(); ++i) {
            linear_system[i][j] = prev[i];
        }
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
    for (int to_power = 0; to_power < p.size(); ++to_power) {
        value += p[to_power] * power(index, to_power);
    }
    return value * r_powers[index];
}

void out_polynom(polynom const &p) {
    cout << p.size() - 1 << '\n';
    for (LL coefficient: p)
        cout << coefficient << ' ';
    cout << '\n';
}

void out_matrix(matrix const &m) {
    for (const auto &line:m) {
        for (long long i : line)
            cout << i << ' ';
        cout << '\n';
    }
}

int main() {
    c_boost;

    LL d, r;
    cin >> r >> d;
    polynom p(d + 1);
    for (LL &coefficient : p)
        cin >> coefficient;

    vector<LL> r_powers(d + 2, 1);
    for (int i = 1; i <= r_powers.size(); ++i)
        r_powers[i] = r_powers[i - 1] * r;

    int MAX_DEGREE = 10;
    matrix choose(MAX_DEGREE + 1, vector<LL>(MAX_DEGREE + 1, 0));

    polynom first_row(MAX_DEGREE + 1, 0);
    first_row.front() = 1;
    choose.front() = first_row;

    for (int i = 1; i < MAX_DEGREE + 1; ++i) {
        choose[i][0] = 1;
        choose[i][i] = 1;
        for (int j = 1; j < MAX_DEGREE; ++j) {
            choose[i][j] = choose[i - 1][j] + choose[i - 1][j - 1];
        }
    }

    matrix linear_system(d + 1);
    for (int i = 0; i < linear_system.size(); ++i) {
        linear_system[i] = scalar(r_powers[d - i], (expand_polynom(p, i + 1, choose)));
    }

    linear_system = transpose(linear_system);
    polynom answer_vector = scalar(r_powers[d + 1], p);

    polynom P;
    polynom Q = {1};
    if (p.size() == 1) {
        Q.push_back(-1 * answer_vector.front() / linear_system.front().front());
        P = {p.front()};
    } else {

        polynom initial(d + 1, 0);
        for (int index = 0; index < initial.size(); ++index) {
            initial[index] = calculate_initial(index, p, r_powers);
        }

        out_matrix(linear_system);
        polynom system_answers = solve_system(linear_system, answer_vector);
        for (LL value: system_answers)
            Q.push_back(-value);

        P.assign(d + 1, 0);
        for (int i = 0; i < P.size(); ++i)
            P[i] = initial[i] - recurrent_value(initial, system_answers, i);
    }

    out_polynom(P);
    out_polynom(Q);
}
