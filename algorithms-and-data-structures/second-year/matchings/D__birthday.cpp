#include <iostream>
#include <vector>
#include <set>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;

using graph = vector<vector<int>>;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

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

void
dfs(int from, vector<vector<int>> &leftPart, vector<int> &rightPart, vector<int> &markLeft, vector<int> &markRight) {
    if (markLeft[from]) return;
    markLeft[from] = true;
    for (int to: leftPart[from])
        if (rightPart[to] != -1) {
            markRight[to] = true;
            dfs(rightPart[to], leftPart, rightPart, markLeft, markRight);
        }
}

void kuhn(int leftPartSize, int rightPartSize, vector<vector<int>> &leftPart, vector<int> &matching) {
    auto rightPart = vector<int>(rightPartSize, -1);
    auto mark = vector<int>(leftPartSize, false);

    for (int i = 0; i < leftPart.size(); ++i)
        if (dfsDualGraphTour(i, leftPart, rightPart, mark))
            mark.assign(leftPartSize, false);

    matching = rightPart;
}

void solve() {
    int m, n;
    cin >> m >> n;
    graph gT = graph(m, vector<int>(n, true));
    for (int i = 0; i < m; ++i) {
        int girlIndex;
        cin >> girlIndex;
        while (girlIndex) {
            --girlIndex;
            gT[i][girlIndex] = false;
            cin >> girlIndex;
        }
    }
    graph reversedLeftPart(m);
    for (int i = 0; i < m; ++i)
        for (int j = 0; j < n; ++j)
            if (gT[i][j])
                reversedLeftPart[i].push_back(j);

    vector<int> matching;
    kuhn(m, n, reversedLeftPart, matching);

    set<int> free;
    for (int i = 0; i < m; ++i) free.insert(i);
    for (int leftVertex: matching)
        free.erase(leftVertex);

    vector<int> markLeft(m, false);
    vector<int> markRight(n, false);
    for (int v: free)
        dfs(v, reversedLeftPart, matching, markLeft, markRight);

    vector<int> boysIndices, girlsIndices;
    for (int i = 0; i < m; ++i)
        if (markLeft[i])
            boysIndices.push_back(i + 1);

    for (int i = 0; i < n; ++i)
        if (!markRight[i])
            girlsIndices.push_back(i + 1);

    cout << boysIndices.size() + girlsIndices.size() << '\n';
    cout << boysIndices.size() << ' ' << girlsIndices.size() << '\n';
    for (int boyIndex : boysIndices) {
        cout << boyIndex << ' ';
    }
    cout << '\n';
    for (int girlIndex : girlsIndices) {
        cout << girlIndex << ' ';
    }
    cout << "\n\n";
}

int main() {
    c_boost;
    file_open("birthday");
    int k;
    cin >> k;
    while (k--)
        solve();
}
