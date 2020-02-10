package Discrete_Mathematics.Combinatorics;

import java.io.*;

public class Num2Brackets {
    private static long[][] din;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("num2brackets.in")));
        PrintWriter writer = new PrintWriter("num2brackets.out");
        String[] x = reader.readLine().split("\\s");
        int n = Integer.parseInt(x[0]);
        long k = Long.parseLong(x[1]);
        makeDin(n);
        if (k == 0) {
            for (int i = 0; i < n; i++) {
                writer.print("(");
            }
            for (int i = 0; i < n; i++) {
                writer.print(")");
            }
            writer.close();
        } else {
            String cur = num2bracket(n, k + 1);
            for (char bracket : cur.toCharArray()) {
                writer.print(bracket);
                writer.close();
            }
        }
    }

    private static String num2bracket(int n, long k) {
        int balance = 0;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 2 * n; i++) {
            if (din[2 * n - (i + 1)][balance + 1] >= k) {
                s.append('(');
                balance++;
            } else {
                k -= din[2 * n - (i + 1)][balance + 1];
                s.append(')');
                balance--;
            }
        }
        return s.toString();
    }


    private static void makeDin(int n) {
        din = new long[n * 2 + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            din[0][i] = 0;
        }
        din[0][0] = 1;
        for (int i = 1; i <= 2 * n; i++) {
            for (int j = 0; j <= n; j++) {
                if (j > 0)
                    din[i][j] += din[i - 1][j - 1];
                if (j < n)
                    din[i][j] += din[i - 1][j + 1];
            }
        }
    }
}

