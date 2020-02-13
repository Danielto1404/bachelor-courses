#include <vector>
#include <algorithm>
#include <iostream>

#define FILE freopen("beautiful.in", "r", stdin), freopen("beautiful.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
int n, r;

int calc(vector <int>& array) {
    int res = 0;
    for (int i = 0; i < n - 1; ++i)
        res += array[i] * array[i + 1];
    return res % r;
}

bool is_beautiful(int number) {
    int i, factors = number == 1 ? 0 : 1;
    for (i = 2; i * i < number; ++i) {
        if (number % i == 0) factors += 2;
    }
    if (number == i * i) {
        factors++;
    }
    return (factors + 1) % 3 == 0;
}

int main() {
    c_boost;
    FILE;
    int count = 0;
    cin >> n >> r;
    vector <int> arr(n);
    for (int i = 1; i <= n; i++) {
        arr[i - 1] = i;
    }
    do {
        int num = calc(arr);
        count += (num == 0 ? 1 : is_beautiful(num) ? 1 : 0);
    } while (next_permutation(arr.begin(), arr.end()));
    cout << count;
}
