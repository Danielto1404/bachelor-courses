#include <vector>
#include <algorithm>
#include <iostream>

#define FILE freopen("request.in", "r", stdin), freopen("request.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
#define start_t first
#define end_t second

using namespace std;

int main() {
    c_boost;
    FILE;
    int n, start, end, cur_end_time, cnt = 1;
    cin >> n;
    vector <pair <int, int>> times(n);
    for (int i = 0; i < n; i++) {
        cin >> start >> end;
        times[i] = make_pair(start, end);
    }
    sort(times.begin(), times.end(), [](pair <int, int> a, pair <int, int> b) { return a.end_t < b.end_t; });
    cur_end_time = times[0].end_t;
    for (int i = 1; i < n; i++) {
        if (times[i].start_t >= cur_end_time) {
            cur_end_time = times[i].end_t;
            cnt++;
        }
    }
    cout << cnt;
}
