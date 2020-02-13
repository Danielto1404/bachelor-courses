package Discrete_Mathematics.BooleanFunctions;

import java.util.*;

public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int size = (int) Math.pow(2, n);
        int[][] matrix = new int[size][n];
        int[] pairs = new int[2 * m];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = getBit(i, n - j - 1);
            }
        }
        for (int i = 0; i < 2 * m; i++) {
            pairs[i] = sc.nextInt();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 2 * m; j += 2) {
                int first = pairs[j];
                int seсond = pairs[j + 1];
                int firstBit = matrix[i][Math.abs(first) - 1];
                int secondBit = matrix[i][Math.abs(seсond) - 1];
                if (first < 0) {
                    firstBit = invert(firstBit);
                }
                if (seсond < 0) {
                    secondBit = invert(secondBit);
                }
                if (secondBit == 0 && firstBit == 0) {
                    break;
                } else {
                    if (j == 2 * (m - 1)) {
                        System.out.println("NO");
                        return;
                    } else {
                        continue;
                    }
                }
            }
        }
        System.out.println("YES");
    }

    public static int getBit(int number, int shift) {
        return (number >> shift) % 2;
    }

    public static int invert(int number) {
        if (number == 0) return 1;
        else return 0;
    }
}
