#include <iostream>
#include <vector>
#include <queue>
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
    const int n, start, finish, INF = 1000000;
    vector<vector<Edge>> edges;

    Net(int n) : n(n), start(0), finish(n - 1), edges(n) {}

    void add_oriented_edge(int from, int to, int c = 1) {
        int reverse_from_index = edges[to].size();
        int reverse_to_index = edges[from].size();
        edges_indexes[{from, to}] = reverse_to_index;
        edges[from].push_back({.to = to, .c = c, .reverse_index = reverse_from_index});
        edges[to].push_back({.to = from, .c = 0, .reverse_index = reverse_to_index});
    }

    int get_edge_flow(int from, int to) {
        int index = edges_indexes[{from, to}];
        return edges[from][index].flow;
    }

    Edge &get_backward_edge(int from, int idx) {
        Edge e = edges[from][idx];
        return edges[e.to][e.reverse_index];
    }

private:
    map<pair<int, int>, int> edges_indexes;
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
    int n, requested_points;
    char symbol;
    cin >> n;
    int total_games = n * (n - 1) / 2;
    map<pair<int, int>, int> games_indexes;
    int global_index = 1;
    Net net(total_games + n + 2);

    for (int i = 1; i <= n; ++i)
        for (int j = 1; j <= n; ++j) {
            cin >> symbol;
            if (i == j) continue;
            int from = i, to = j;
            if (from > to) swap(from, to);
            if (!games_indexes[{from, to}])
                games_indexes[{from, to}] = global_index++;
            int c = 0;
            if (symbol == 'W' || symbol == '.') {
                c = 3;
            } else if (symbol == 'w') {
                c = 2;
            } else if (symbol == 'l') {
                c = 1;
            }
            net.add_oriented_edge(games_indexes[{from, to}], i + total_games, c);
        }
    for (int i = 1; i <= total_games; ++i)
        net.add_oriented_edge(net.start, i, 3);
    for (int i = 1; i <= n; ++i) {
        cin >> requested_points;
        net.add_oriented_edge(total_games + i, net.finish, requested_points);
    }

    find_max_flow(net);

    for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= n; ++j) {
            if (i == j) {
                cout << '#';
                continue;
            }
            int from = i, to = j;
            if (from > to) swap(from, to);
            string team_result;
            switch (net.get_edge_flow(games_indexes[{from, to}], total_games + i)) {
                case 3:
                    team_result = 'W';
                    break;
                case 2:
                    team_result = 'w';
                    break;
                case 1:
                    team_result = 'l';
                    break;
                default:
                    team_result = 'L';
            }
            cout << team_result;
        }
        cout << '\n';
    }
}
