#include <iostream>
#include <vector>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

struct Point {
    int x, y;

    int distance(Point other) {
        return abs(this->x - other.x) + abs(this->y - other.y);
    }
};

struct Order {
    int beginTime;
    Point from_point, to_point;

    int timeTo(Order other) {
        return to_point.distance(other.from_point);
    }

    int orderTime() {
        return from_point.distance(to_point);
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
    file_open("taxi");
    char skip;
    int n, hour, minute, from_x, from_y, to_x, to_y;
    cin >> n;
    vector<vector<int>> leftPart(n);
    vector<pair<int, int>> matching;
    vector<Order> orders;
    for (int i = 0; i < n; ++i) {
        cin >> hour >> skip >> minute >> from_x >> from_y >> to_x >> to_y;
        int beginTime = 60 * hour + minute;
        Point from_point = {.x = from_x, .y = from_y};
        Point to_point = {.x = to_x, .y = to_y};
        Order order = {.beginTime = beginTime, .from_point = from_point, .to_point = to_point};
        orders.push_back(order);
    }
    for (int i = 0; i < orders.size() - 1; ++i)
        for (int j = i + 1; j < orders.size(); ++j)
            if (orders[i].orderTime() + orders[i].timeTo(orders[j]) + 1 <= orders[j].beginTime - orders[i].beginTime)
                leftPart[i].push_back(j);

    kuhn(n, n, leftPart, matching);
    cout << n - matching.size();
}
