import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class D__explicit_formula {

    private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);

    private static class Fraction {
        private final BigInteger numerator;
        private final BigInteger denominator;

        Fraction(BigInteger numerator) {
            this.numerator = numerator;
            this.denominator = BigInteger.ONE;
        }

        Fraction(BigInteger numerator, BigInteger denominator) {
            BigInteger gcd = numerator.abs().gcd(denominator.abs());
            this.numerator = numerator.divide(gcd);
            this.denominator = denominator.divide(gcd);
        }

        public Fraction multiply(final BigInteger val) {
            Fraction other = new Fraction(val);
            BigInteger numerator = this.numerator.multiply(other.numerator);
            BigInteger denominator = this.denominator.multiply(other.denominator);
            return new Fraction(numerator, denominator);
        }

        public Fraction divide(BigInteger val) {
            Fraction other = new Fraction(val);
            BigInteger numerator = this.numerator.multiply(other.denominator);
            BigInteger denominator = this.denominator.multiply(other.numerator);
            return new Fraction(numerator, denominator);
        }

        public Fraction subtract(Fraction val) {
            Fraction other = new Fraction(val.numerator.negate(), val.denominator);
            return add(other);
        }

        public Fraction add(Fraction val) {
            BigInteger numerator = this.numerator.multiply(val.denominator).add(denominator.multiply(val.numerator));
            BigInteger denominator = this.denominator.multiply(val.denominator);
            return new Fraction(numerator, denominator);
        }

        @Override
        public String toString() {
            return numerator + "/" + denominator;
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BigInteger r;
        int k;
        r = new BigInteger(sc.next());
        k = sc.nextInt();

        ArrayList<BigInteger> initialP = new ArrayList<>();
        for (int i = 0; i <= k; ++i)
            initialP.add(new BigInteger(sc.next()));

        ArrayList<BigInteger> r_powers = new ArrayList<>();
        r_powers.add(BigInteger.ONE);
        for (int i = 1; i <= k; ++i)
            r_powers.add(r_powers.get(i - 1).multiply(r));

        // PASCAL TRIANGLE
        int MAX_DEGREE = 11;
        ArrayList<ArrayList<BigInteger>> choose = new ArrayList<>();
        for (int i = 0; i < MAX_DEGREE + 1; ++i) {
            choose.add(new ArrayList<>());
            for (int j = 0; j < MAX_DEGREE + 1; ++j)
                choose.get(i).add(BigInteger.ZERO);
        }

        ArrayList<BigInteger> first_row = new ArrayList<>(Collections.nCopies(MAX_DEGREE + 1, BigInteger.ZERO));
        first_row.set(0, BigInteger.ONE);
        choose.set(0, first_row);
        for (int i = 1; i < MAX_DEGREE + 1; ++i) {
            choose.get(i).set(0, BigInteger.ONE);
            choose.get(i).set(i, BigInteger.ONE);
            for (int j = 1; j < MAX_DEGREE; ++j) {
                choose.get(i).set(j, choose.get(i - 1).get(j).add(choose.get(i - 1).get(j - 1)));
            }
        }

        ArrayList<BigInteger> Q = expand(1 + k, r, choose);

        ArrayList<BigInteger> coefficients = new ArrayList<>();
        for (int i = 1; i < Q.size(); ++i)
            coefficients.add(MINUS_ONE.multiply(Q.get(i)));


        ArrayList<BigInteger> initial_values = new ArrayList<>();
        for (int i = 0; i <= k; ++i)
            initial_values.add(initialP.get(i).add(recurrent_value(initial_values, coefficients, i)));


        ArrayList<ArrayList<BigInteger>> linear_system = new ArrayList<>();
        for (int i = 0; i <= k; ++i)
            linear_system.add(find_coefficients_by_index(i, k, r_powers));


        BigInteger[][] system = new BigInteger[linear_system.size()][];
        for (int i = 0; i < system.length; ++i) {
            system[i] = linear_system.get(i).toArray(new BigInteger[0]);
        }


        BigInteger[] right_part = initial_values.toArray(new BigInteger[0]);
        Fraction[] answers = solve_linear_system(system, right_part);

        Arrays.stream(answers).forEach(fraction -> System.out.print(fraction + " "));
    }

    private static ArrayList<BigInteger> expand(int n,
                                                final BigInteger value,
                                                final ArrayList<ArrayList<BigInteger>> choose) {
        ArrayList<BigInteger> result = new ArrayList<>();
        for (int k = 0; k <= n; ++k) {
            BigInteger sign = k % 2 == 0 ? BigInteger.ONE : MINUS_ONE;
            result.add(sign.multiply(choose.get(n).get(k)).multiply(power(value, k)));
        }
        return result;
    }

    private static BigInteger power(final BigInteger value, int k) {
        BigInteger powered = BigInteger.ONE;
        for (int i = 0; i < k; ++i)
            powered = powered.multiply(value);
        return powered;
    }

    private static BigInteger recurrent_value(final ArrayList<BigInteger> initial,
                                              final ArrayList<BigInteger> coefficients,
                                              int k) {
        BigInteger value = BigInteger.ZERO;
        for (int i = 0; i < k; ++i) {
            value = value.add(coefficients.get(i).multiply(initial.get(k - i - 1)));
        }
        return value;
    }

    private static ArrayList<BigInteger> find_coefficients_by_index(int value,
                                                                    int degree,
                                                                    final ArrayList<BigInteger> r_powers) {
        ArrayList<BigInteger> result = new ArrayList<>();
        for (int i = 0; i <= degree; ++i)
            result.add(power(BigInteger.valueOf(value), i).multiply(r_powers.get(value)));
        return result;
    }

    static public Fraction[] solve_linear_system(final BigInteger[][] linear_system,
                                                 final BigInteger[] rhs) {
        final int rL = linear_system.length;
        final int cL = linear_system[0].length;
        Fraction[] x = new Fraction[rL];
        for (int c = 0; c < cL; c++)
            x[c] = new Fraction(rhs[c]);

        for (int c = 0; c < cL - 1; c++) {
            if (linear_system[c][c].compareTo(BigInteger.ZERO) == 0) {
                for (int r = c + 1; r < rL; r++)
                    if (linear_system[r][c].compareTo(BigInteger.ZERO) != 0) {
                        for (int cpr = c; cpr < cL; cpr++) {
                            BigInteger tmp = linear_system[c][cpr];
                            linear_system[c][cpr] = linear_system[r][cpr];
                            linear_system[r][cpr] = tmp;
                        }
                        Fraction tmp = x[c];
                        x[c] = x[r];
                        x[r] = tmp;
                        break;
                    }
            }
            for (int r = c + 1; r < rL; r++) {
                for (int cpr = c + 1; cpr < cL; cpr++) {
                    BigInteger tmp = linear_system[c][c].multiply(
                            linear_system[r][cpr]).subtract(
                            linear_system[c][cpr].multiply(
                                    linear_system[r][c]));
                    linear_system[r][cpr] = tmp;
                }
                Fraction tmp = x[r].multiply(linear_system[c][c]).subtract(x[c].multiply(linear_system[r][c]));
                x[r] = tmp;
            }
        }
        for (int r = cL - 1; r >= 0; r--) {
            x[r] = x[r].divide(linear_system[r][r]);
            for (int rpr = r - 1; rpr >= 0; rpr--)
                x[rpr] = x[rpr].subtract(x[r].multiply(linear_system[rpr][r]));
        }
        return x;
    }
}

