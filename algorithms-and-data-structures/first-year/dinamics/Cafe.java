package Algorithms.dinamics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Cafe {
    private static int n;
    private static int[] costs;
    private static long[][] money;
    private static ArrayList<Integer> path = new ArrayList<>();
    private static long min;
    private static int index;
    private static final int inf = 300_002;

    public static void main(String[] args) {
        FastReader reader = new FastReader();
        n = reader.nextInt();
        if (n == 0) {
            System.out.println(0);
            System.out.println(0 + " " + 0);
        } else {
            costs = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                costs[i] = reader.nextInt();
            }
            makeMoney();
            System.out.println(min);
            findPath();
            System.out.println(index + " " + path.size());
            Collections.reverse(path);
            for (int day : path) {
                System.out.println(day);
            }
        }
    }

    private static void findPath() {
        int i = n;
        int j = index;
        while (i > 1) {
            if (costs[i] > 100) {
                if (j > 0 && j < n) {
                    if (money[i - 1][j - 1] + costs[i] <= money[i - 1][j + 1]) {
                        j--;
                    } else {
                        path.add(i);
                        j++;
                    }
                    i--;
                    continue;
                }
                if (j == 0) {
                    path.add(i);
                    j++;
                    i--;
                    continue;
                }
                if (j == n) {
                    j--;
                    i--;
                }
            } else {
                if (j == n) {
                    i--;
                } else {
                    if (money[i - 1][j] + costs[i] > money[i - 1][j + 1]) {
                        path.add(i);
                        j++;
                    }
                    i--;
                }
            }
        }
    }

    private static void makeMoney() {
        money = new long[n + 1][n + 1];
        for (int i = 2; i <= n; i++) {
            money[1][i] = inf;
        }
        if (costs[1] <= 100) {
            money[1][0] = costs[1];
            money[1][1] = inf;
        } else {
            money[1][0] = inf;
            money[1][1] = costs[1];
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (costs[i] > 100) {
                    if (j == n) {
                        money[i][n] = money[i - 1][n - 1] + costs[i];
                    }
                    if (j == 0) {
                        money[i][0] = money[i - 1][1];
                    }
                    if (j > 0 && j < n) {
                        money[i][j] = Math.min(money[i - 1][j - 1] + costs[i], money[i - 1][j + 1]);
                    }
                } else {
                    if (j == n) {
                        money[i][n] = inf;
                    } else {
                        money[i][j] = Math.min(money[i - 1][j] + costs[i], money[i - 1][j + 1]);
                    }
                }
            }
        }
        index = 0;
        min = money[n][0];
        for (int i = 1; i <= n; i++) {
            if (min >= money[n][i]) {
                min = money[n][i];
                index = i;
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
            while (st == null || !st.hasMoreElements()) {
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