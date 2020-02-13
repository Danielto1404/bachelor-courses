#include <iostream>
#include <vector>

#define FILE freopen("avia.in", "r", stdin), freopen("avia.out", "w", stdout)
#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using weightGraph=vector <vector <long long>>;

vector <bool> used;

int countVisitedVertexDfs(int from, long long w, weightGraph& g) {
    used[from] = true;
    int visitedCnt = 1;
    for (int to = 0; to < g.size(); ++to)
        if (!used[to])
            if (g[from][to] <= w)
                visitedCnt += countVisitedVertexDfs(to, w, g);

    return visitedCnt;
}

int main() {
    c_boost;
    FILE;
    int n;
    long long w, minWeight = 0, maxWeight = 0;
    cin >> n;

    used.assign(n, false);
    auto g = weightGraph(n, vector <long long>(n));
    auto gT = weightGraph(n, vector <long long>(n));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            cin >> w;
            g[i][j] = w;
            gT[j][i] = w;
            maxWeight = max(maxWeight, w);
        }
    }

    long long weight = 0;
    while (maxWeight - minWeight > 1) {

        used.assign(n, false);
        weight = (maxWeight + minWeight) / 2;

        int visitedForwardCnt = countVisitedVertexDfs(0, weight, g);
        if (visitedForwardCnt == n) {

            used.assign(n, false);
            int visitedBackCnt = countVisitedVertexDfs(0, weight, gT);

            if (visitedBackCnt == n) {
                maxWeight = weight;
            } else {
                minWeight = weight;
            }

        } else {
            minWeight = weight;
        }
    }
    cout << maxWeight;
}
