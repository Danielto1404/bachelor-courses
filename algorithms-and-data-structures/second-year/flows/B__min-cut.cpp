#include <iostream>
#include <vector>
#include <queue>

// @author: Danielto1404

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)
using namespace std;

struct EdgeIndex {
    const int from, index;
};

struct Edge {
    const int to, c, reverse_index;
    int flow = 0;

    int current_capacity() const { return c - flow; }
};

class Net {
public:
    const int n, m, start, finish, INF = 1000000000;
    vector<EdgeIndex> order;
    vector<vector<Edge>> edges;

    Net(int n, int m) : n(n), m(m), start(0), finish(n - 1), edges(n) {}

    void add_an_undirected_edge(int from, int to, int c) {
        order.push_back({.from = from, .index = (int) edges[from].size()});
        add_oriented_edge(from, to, c);
        add_oriented_edge(to, from, c);
    }

    Edge &get_backward_edge(int from, int idx) {
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
            net.get_backward_edge(from, i).flow -= delta;
            return delta;
        }
    }
    return 0;
}

long find_max_flow(Net &net) {
    long max_flow = 0;
    vector<int> distance;
    while (bfs_set_depth_tour(net, distance)) {
        vector<int> last_block_edge(net.n, 0);
        while (int flow = dfs_find_flow_tour(net.start, net, last_block_edge, distance, net.INF))
            max_flow += flow;
    }
    return max_flow;
}

void dfs_visit_tour(int from, Net &net, vector<int> &visited) {
    visited[from] = true;
    for (const Edge e: net.edges[from])
        if (!visited[e.to] && e.c != e.flow)
            dfs_visit_tour(e.to, net, visited);
}

long min_cut(Net &net, vector<int> &edgesIndexes) {
    long min_cut_value = find_max_flow(net);

    vector<int> visited(net.n, false);
    dfs_visit_tour(net.start, net, visited);

    for (int i = 0; i < net.order.size(); ++i) {
        EdgeIndex dir = net.order[i];
        int from = dir.from, to = net.edges[dir.from][dir.index].to;
        if (visited[from] != visited[to])
            edgesIndexes.push_back(i);
    }
    return min_cut_value;
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
    vector<int> edgesIndexes;
    long min_cut_value = min_cut(net, edgesIndexes);
    cout << edgesIndexes.size() << ' ' << min_cut_value << '\n';
    for (int i: edgesIndexes) cout << i + 1 << ' ';
}
