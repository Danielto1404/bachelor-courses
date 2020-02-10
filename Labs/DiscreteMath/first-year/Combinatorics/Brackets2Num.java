package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.math.BigInteger;

public class Brackets2Num {
    private static BigInteger[][] din;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(new FileInputStream("brackets2num.in")));
        PrintWriter writer = new PrintWriter("brackets2num.out");
        String line = reader.readLine();
        final int n = line.length() / 2;
        makeDin(n);
        writer.print(getNum(line).toString(10));
        writer.close();
    }

    private static void makeDin(int n) {
        din = new BigInteger[n * 2 + 1][n + 1];
        for (int i = 0; i <= 2 * n; i++) {
            for (int j = 0; j <= n; j++) {
                din[i][j] = BigInteger.ZERO;
            }
        }
        din[0][0] = BigInteger.ONE;
        for (int i = 1; i <= 2 * n; i++)
            for (int j = 0; j <= n; j++) {
                if (j > 0)
                    din[i][j] = din[i][j].add(din[i - 1][j - 1]);
                if (j < n)
                    din[i][j] = din[i][j].add(din[i - 1][j + 1]);
            }
    }

    private static BigInteger getNum(String line) {
        BigInteger num = BigInteger.ZERO;
        int depth = 0;
        for (int i = 0; i < line.length(); i++) {
            if (depth == line.length() / 2) {
                return BigInteger.ZERO;
            }
            if (line.charAt(i) == '(')
                depth++;
            else {
                num = num.add(din[line.length() - i - 1][depth + 1]);
                depth--;
            }
        }
        return num;
    }
}
