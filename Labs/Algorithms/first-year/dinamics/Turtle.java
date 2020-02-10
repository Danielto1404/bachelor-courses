package Algorithms.dinamics;

import java.io.*;
import java.util.StringTokenizer;

public class Turtle {
    private static long[][] best;
    private static int[][] values;
    private static StringBuilder road = new StringBuilder();

    public static void main(String[] args) throws IOException {
        // FastReader reader = new FastReader("/Users/daniilkorolev/Documents/JavaWorkingSpace/src/input.txt");
        FastReader reader = new FastReader("input.txt");
        PrintWriter writer = new PrintWriter("output.txt");
        int n = reader.nextInt();
        int m = reader.nextInt();
        values = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                values[i][j] = reader.nextInt();
            }
        }
        makePath(n, m);
        writer.print(best[n - 1][m - 1] + "\n");
        findPath(n, m);
        writer.print(road);
        writer.close();
    }


    private static void makePath(int n, int m) {
        best = new long[n][m];
        best[0][0] = values[0][0];
        for (int i = 1; i < m; i++) {
            best[0][i] = best[0][i - 1] + values[0][i];
        }
        for (int i = 1; i < n; i++) {
            best[i][0] = best[i - 1][0] + values[i][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (best[i - 1][j] > best[i][j - 1]) {
                    best[i][j] = best[i - 1][j] + values[i][j];
                } else {
                    best[i][j] = best[i][j - 1] + values[i][j];
                }
            }
        }
    }

    private static void findPath(int n, int m) {
        int i = n - 1, j = m - 1;
        while (i > 0 || j > 0) {
            if (i == 0) {
                j--;
                road.append("R");
                continue;
            }
            if (j == 0) {
                i--;
                road.append("D");
                continue;
            }
            if (best[i - 1][j] > best[i][j - 1]) {
                i--;
                road.append("D");
            } else {
                j--;
                road.append("R");
            }
        }
        road.reverse();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader(String name) throws FileNotFoundException {
            br = new BufferedReader(new
                    InputStreamReader(new
                    FileInputStream(name)));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = (new StringTokenizer(br.readLine()));
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
