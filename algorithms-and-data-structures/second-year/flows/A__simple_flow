#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
using namespace std;

struct Edge {
    const int to, c, reverse_index;
    int flow = 0;

    int current_capacity() const {
        return c - flow;
    }
};

class Net {
public:
    const int n, m, start, finish, INF = 1000000000;
    vector<pair<int, int>> order;
    vector<vector<Edge>> edges;

    Net(int n, int m) : n(n), m(m), start(0), finish(n - 1), edges(n) {}

    void add_an_undirected_edge(int from, int to, int c) {
        order.emplace_back(from, edges[from].size());
        add_oriented_edge(from, to, c);
        add_oriented_edge(to, from, c);
    }

    Edge &get_reverse_edge(int from, int idx) {
        Edge e = edges[from][idx];
        return edges[e.to][e.reverse_index];
    }

private:
    void add_oriented_edge(int from, int to, int c) {
        int reverse_from_index = edges[to].size();
        int reverse_to_index = edges[from].size();
        edges[from].push_back({.to = to, .c = c, .reverse_index = reverse_from_index});
        edges[to].push_back({.to = from, .c = 0, .reverse_index = reverse_to_index});
    }
};

bool bfs_set_depth_tour(Net &net, vector<int> &distance) {
    distance.assign(net.n, -1);
    distance[net.start] = 0;
    queue<int> q;
    q.push(net.start);
    while (!q.empty()) {
        int from = q.front();
        q.pop();
        for (const Edge &e: net.edges[from])
            if (distance[e.to] == -1 && e.current_capacity() > 0) {
                distance[e.to] = distance[from] + 1;
                q.push(e.to);
            }
    }
    return distance[net.finish] != -1;
}

int dfs_find_flow_tour(int from, Net &net, vector<int> &last_block_edge, vector<int> &distance, int flow) {
    if (from == net.finish || flow == 0)
        return flow;

    for (int i = last_block_edge[from]; i < net.edges[from].size(); ++i, ++last_block_edge[from]) {
        Edge &e = net.edges[from][i];
        if (distance[e.to] != distance[from] + 1) { continue; }
        int delta = dfs_find_flow_tour(e.to, net, last_block_edge, distance, min(e.current_capacity(), flow));
        if (delta) {
            e.flow += delta;
            net.get_reverse_edge(from, i).flow -= delta;
            return delta;
        }
    }
    return 0;
}


int find_max_flow(Net &net) {
    int max_flow = 0;
    vector<int> distance;
    while (bfs_set_depth_tour(net, distance)) {
        vector<int> block(net.n, 0);
        while (int flow = dfs_find_flow_tour(net.start, net, block, distance, net.INF))
            max_flow += flow;
    }
    return max_flow;
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    Net net(n, m);
    for (int i = 0; i < m; ++i) {
        int from, to, c;
        cin >> from >> to >> c;
        net.add_an_undirected_edge(from - 1, to - 1, c);
    }

    cout << find_max_flow(net) << '\n';
    for (pair<int, int> const &direction: net.order) {
        int edge_flow = net.edges[direction.first][direction.second].flow;
        if (edge_flow == 0)
            edge_flow = net.edges[direction.first][direction.second + 1].flow;

        cout << edge_flow << '\n';
    }
}
