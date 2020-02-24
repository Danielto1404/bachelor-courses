#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

struct UFO {
    int minTime;
    int x, y;

    double distance(UFO other) {
        return sqrt(pow((this->x - other.x), 2) + pow((this->y - other.y), 2));
    }
};

bool dfsDualGraphTour(int from, vector<vector<int>> &leftPart, vector<int> &rightPart, vector<int> &mark) {
    if (mark[from]) return false;
    mark[from] = true;
    for (int to: leftPart[from]) {
        if (rightPart[to] == -1 || dfsDualGraphTour(rightPart[to], leftPart, rightPart, mark)) {
            rightPart[to] = from;
            return true;
        }
    }
    return false;
}

void kuhn(int n, int m, vector<vector<int>> &leftPart, vector<pair<int, int>> &matching) {
    auto rightPart = vector<int>(m, -1);
    auto mark = vector<int>(n, false);

    for (int i = 0; i < leftPart.size(); ++i)
        if (dfsDualGraphTour(i, leftPart, rightPart, mark))
            mark.assign(n, false);

    matching = vector<pair<int, int>>();
    for (int i = 0; i < rightPart.size(); ++i)
        if (rightPart[i] != -1)
            matching.emplace_back(rightPart[i], i);
}

int main() {
    c_boost;
    file_open("ufo");
    char skip;
    int n, hour, minute, x, y;
    double velocity;

    cin >> n >> velocity;
    velocity /= 60;

    vector<vector<int>> leftPart(n);
    vector<pair<int, int>> matching;
    vector<UFO> aliens;

    for (int i = 0; i < n; ++i) {
        cin >> hour >> skip >> minute >> x >> y;
        int time = 60 * hour + minute;
        UFO ufo = {.minTime = time, .x = x, .y =y};
        aliens.push_back(ufo);
    }

    sort(aliens.begin(), aliens.end(), [](UFO a, UFO b) { return a.minTime <= b.minTime; });

    for (int i = 0; i < aliens.size() - 1; ++i)
        for (int j = i + 1; j < aliens.size(); ++j)
            if (aliens[i].distance(aliens[j]) <= (aliens[j].minTime - aliens[i].minTime) * velocity)
                leftPart[i].push_back(j);

    kuhn(n, n, leftPart, matching);
    cout << n - matching.size();
}
