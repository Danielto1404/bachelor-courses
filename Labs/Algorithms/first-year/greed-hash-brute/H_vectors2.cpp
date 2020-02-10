#include <iostream>
#include <vector>

#define FILE freopen("vectors2.in", "r", stdin), freopen("vectors2.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

vector <vector <int>> all_vectors;

void generate_with_out_k(vector <int>& bits, int prefix, int n, int cur_length) {
    if (n == cur_length) {
        all_vectors.push_back(bits);
    } else {
        for (int bit = 0; bit <= 1; bit++) {
            if (bit == 1) {
                if (prefix != 1) {
                    bits[cur_length] = bit;
                    generate_with_out_k(bits, prefix + 1, n, cur_length + 1);
                }
            } else {
                bits[cur_length] = bit;
                generate_with_out_k(bits, 0, n, cur_length + 1);
            }
        }
    }
}

int main() {
    c_boost;
    FILE;
    int n;
    cin >> n;
    vector <int> bits(n);
    generate_with_out_k(bits, 0, n, 0);
    cout << all_vectors.size() << '\n';
    for (const vector <int>& bit_vector : all_vectors) {
        for (int el : bit_vector)
            cout << el;
        cout << '\n';
    }
}
