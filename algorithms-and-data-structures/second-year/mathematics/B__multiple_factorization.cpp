#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

const int MAX_NUMBER = 1e6;
const int undefined = 1;

void outFactors(vector<int> &least_factors, int n) {
    vector<int> factors;
    while (n != 1) {
        factors.push_back(least_factors[n]);
        n /= least_factors[n];
    }
    sort(factors.begin(), factors.end());
    for_each(factors.begin(), factors.end(), [](int x) { cout << x << ' '; });
    cout << '\n';
}

int main() {
    c_boost;
    vector<int> least_factors(MAX_NUMBER + 1, undefined);
    vector<int> primes;
    for (int i = 2; i < least_factors.size(); ++i) {
        if (least_factors[i] == 1) {
            least_factors[i] = i;
            primes.push_back(i);
        }
        for (int prime : primes) {
            if (prime > least_factors[i] || least_factors.size() / prime < i) break;
            least_factors[i * prime] = prime;
        }
    }
    
    int n, x;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        cin >> x;
        outFactors(least_factors, x);
    }
}
