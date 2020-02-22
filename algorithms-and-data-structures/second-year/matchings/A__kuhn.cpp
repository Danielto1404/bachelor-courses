#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

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

void kuhn(int n, int m) {
    auto leftPart = vector<vector<int>>(n, vector<int>());
    auto rightPart = vector<int>(m, -1);
    auto mark = vector<int>(n, false);

    for (auto &left : leftPart) {
        int rightVertex;
        cin >> rightVertex;
        while (rightVertex != 0) {
            left.push_back(--rightVertex);
            cin >> rightVertex;
        }
    }
    for (int i = 0; i < n; ++i) {
        if (dfsDualGraphTour(i, leftPart, rightPart, mark))
            mark.assign(n, false);
    }
    auto matchingEdges = vector<pair<int, int>>();
    for (int i = 0; i < rightPart.size(); ++i)
        if (rightPart[i] != -1)
            matchingEdges.emplace_back(rightPart[i] + 1, i + 1);

    cout << matchingEdges.size() << '\n';
    for (auto const &edge : matchingEdges)
        cout << edge.first << ' ' << edge.second << '\n';
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    kuhn(n, m);
}
