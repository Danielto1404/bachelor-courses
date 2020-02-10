#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector <vector <int>>;

// @author: Danielto1404

void topSort(vector <int>& sorted, graph& g, vector <int>& cntIn) {
    queue <int> q;
    for (size_t i = 0; i < cntIn.size(); ++i)
        if (cntIn[i] == 0)
            q.push(i);

    while (!q.empty()) {
        int to_delete = q.front();
        q.pop();
        for (int to: g[to_delete])
            if (--cntIn[to] == 0)
                q.push(to);

        sorted.push_back(to_delete);
    }
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    graph g = graph(n);
    vector <int> cntIn(n, 0);
    for (int i = 0; i < m; ++i) {
        int from, to;
        cin >> from >> to;
        --from;
        --to;
        g[from].push_back(to);
        cntIn[to]++;
    }

    vector <int> topSortOrder;
    vector <int> funcValues(n, 0);
    topSort(topSortOrder, g, cntIn);

    reverse(topSortOrder.begin(), topSortOrder.end());

    for (int from: topSortOrder) {
        vector <int> usedValues;
        for (int to: g[from])
            usedValues.push_back(funcValues[to]);

        sort(usedValues.begin(), usedValues.end());

        int minPossibleValue = 0;
        for (int val: usedValues)
            if (val == minPossibleValue)
                ++minPossibleValue;

        funcValues[from] = minPossibleValue;
    }
    for (int val: funcValues)
        cout << val << '\n';
}
