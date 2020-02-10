#include <vector>
#include <iostream>

#define FILE freopen("sqroot.in", "r", stdin), freopen("sqroot.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

vector <vector <int>> matrix;
bool consist = false;

bool is_matrix_equals(vector <vector <int>>& sqrt_matrix) {
    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 4; ++j) {
            int el = 0;
            for (int k = 0; k < 4; ++k) {
                el += sqrt_matrix[i][k] * sqrt_matrix[k][j];
            }
            if (el % 2 != matrix[i][j]) return false;
        }
    }
    return consist = true;
}

void perm_all_matrix(int index, vector <vector <int>> sqrt_matrix) {
    if (index == 16) {
        if (is_matrix_equals(sqrt_matrix)) {
            for (vector <int>& line : sqrt_matrix) {
                for (int element : line)
                    cout << element << " ";
                cout << '\n';
            }
            exit(0);
        }
    } else {
        for (int bit = 0; bit <= 1; ++bit) {
            sqrt_matrix[index / 4][index % 4] = bit;
            perm_all_matrix(index + 1, sqrt_matrix);
        }
    }
}

int main() {
    c_boost;
    FILE;
    vector <vector <int>> sqrt_matrix(4, vector <int>(4));
    matrix.assign(4, vector <int>(4));
    for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++)
            cin >> matrix[i][j];
    perm_all_matrix(0, sqrt_matrix);
    if (!consist) cout << "NO SOLUTION";
}
