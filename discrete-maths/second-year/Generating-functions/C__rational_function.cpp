#include <iostream>
#include <vector>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

int recurrent_value(vector<int> &initial, vector<int> &coefficients, int k) {
    int value = 0;
    for (int i = 0; i < k; ++i)
        value += coefficients[i] * initial[k - i - 1];
    return value;
}

int main() {
    c_boost;
    int k;
    cin >> k;

    vector<int> initial(k);
    vector<int> coefficients(k);

    for (int i = 0; i < k; ++i)
        cin >> initial[i];

    for (int i = 0; i < k; ++i)
        cin >> coefficients[i];

    vector<int> P(k + 1, 0);
    for (int i = 0; i < k; ++i)
        P[i] = initial[i] - recurrent_value(initial, coefficients, i);

    int degree = k + 1;
    while (degree >= 0 && P[degree - 1] == 0)
        --degree;

    cout << degree - 1 << '\n';
    for (int i = 0; i < degree; ++i)
        cout << P[i] << ' ';

    cout << '\n' << k << '\n' << 1 << ' ';
    for (int coefficient : coefficients)
        cout << -coefficient << ' ';
}
