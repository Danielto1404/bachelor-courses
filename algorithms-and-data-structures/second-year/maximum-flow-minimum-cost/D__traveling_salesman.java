import java.util.*;
import java.util.stream.IntStream;
 
public class D__traveling_salesman {
 
    static class Pair {
        long distance;
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
 
        public long johnsonCost(long[] potentials) {
            return weight + potentials[from] - potentials[to];
        }
    }
 
    static class Network {
        private static final long INF = Long.MAX_VALUE;
 
        private final int n, start, finish;
        private final ArrayList<ArrayList<Edge>> edges = new ArrayList<>();
        private long[] potentials;
 
        public Network(int n) {
            this.n = n;
            this.start = 0;
            this.finish = n - 1;
            potentials = new long[n];
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
            Edge[] path = new Edge[n];
            long result = 0;
 
            while (true) {
                long[] distance = quickDijkstra(path);
                if (distance[finish] == INF) break;
 
                potentials = IntStream.range(0, n)
                        .mapToLong(v -> distance[v] != INF ? potentials[v] + distance[v] : 0L)
                        .toArray();
 
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
 
        private long[] quickDijkstra(Edge[] path) {
            long[] distance = new long[n];
            Arrays.fill(distance, INF);
            Arrays.fill(path, null);
 
            distance[start] = 0;
            PriorityQueue<Pair> q = new PriorityQueue<>(Pair.PairComparator);
            for (Edge e : edges.get(start))
                if (e.currentCapacity() > 0) {
                    distance[e.to] = e.johnsonCost(potentials);
                    path[e.to] = e;
                }
 
            for (int v = 0; v < n; ++v)
                if (v != start)
                    q.add(new Pair(distance[v], v));
 
            while (!q.isEmpty()) {
                Pair way = q.poll();
                long curMinPath = way.distance;
                int v = way.index;
 
                if (distance[v] < curMinPath || distance[v] == INF)
                    continue; // Добавляли эту вершину либо не пришли к ней за конечный путь !!!
 
                for (Edge e : edges.get(v)) {
                    if (e.currentCapacity() > 0)
                        if (distance[e.to] > distance[e.from] + e.johnsonCost(potentials)) {
                            distance[e.to] = distance[e.from] + e.johnsonCost(potentials);
                            path[e.to] = e;
                            q.add(new Pair(distance[e.from] + e.johnsonCost(potentials), e.to));
                        }
                }
            }
            return distance;
        }
    }
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Network network = new Network(2 * n + 2);
 
        IntStream.rangeClosed(1, n).forEach(i -> {
            long weight = sc.nextLong();
            network.addOrientedEdge(network.start, i, 1, 0);
            network.addOrientedEdge(i, n + i, 1, weight);
            network.addOrientedEdge(i + n, i, n, 0);
            network.addOrientedEdge(i + n, network.finish, 1, 0);
        });
 
        IntStream.range(0, m).map(i -> sc.nextInt()).forEach(from -> {
            int to = sc.nextInt();
            long weight = sc.nextLong();
            network.addOrientedEdge(from, n + to, m, weight);
        });
 
        System.out.println(network.findMaxFlowMinCost());
    }
}
