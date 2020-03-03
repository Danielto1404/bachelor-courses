#include <iostream>
#include <vector>
#include <map>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector<vector<int>>;
const int undefined = -1;

struct Vertex {
    int weight, idx;
};

bool dfsDualGraphTour(int from, graph &g, vector<int> &mark, vector<int> &matching) {
    if (mark[from]) return false;
    mark[from] = true;
    for (int to: g[from]) {
        if (matching[to] == undefined || dfsDualGraphTour(matching[to], g, mark, matching)) {
            matching[to] = from;
            return true;
        }
    }
    return false;
}

// MARK PATH WITH STEP == 1
void dfs(int from,
         bool inLeft,
         bool pick,
         vector<int> &markLeft,
         vector<int> &markRight,
         graph &left,
         graph &right,
         vector<pair<int, int>> &edges) {

    if (inLeft) { markLeft[from] = true; }
    else { markRight[from] = true; }
    for (int to: (inLeft ? left : right)[from])
        if (to != undefined && !(inLeft ? markRight : markLeft)[to]) {
            if (pick) {
                pair<int, int> edge = inLeft ? make_pair(from, to) : make_pair(to, from);
                edges.push_back(edge);
            }
            dfs(to, !inLeft, !pick, markLeft, markRight, left, right, edges);
        }
}

void createGraph(graph &a, graph &b, vector<int> &matching) {
    for (int from = 0; from < matching.size(); ++from) {
        int to = matching[from];
        if (to != undefined) {
            a[from].push_back(to);
            b[to].push_back(from);
        }
    }
}

void findMaximumMatching(vector<Vertex> &sortedVertexes, graph &g, vector<int> &matching) {
    vector<int> used(sortedVertexes.size(), false);
    for (const Vertex &v: sortedVertexes) {
        used.assign(sortedVertexes.size(), false);
        dfsDualGraphTour(v.idx, g, used, matching);
    }
}

int main() {
    c_boost;
    int n, m, edgesAmount, from, to;
    cin >> n >> m >> edgesAmount;

    // ASSIGN VALUES
    vector<Vertex> leftVertexes(n);
    vector<Vertex> rightVertexes(m);

    vector<int> markLeft(n, false);
    vector<int> markRight(m, false);

    vector<int> leftMatching(n, undefined);
    vector<int> rightMatching(m, undefined);

    graph leftPart(n);
    graph rightPart(m);

    graph nLeftPart(n);
    graph nRightPart(m);

    map<pair<int, int>, int> edgesIdx;
    vector<pair<int, int>> edges;

    // READ GRAPH
    for (int i = 0; i < leftVertexes.size(); ++i) {
        cin >> leftVertexes[i].weight;
        leftVertexes[i].idx = i;
    }
    for (int i = 0; i < rightVertexes.size(); ++i) {
        cin >> rightVertexes[i].weight;
        rightVertexes[i].idx = i;
    }
    for (int i = 0; i < edgesAmount; ++i) {
        cin >> from >> to;
        --from;
        --to;
        edgesIdx[{from, to}] = i + 1;
        leftPart[from].push_back(to);
        rightPart[to].push_back(from);
    }

    // SORT VERTEXES
    vector<Vertex> sortedLeftVertexes = leftVertexes;
    vector<Vertex> sortedRightVertexes = rightVertexes;

    sort(sortedLeftVertexes.begin(), sortedLeftVertexes.end(),
         [](Vertex a, Vertex b) { return a.weight > b.weight; });
    sort(sortedRightVertexes.begin(), sortedRightVertexes.end(),
         [](Vertex a, Vertex b) { return a.weight > b.weight; });


    // FIND MAX MATCHING IN EACH PARTS OF GRAPH (LIKE MATROIDS)
    findMaximumMatching(sortedLeftVertexes, leftPart, rightMatching);
    findMaximumMatching(sortedRightVertexes, rightPart, leftMatching);

    createGraph(nLeftPart, nRightPart, leftMatching);
    createGraph(nRightPart, nLeftPart, rightMatching);

    // FIND PATHS
    for (const Vertex &v: sortedLeftVertexes)
        if (nLeftPart[v.idx].size() == 1 && !markLeft[v.idx]) {
            dfs(v.idx, true, true, markLeft, markRight, nLeftPart, nRightPart, edges);
        }

    for (const Vertex &v: sortedRightVertexes)
        if (nRightPart[v.idx].size() == 1 && !markRight[v.idx])
            dfs(v.idx, false, true, markLeft, markRight, nLeftPart, nRightPart, edges);

    // FIND EVEN CYCLES
    for (int i = 0; i < n; ++i)
        if (!nLeftPart[i].empty() && !markLeft[i])
            dfs(i, true, true, markLeft, markRight, nLeftPart, nRightPart, edges);

    for (int i = 0; i < m; ++i)
        if (!nRightPart[i].empty() && !markRight[i])
            dfs(i, false, true, markLeft, markRight, nLeftPart, nRightPart, edges);

    long long s = 0;
    for (auto edge: edges)
        s += leftVertexes[edge.first].weight + rightVertexes[edge.second].weight;

    cout << s << '\n' << edges.size() << '\n';
    for (auto edge: edges)
        cout << edgesIdx[edge] << ' ';
}
