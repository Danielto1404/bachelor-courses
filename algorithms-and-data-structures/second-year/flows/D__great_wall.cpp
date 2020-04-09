
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
    const int n, INF = 1000000000;
    int finish;
    int start;
    vector<EdgeIndex> order;
    vector<vector<Edge>> edges;

    Net(int n) : n(n), start(0), finish(n - 1), edges(n) {}

    void add_oriented_edge(int from, int to, int c) {
        int reverse_from_index = edges[to].size();
        int reverse_to_index = edges[from].size();
        order.push_back({.from = from, .index = reverse_to_index});
        edges[from].push_back({.to = to, .c = c, .reverse_index = reverse_from_index});
        edges[to].push_back({.to = from, .c = 0, .reverse_index = reverse_to_index});
    }

    Edge &get_backward_edge(int from, int idx) {
        Edge e = edges[from][idx];
        return edges[e.to][e.reverse_index];
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
        if (!visited[e.to] && e.c > e.flow)
            dfs_visit_tour(e.to, net, visited);
}

long min_cut(Net &net, vector<int> &unconnected_vertexes) {
    long min_cut_value = find_max_flow(net);

    vector<int> visited(net.n, false);
    dfs_visit_tour(net.start, net, visited);
    for (int v = 0; v < net.n; ++v) {
        if (!visited[v]) continue;
        for (auto e: net.edges[v])
            if (!visited[e.to] && e.flow == 1) {
                unconnected_vertexes.push_back(e.to);
                break;
            }
    }
    return min_cut_value;
}

int index_for_outgoing_edges(int i, int j, int length) {
    return length * i + j;
}

int index_for_incoming_edges(int i, size_t j, int height, int length) {
    return height * length + index_for_outgoing_edges(i, j, length);
}

int main() {
    int l, h;
    char symbol;
    cin >> h >> l;
    Net net(h * l * 2);
    vector<vector<char>> table(h, vector<char>(l));
    for (int i = 0; i < h; ++i)
        for (int j = 0; j < l; ++j) {
            cin >> table[i][j];
            switch (table[i][j]) {
                case '-':
                    net.add_oriented_edge(index_for_incoming_edges(i, j, h, l),
                                          index_for_outgoing_edges(i, j, l),
                                          net.INF);
                    break;
                case '.':
                    net.add_oriented_edge(index_for_incoming_edges(i, j, h, l),
                                          index_for_outgoing_edges(i, j, l),
                                          1);
                    break;
                case 'A':
                    net.start = index_for_outgoing_edges(i, j, l);
                    break;
                case 'B':
                    net.finish = index_for_incoming_edges(i, j, h, l);
            }
        }

    for (int i = 0; i < h; ++i)
        for (int j = 0; j < l; ++j) {
            if (table[i][j] == '#') continue;

            int from = index_for_outgoing_edges(i, j, l);
            int to = index_for_incoming_edges(i, j, h, l);

            if (i + 1 < h && table[i + 1][j]) {
                net.add_oriented_edge(index_for_outgoing_edges(i + 1, j, l), to, net.INF);
                net.add_oriented_edge(from, index_for_incoming_edges(i + 1, j, h, l), net.INF);
            }

            if (j + 1 < l && table[i][j + 1]) {
                net.add_oriented_edge(index_for_outgoing_edges(i, j + 1, l), to, net.INF);
                net.add_oriented_edge(from, index_for_incoming_edges(i, j + 1, h, l), net.INF);
            }
        }

    vector<int> min_cut_vertexes;
    int min_cut_value = min_cut(net, min_cut_vertexes);
    if (min_cut_value >= net.INF || min_cut_value < 0) {
        cout << -1;
        return 0;
    }

    for (int v: min_cut_vertexes)
        table[v / l][v % l] = '+';

    cout << min_cut_value << '\n';
    for (size_t i = 0; i < h; ++i) {
        for (size_t j = 0; j < l; ++j)
            cout << table[i][j];

        cout << '\n';
    }
}
