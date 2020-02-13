#include <iostream>
#include <vector>

using namespace std;

const long INF = 2 * 10 * 10 * 10 * 10 * 10 * 10 * 10 * 10 * 10; // 10^9

struct edge {
    int from, to, w;
    edge(int from, int to, int w) : from(from), to(to), w(w) {}
};

int main() {
    int N, M, K, S, from, to, weight;
    cin >> N >> M >> K >> S;
    auto edges = vector <edge>();
    auto d = vector <vector <long>>(N, vector <long>(K + 1, INF));
    for (int i = 0; i < M; ++i) {
        cin >> from >> to >> weight;
        --from;
        --to;
        edges.emplace_back(from, to, weight);
    }
    --S;
    d[S][0] = 0;
    for (int i = 1; i <= K; ++i)
        for (const auto& e: edges)

            // Смотрим, чтобы можно было добраться до from за i-1 шаг
            // Если e.w < 0, а d[e.from][i-1] == +INF, то мы получим, что d[e.from][i-1] + e.w < +INF => upd d[e.to]
            // Хотя на самом деле мы не можем добратся до from за i-1 шаг, то есть d[e.to][i] должен остаться +INF
            if (d[e.from][i - 1] != INF)
                d[e.to][i] = min(d[e.to][i], d[e.from][i - 1] + e.w);

    for (int i = 0; i < N; ++i)
        cout << (d[i][K] == INF ? -1 : d[i][K]) << '\n';
}
