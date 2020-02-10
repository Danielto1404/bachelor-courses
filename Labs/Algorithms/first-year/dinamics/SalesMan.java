package Algorithms.dinamics;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class SalesMan {
    private static int n;
    private static long[][] length;
    private static FastReader reader = new FastReader();
    private static long[][] dp;
    private static int maxSize;
    private static final long inf = Integer.MAX_VALUE;
    private static ArrayList<Integer> path = new ArrayList<>();

    public static void main(String[] args) {
        n = reader.nextInt();
        maxSize = (int) Math.pow(2, n);
        scan();
        if (n == 1) {
            System.out.println(0);
            System.out.println(1);
        } else {
            long cheapestCost = start();
            findPath();
            System.out.println(
                    cheapestCost - length[path.get(path.size() - 1) - 1][path.get(path.size() - 2) - 1]);
            path.remove(path.size() - 1);
            Collections.reverse(path);
            for (int way : path) {
                System.out.print(way + " ");
            }
        }
    }


    private static void scan() {
        dp = new long[maxSize][maxSize];
        length = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                length[i][j] = reader.nextInt();
            }
        }
    }

    private static void print(long[][] a) {
        for (long[] anA : a) {
            for (int j = 0; j < a.length; j++) {
                System.out.printf("%15d", anA[j]);
            }
            System.out.println();
        }
    }

    private static long findCheapestWays(int i, int mask) {
        if (dp[i][mask] != inf) {
            return dp[i][mask];
        }
        for (int j = 0; j < n; j++) {
            if (i != j && getBit(mask, j) == 1) {
                dp[i][mask] = Math.min(dp[i][mask],
                        findCheapestWays(j, mask - (int) Math.pow(2, j)) + length[i][j]);
            }
        }
        return dp[i][mask];
    }

    private static int getBit(int number, int index) {
        return ((number >> index) % 2);
    }

    private static long start() {
        for (int i = 0; i < n; i++) {
            for (int mask = 0; mask < maxSize; mask++) {
                dp[i][mask] = inf;
            }
        }
        dp[0][0] = 0;
        return findCheapestWays(0, maxSize - 1);
    }

    private static void findPath() {
        int i = 0;
        int mask = maxSize - 1;
        path.add(1);
        while (mask != 0) {
            for (int j = 0; j < n; j++)
                if (i != j && getBit(mask, j) == 1
                        && dp[i][mask] == dp[j][mask - (int) Math.pow(2, j)] + length[i][j]) {
                    path.add(j + 1);
                    i = j;
                    mask = mask - (int) Math.pow(2, j);
                }
        }
    }

    private static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == (null) || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}