#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

// @author: Danielto1404
#define time first
#define cost second
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using LL = long long;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

int main() {
    c_boost;
    file_open("schedule");
    LL n, t, c, penalty = 0, curTime = 0;
    cin >> n;
    vector<pair<int, LL>> tasks;
    for (int i = 0; i < n; ++i) {
        cin >> t >> c;
        if (t == 0) penalty += c;
        else tasks.emplace_back(t, c);
    }
    sort(tasks.begin(), tasks.end());
    priority_queue<LL, vector<LL>, greater<>> taskPriority;
    for (auto &task : tasks) {
        if (curTime < task.time) curTime++;
        else {
            // Здесь curTime == task[i].time (cutTime не может быть больше так как мы идем в порядке возрастания времен)
            // task.cost идут также в порядке возрастания (то есть мы оставим в отложенных заданиях наибольшиие веса)
            penalty += taskPriority.top();
            taskPriority.pop();
        }
        taskPriority.push(task.cost);
    }
    cout << penalty;
}
