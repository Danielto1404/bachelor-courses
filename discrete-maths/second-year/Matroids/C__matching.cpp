#include <iostream>
#include <vector>
#include <algorithm>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector<vector<int>>;
graph g;
vector<int> leftMatchVertexes;
vector<int> rightMatchVertexes;
const int undefined = -1;

inline void file_open(const string &name) {
    freopen((name + ".in").c_str(), "r", stdin);
    freopen((name + ".out").c_str(), "w", stdout);
}

struct Vertex {
    int weight, idx;
};

bool findMatching(int from, vector<int> &used) {
    if (used[from]) return false;
    used[from] = true;
    for (int to: g[from]) {
        int prevMatchingVertex = leftMatchVertexes[to];
        if (prevMatchingVertex == undefined || findMatching(prevMatchingVertex, used)) {
            leftMatchVertexes[to] = from;
            rightMatchVertexes[from] = to;
            return true;
        }
    }
    return false;
}

int main() {
    c_boost;
    file_open("matching");
    int n, to, vertexSz;
    cin >> n;
    vector<Vertex> weights(n);
    leftMatchVertexes.assign(n, undefined);
    rightMatchVertexes.assign(n, undefined);
    g.resize(n);

    for (int i = 0; i < n; ++i) {
        cin >> weights[i].weight;
        weights[i].idx = i;
    }
    for (int i = 0; i < n; ++i) {
        cin >> vertexSz;
        for (int j = 0; j < vertexSz; ++j) {
            cin >> to;
            --to;
            g[i].push_back(to);
        }
    }

    sort(weights.begin(), weights.end(), [](Vertex a, Vertex b) { return a.weight > b.weight; });
    for (Vertex v: weights) {
        vector<int> used(n, false);
        findMatching(v.idx, used);
    }
    for (int rightVertex: rightMatchVertexes)
        cout << rightVertex + 1 << ' ';
}
