#include <iostream>
#include <vector>
#include <map>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
using namespace std;

struct Edge {
    const int to, c, reverse_index;
    int flow = 0;

    int current_capacity() const { return c - flow; }
};

class Net {
public:
    const int n, m, start, finish, INF = 1000000;
    vector<vector<Edge>> edges;

    Net(int n, int m, int start, int finish) : n(n), m(m), start(start), finish(finish), edges(n) {}

    void add_oriented_edge(int from, int to, int c = 1) {
        int reverse_from_index = edges[to].size();
        int reverse_to_index = edges[from].size();
        edges[from].push_back({.to = to, .c = c, .reverse_index = reverse_from_index});
        edges[to].push_back({.to = from, .c = 0, .reverse_index = reverse_to_index});
    }

    Edge &get_backward_edge(int from, int idx) {
        Edge e = edges[from][idx];
        return edges[e.to][e.reverse_index];
    }
};

int dfs_find_flow_tour(int from, Net &net, vector<int> &visited, int flow) {
    visited[from] = true;
    if (from == net.finish || flow == 0)
        return flow;

    for (int i = 0; i < net.edges[from].size(); ++i) {
        Edge &e = net.edges[from][i];
        if (!visited[e.to] && e.current_capacity() > 0) {
            int delta = dfs_find_flow_tour(e.to, net, visited, min(e.current_capacity(), flow));
            if (delta) {
                e.flow += delta;
                net.get_backward_edge(from, i).flow -= delta;
                return delta;
            }
        }
    }
    return 0;
}

void make_path(int from, Net &net, vector<int> &visited, vector<int> &path) {
    path.push_back(from);
    visited[from] = true;
    if (from == net.finish) { return; }
    for (Edge &e: net.edges[from])
        if (!visited[e.to] && e.flow > 0) {
            e.flow = 0;
            return make_path(e.to, net, visited, path);
        }
}

int main() {
    c_boost;
    int n, m, s, t, from, to;
    cin >> n >> m >> s >> t;
    Net net(n, m, --s, --t);
    vector<int> visited;
    for (int i = 0; i < m; ++i) {
        cin >> from >> to;
        net.add_oriented_edge(--from, --to);
    }
    for (int i = 0; i < 2; ++i) {
        visited.assign(n, false);
        if (!dfs_find_flow_tour(net.start, net, visited, net.INF)) {
            cout << "NO";
            return 0;
        }
    }
    string answer = "YES\n";
    for (int i = 0; i < 2; ++i) {
        visited.assign(n, false);
        vector<int> path;
        make_path(net.start, net, visited, path);
        for (int v: path) answer += to_string(v + 1) + ' ';
        answer += '\n';
    }
    cout << answer;
}
