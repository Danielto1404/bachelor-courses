package Algorithms.dinamics;

import java.util.Scanner;

public class EditingDistance {
    private static String first;
    private static String second;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        first = sc.next();
        second = sc.next();
        System.out.println(findDistance());
    }

    private static int findDistance() {
        final int n = first.length() + 1;
        final int m = second.length() + 1;
        int[][] d = new int[n][m];
        for (int i = 0; i < n; i++) {
            d[i][0] = i;
        }
        for (int i = 0; i < m; i++) {
            d[0][i] = i;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1,
                        d[i - 1][j - 1] + diff(ch_Fi(i), ch_Se(j)));
            }
        }
        return d[n - 1][m - 1];
    }

    private static int diff(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private static char ch_Fi(int i) {
        return first.charAt(i - 1);
    }

    private static char ch_Se(int j) {
        return second.charAt(j - 1);
    }
}