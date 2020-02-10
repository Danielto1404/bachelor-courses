package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.math.BigInteger;

public class Num2Brackets2 {
    private static BigInteger d[][];

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("num2brackets2.in")));
        PrintWriter writer = new PrintWriter("num2brackets2.out");
        String[] help = reader.readLine().split("\\s");
        int n = Integer.parseInt(help[0]);
        BigInteger k = new BigInteger(help[1]);
        k = k.add(BigInteger.ONE);
        makeD(n);
        writer.print(findBrackets(k, n));
        writer.close();
    }

    private static void makeD(int n) {
        d = new BigInteger[n * 2 + 1][n + 1];
        for (int i = 0; i <= n * 2; i++)
            for (int j = 0; j <= n; j++)
                d[i][j] = BigInteger.ZERO;
        d[0][0] = BigInteger.ONE;
        for (int i = 0; i < n * 2; i++)
            for (int j = 0; j <= n; j++) {
                if (j + 1 <= n)
                    d[i + 1][j + 1] = d[i + 1][j + 1].add(d[i][j]);
                if (j > 0)
                    d[i + 1][j - 1] = d[i + 1][j - 1].add(d[i][j]);
            }
    }

    private static String findBrackets(BigInteger k, int n) {
        StringBuilder ans = new StringBuilder();
        int openedBrackets = 0;
        char[] currentBrackets = new char[n * 2];
        int currentBracketsSize = 0;
        for (int i = n * 2 - 1; i >= 0; --i) {
            BigInteger curIndex;
            if (openedBrackets + 1 <= n)
                curIndex = d[i][openedBrackets + 1].shiftLeft((i - openedBrackets - 1) / 2);
            else
                curIndex = BigInteger.ZERO;
            if (curIndex.compareTo(k) >= 0) {
                ans.append('(');
                currentBrackets[currentBracketsSize++] = '(';
                openedBrackets++;
                continue;
            }
            k = k.subtract(curIndex);
            if (currentBracketsSize > 0 && currentBrackets[currentBracketsSize - 1] == '(' && openedBrackets - 1 >= 0)
                curIndex = d[i][openedBrackets - 1].shiftLeft((i - openedBrackets + 1) / 2);
            else
                curIndex = BigInteger.ZERO;
            if (curIndex.compareTo(k) >= 0) {
                ans.append(')');
                currentBracketsSize--;
                openedBrackets--;
                continue;
            }
            k = k.subtract(curIndex);
            if (openedBrackets + 1 <= n)
                curIndex = d[i][openedBrackets + 1].shiftLeft((i - openedBrackets - 1) / 2);
            else
                curIndex = BigInteger.ZERO;
            if (curIndex.compareTo(k) >= 0) {
                ans.append('[');
                currentBrackets[currentBracketsSize++] = '[';
                openedBrackets++;
                continue;
            }
            k = k.subtract(curIndex);
            ans.append(']');
            currentBracketsSize--;
            openedBrackets--;
        }
        return ans.toString();
    }
}
