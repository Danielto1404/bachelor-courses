import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class H__avoiding_left_brushes {
    private static final long MOD = 998244353;

    private static long applyMod(long value) {
        value %= MOD;
        return value + (value < 0 ? MOD : 0);
    }

    static class ReversePascalTriangle {
        private final long[][] choose;

        public ReversePascalTriangle(int n) {
            choose = new long[n + 1][n + 1];
            choose[0][0] = 1L;
            for (int N = 1; N < choose.length; ++N) {
                choose[0][N] = 1L;
                choose[N][N] = 1L;
                for (int K = 1; K < N; ++K) {
                    choose[K][N] = applyMod(choose[K][N - 1] + choose[K - 1][N - 1]);
                }
            }
        }

        public long get(int i, int j) {
            return choose[i][j];
        }
    }

    static class ArithmeticPolynom {
        private final ArrayList<Long> coefficients;

        public ArithmeticPolynom(int degree) {
            coefficients = new ArrayList<>(Collections.nCopies(degree + 1, 0L));
        }

        public void setCoefficient(int index, long value) {
            coefficients.set(index, value);
        }

        public long getCoefficient(int index) {
            return index < coefficients.size() ? coefficients.get(index) : 0L;
        }

        public ArithmeticPolynom divide(ArithmeticPolynom other, int n) {
            ArithmeticPolynom result = new ArithmeticPolynom(n - 1);

            for (int k = 0; k < n; ++k) {
                long sum = 0;
                for (int i = 0; i < k; ++i) {
                    long mul = applyMod(result.getCoefficient(i) * other.getCoefficient(k - i));
                    sum = applyMod(sum + mul);
                }
                long sub = applyMod(getCoefficient(k) - sum);
                result.setCoefficient(k, applyMod(sub / other.getCoefficient(0)));
            }
            return result;
        }

        @Override
        public String toString() {
            return coefficients
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int n = sc.nextInt();
        ReversePascalTriangle triangle = new ReversePascalTriangle(k);

        int numeratorDegree = ((k - 1) % 2 == 0 ? -1 : 0) + (k - 1) / 2;
        int denominatorDegree = (k % 2 == 0 ? -1 : 0) + k / 2;

        ArithmeticPolynom numerator = new ArithmeticPolynom(numeratorDegree);
        ArithmeticPolynom denominator = new ArithmeticPolynom(denominatorDegree);

        for (int i = 0; i <= numeratorDegree; ++i) {
            long value = applyMod((i % 2 == 0 ? 1 : -1) * triangle.get(i, k - i - 2));
            numerator.setCoefficient(i, value);
        }

        for (int i = 0; i <= denominatorDegree; ++i) {
            long value = applyMod((i % 2 == 0 ? 1 : -1) * triangle.get(i, k - i - 1));
            denominator.setCoefficient(i, value);
        }

        System.out.println(numerator.divide(denominator, n));
    }
}
