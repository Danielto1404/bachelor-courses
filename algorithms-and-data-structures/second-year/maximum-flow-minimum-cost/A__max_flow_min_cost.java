import java.util.*;
import java.util.stream.IntStream;

public class A__max_flow_min_cost {

    static class Pair {
        Long distance;
        int index;

        public static final Comparator<Pair> PairComparator = Comparator.comparing(o -> o.distance);

        Pair(Long distance, int index) {
            this.distance = distance;
            this.index = index;
        }
    }

    static class Edge {
        final int from, to, reverseIndex;
        final long capacity, weight;
        long flow = 0;

        Edge(int from, int to, long capacity, long weight, int reverseIndex) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.weight = weight;
            this.reverseIndex = reverseIndex;
        }

        public long currentCapacity() {
            return capacity - flow;
        }

        public long johnsonCost(Long[] potentials) {
            return weight + potentials[from] - potentials[to];
        }
    }

    static class Network {
        private static final long INF = Long.MAX_VALUE;

        private final int n, start, finish;
        private final ArrayList<ArrayList<Edge>> edges = new ArrayList<>();
        private Long[] potentials;

        public Network(int n) {
            this.n = n;
            this.start = 0;
            this.finish = n - 1;
            potentials = new Long[n];
            Arrays.fill(potentials, 0L);
            for (int i = 0; i < n; ++i)
                edges.add(new ArrayList<>());
        }

        public void addOrientedEdge(int from, int to, long capacity, long weight) {
            int reverse_from_index = edges.get(to).size();
            int reverse_to_index = edges.get(from).size();
            edges.get(from).add(new Edge(from, to, capacity, weight, reverse_from_index));
            edges.get(to).add(new Edge(to, from, 0, -weight, reverse_to_index));
        }

        public long findMaxFlowMinCost() {
            boolean[] reached = new boolean[n];
            Edge[] path = new Edge[n];
            long result = 0;

            while (true) {
                long[] distance = dijkstra(path, reached);
                if (!reached[finish]) break;

                potentials = IntStream.range(0, n)
                        .mapToObj(v -> reached[v] ? potentials[v] + distance[v] : 0L)
                        .toArray(Long[]::new);

                long flow = INF;
                for (int v = finish; v != start; v = path[v].from)
                    flow = Math.min(flow, path[v].currentCapacity());

                for (int v = finish; v != start; v = path[v].from) {
                    Edge e = path[v];
                    Edge backwardEdge = edges.get(e.to).get(e.reverseIndex);
                    e.flow += flow;
                    backwardEdge.flow -= flow;
                    result += flow * e.weight;
                }
            }
            return result;
        }

        private long[] dijkstra(Edge[] path, boolean[] reached) {
            long[] distance = new long[n];
            Arrays.fill(distance, INF);
            Arrays.fill(reached, false);
            Arrays.fill(path, null);
            distance[start] = 0;

            while (true) {
                int nearestVertex = IntStream.range(0, n)
                        .filter(value -> !reached[value])
                        .filter(value -> distance[value] < INF)
                        .mapToObj(value -> new Pair(distance[value], value))
                        .min(Pair.PairComparator)
                        .orElse(new Pair(-1L, -1))
                        .index;

                if (nearestVertex == -1) break;
                reached[nearestVertex] = true;

                for (Edge e : edges.get(nearestVertex))
                    if (e.currentCapacity() > 0 && !reached[e.to])
                        if (distance[e.to] > distance[e.from] + e.johnsonCost(potentials)) {
                            distance[e.to] = distance[e.from] + e.johnsonCost(potentials);
                            path[e.to] = e;
                        }
            }
            return distance;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Network network = new Network(n);
        for (int i = 0; i < m; ++i)
            network.addOrientedEdge(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextLong(), sc.nextLong());

        System.out.println(network.findMaxFlowMinCost());
    }
}
