#include <iostream>
#include <vector>
#include <queue>

using namespace std;

using graph = vector <vector <int>>;

graph gT;
vector <bool> win;
vector <bool> loose;
vector <int> cntOut;

void bfsVisitTour(queue <int>& q) {
    while (!q.empty()) {
        int from = q.front();
        q.pop();
        for (int to: gT[from])

            // equal to !used[to]
            if (!loose[to] && !win[to]) {
                if (win[from] && --cntOut[to] == 0) {
                    loose[to] = true;
                } else if (loose[from]) {
                    win[to] = true;
                } else continue;

                q.push(to);
            }
    }
}

int main() {
    int n, m, from, to;
    while (cin >> n >> m) {

        gT.assign(n, vector <int>());
        win.assign(n, false);
        loose.assign(n, false);
        cntOut.assign(n, 0);

        for (int i = 0; i < m; ++i) {
            cin >> from >> to;
            --from;
            --to;
            gT[to].push_back(from);
            cntOut[from]++;
        }

        queue <int> bfsQueue;
        for (int i = 0; i < n; ++i)
            if (cntOut[i] == 0) {
                loose[i] = true;
                bfsQueue.push(i);
            }

        bfsVisitTour(bfsQueue);

        for (int i = 0; i < n; ++i) {
            if (!win[i] && !loose[i]) {
                cout << "DRAW";
            } else if (win[i]) {
                cout << "FIRST";
            } else cout << "SECOND";

            cout << '\n';
        }
    }
}
