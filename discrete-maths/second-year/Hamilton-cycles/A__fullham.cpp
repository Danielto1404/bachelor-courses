#include <iostream>
#include <vector>
#include <algorithm>
#include <deque>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

inline void file_open(const string& name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

using graph = vector <vector <int>>;

// @author: Danielto1404

int main() {
    c_boost;
    file_open("fullham");

    int n;
    string edges;
    cin >> n;

    deque <int> deq = {0};
    graph g = graph(n, vector <int>(n, 0));

    for (int i = 1; i < n; ++i) {
        deq.push_back(i);
        cin >> edges;
        for (int j = 0; j < int(edges.length()); ++j)
            if (edges[j] == '1') {
                g[i][j] = 1;
                g[j][i] = 1;
            }
    }

    for (int i = 0; i < n * (n - 1); ++i) {
        int fst = deq[0];
        int snd = deq[1];

        if (g[fst][snd] == 0) {
            int idx = 2;
            while (true) {
                bool fstConnection = g[fst][deq[idx]] == 1;
                if (fstConnection) {
                    reverse(deq.begin() + 1, deq.begin() + (idx + 1));
                    break;
                }
                idx++;
            }
        }
        deq.pop_front();
        deq.push_back(fst);
    }

    for (int v : deq)
        cout << v + 1 << ' ';
}
