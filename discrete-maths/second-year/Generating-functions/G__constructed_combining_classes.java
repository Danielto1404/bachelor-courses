import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class G__constructed_combining_classes {

    private static final int ELEMENTS_CNT = 7;
    private static final long[] ZERO_ARRAY = {0, 0, 0, 0, 0, 0, 0};
    private static final int BASIC_OFFSET = 2;

    private static class Pair {
        String left;
        String right;

        Pair(String leftArgument, String rightArgument) {
            this.left = leftArgument;
            this.right = rightArgument;
        }
    }

    private static Pair parsePair(String expression) {
        int balance = 0, cutPosition = 0;
        for (int i = 0; i < expression.length(); ++i) {
            switch (expression.charAt(i)) {
                case '(':
                    ++balance;
                    break;
                case ')':
                    --balance;
                    break;
                case ',':
                    if (balance == 1) {
                        cutPosition = i;
                        return new Pair(
                                expression.substring(BASIC_OFFSET, cutPosition),
                                expression.substring(cutPosition + 1, expression.length() - 1));
                    }
            }
        }
        return new Pair("DANIIL", "KOROLEV");

    }

    private static long[] calculate(String expression) {
        char object = expression.charAt(0);
        if (object == 'B') {
            return new long[]{0, 1, 0, 0, 0, 0, 0};
        }
        long[] weights = Arrays.copyOf(ZERO_ARRAY, ZERO_ARRAY.length);
        long[] inner;
        String insideExpression = expression.substring(BASIC_OFFSET, expression.length() - 1);
        switch (object) {
            case 'L':
                inner = calculate(insideExpression);
                weights[0] = 1;
                for (int w = 1; w < ELEMENTS_CNT; ++w) {
                    for (int k = 1; k <= w; ++k) {
                        weights[w] += inner[k] * weights[w - k];
                    }
                }
                return weights;
            case 'S':
                inner = calculate(insideExpression);
                weights[0] = 1;
                long[][] matrix = new long[ELEMENTS_CNT][ELEMENTS_CNT];
                for (int i = 0; i < ELEMENTS_CNT; ++i) {
                    matrix[0][i] = 1;
                }
                for (int i = 1; i < ELEMENTS_CNT; ++i) {
                    for (int j = 1; j < ELEMENTS_CNT; ++j) {
                        for (int k = 0; k <= i / j; ++k) {
                            long n = Math.max(inner[j] + k - 1, 0);
                            matrix[i][j] += matrix[i - j * k][j - 1] *
                                    combinationsWithPlacements(n - k, n) /
                                    combinationsWithPlacements(1, k);
                        }
                    }
                    weights[i] = matrix[i][i];
                }
                return weights;
            default:
                Pair arguments = parsePair(expression);
                long[] left = calculate(arguments.left);
                long[] right = calculate(arguments.right);
                for (int w = 0; w < ELEMENTS_CNT; ++w) {
                    for (int k = 0; k <= w; ++k) {
                        weights[w] += left[k] * right[w - k];
                    }
                }
                return weights;
        }
    }

    private static long combinationsWithPlacements(long k, long n) {
        long result = 1;
        for (long i = k + 1; i <= n; ++i) {
            result *= i;
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(
                Arrays.stream(calculate(new Scanner(System.in).nextLine()))
                        .mapToObj(Long::toString)
                        .collect(Collectors.joining(" ")));
    }
}