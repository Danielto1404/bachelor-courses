#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

#define FILE freopen("cobbler.in", "r", stdin), freopen("cobbler.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

int main() {
    c_boost;
    FILE;
    int k, n;
    cin >> k >> n;
    vector <int> minutes(n);
    for (int& min : minutes)
        cin >> min;
    sort(minutes.begin(), minutes.end());
    int cnt = 0;
    for (int min : minutes)
        if (min <= k) {
            k -= min;
            cnt++;
        } else {
            break;
        }
    cout << cnt;
}
