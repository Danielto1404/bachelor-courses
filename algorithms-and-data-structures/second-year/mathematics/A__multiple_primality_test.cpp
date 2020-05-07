#include <iostream>
#include <vector>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

const int MAX_NUMBER = 2 * 10 * 1000 * 1000;

int main() {
    c_boost;

    vector<int> isPrime(MAX_NUMBER + 1, true);
    isPrime[0] = isPrime[1] = false;
    for (int i = 2; i < isPrime.size(); ++i)
        if (isPrime[i])
            for (int j = 2 * i; j < isPrime.size(); j += i)
                isPrime[j] = false;

    int n, x;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        cin >> x;
        cout << (isPrime[x] ? "YES" : "NO") << '\n';
    }
}
