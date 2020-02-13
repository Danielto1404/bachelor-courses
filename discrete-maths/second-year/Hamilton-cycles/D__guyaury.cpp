#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

inline void file_open(const string& name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

using graph = vector <vector <int>>;

set <int> cycle;
set <int> notCycle;

void fillNextAndPrev(int start, int end, vector <int>& next, vector <int>& prev) {
    next[start] = end;
    prev[end] = start;
}

void insertToCycle(int v) {
    cycle.insert(v);
    notCycle.erase(v);
}

void find3lengthCycle(int n, graph& g, vector <int>& next, vector <int>& prev) {
    int fst = 0;
    for (int snd = 0; snd < n; ++snd)
        for (int thd = 0; thd < n; ++thd)
            if (g[fst][snd] == 1 && g[snd][thd] == 1 && g[thd][fst] == 1 && snd != thd) {
                fillNextAndPrev(fst, snd, next, prev);
                fillNextAndPrev(snd, thd, next, prev);
                fillNextAndPrev(thd, fst, next, prev);

                insertToCycle(fst);
                insertToCycle(snd);
                insertToCycle(thd);
                return;
            }
}

bool insideTransition(graph& g, vector <int>& next, vector <int>& prev) {
    for (int i : notCycle)
        for (int v : cycle) {

            bool canAddToCycle = g[prev[v]][i] == 1 && g[i][v] == 1;
            if (canAddToCycle) {
                fillNextAndPrev(prev[v], i, next, prev);
                fillNextAndPrev(i, v, next, prev);

                insertToCycle(i);
                return true;
            }

        }
    return false;
}

void outsideTransition(graph& g, vector <int>& next, vector <int>& prev) {
    for (int v : cycle)
        for (int fst : notCycle)
            for (int snd : notCycle) {
                bool canAddToCycle = g[prev[v]][snd] == 1 && g[snd][fst] == 1 && g[fst][v] == 1;
                if (canAddToCycle) {
                    fillNextAndPrev(prev[v], snd, next, prev);
                    fillNextAndPrev(snd, fst, next, prev);
                    fillNextAndPrev(fst, v, next, prev);

                    insertToCycle(fst);
                    insertToCycle(snd);
                    return;
                }

            }
}

int main() {
    c_boost;
    file_open("guyaury");
    int n;
    string edge;
    cin >> n;
    notCycle = {0};
    graph g = graph(n, vector <int>(n, 0));
    for (int i = 1; i < n; ++i) {
        notCycle.insert(i);
        cin >> edge;
        for (int j = 0; j < int(edge.length()); ++j) {
            if (edge[j] == '1') {
                g[i][j] = 1;
            } else {
                g[j][i] = 1;
            }
        }
    }

    vector <int> next(n, -1);
    vector <int> prev(n, -1);

    find3lengthCycle(n, g, next, prev);
    int cycleLength = 3;

    while (cycleLength != n) {
        bool isChanged = insideTransition(g, next, prev);
        if (isChanged)
            continue;

        outsideTransition(g, next, prev);
        cycleLength++;
    }

    int start = next.front();
    while (true) {
        cout << start + 1 << ' ';
        start = next[start];
        if (start == next.front()) break;
    }
}
