import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class B__hungarian {

    public static class Hungarian {
        private static final int INF = Integer.MAX_VALUE;
        private final ArrayList<ArrayList<Integer>> matrix;

        public Hungarian(ArrayList<ArrayList<Integer>> matrix) {
            this.matrix = new ArrayList<>();
            this.matrix.add(new ArrayList<>(Collections.nCopies(matrix.size() + 1, 0)));
            for (ArrayList<Integer> line : matrix) {
                this.matrix.add(new ArrayList<>(Collections.nCopies(1, 0)));
                this.matrix.get(this.matrix.size() - 1).addAll(line);
            }
        }

        public AnswerPair solve() {
            int[] rows = new int[matrix.size()];
            int[] cols = new int[matrix.size()];
            int[] matching = new int[matrix.size()];
            int[] way = new int[matrix.size()];

            for (int row = 1; row < matrix.size(); row++) {
                matching[0] = row;
                int[] minValues = new int[matrix.size()];
                boolean[] used = new boolean[matrix.size()];
                Arrays.fill(minValues, INF);
                int collumn = 0;
                do {
                    used[collumn] = true;
                    int curCol = 0;
                    int delta = INF;
                    int curRow = matching[collumn];

                    for (int i = 1; i < matrix.size(); i++) {
                        if (!used[i]) {
                            int curDelta = matrix.get(curRow).get(i) - rows[curRow] - cols[i];
                            if (curDelta < minValues[i]) {
                                minValues[i] = curDelta;
                                way[i] = collumn;
                            }
                            if (minValues[i] < delta) {
                                delta = minValues[i];
                                curCol = i;
                            }
                        }
                    }
                    for (int j = 0; j < matrix.size(); j++) {
                        if (used[j]) {
                            rows[matching[j]] += delta;
                            cols[j] -= delta;
                        } else {
                            minValues[j] -= delta;
                        }
                    }
                    collumn = curCol;

                } while (matching[collumn] != 0);

                do {
                    int prev = way[collumn];
                    matching[collumn] = matching[prev];
                    collumn = prev;
                } while (collumn > 0);
            }

            int[] result = new int[matrix.size() - 1];
            for (int j = 1; j < matrix.size(); j++) {
                result[matching[j] - 1] = j;
            }
            return new AnswerPair(-cols[0], result);
        }
    }

    public static class AnswerPair {
        private final int value;
        private final int[] indexes;

        public AnswerPair(int value, int[] indexes) {
            this.value = value;
            this.indexes = indexes;
        }

        @Override
        public String toString() {
            return value + "\n" + IntStream.range(0, indexes.length)
                    .mapToObj(i -> String.format("%d %d\n", i + 1, indexes[i]))
                    .collect(Collectors.joining());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        ArrayList<ArrayList<Integer>> matrix = IntStream.range(0, n)
                .mapToObj(i -> new ArrayList<Integer>())
                .peek(line ->
                        IntStream.range(0, n)
                                .mapToObj(j -> sc.nextInt()).forEach(line::add))
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println(new Hungarian(matrix).solve());
    }
}
