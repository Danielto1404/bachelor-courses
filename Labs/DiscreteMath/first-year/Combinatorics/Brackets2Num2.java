package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.math.BigInteger;

public class Brackets2Num2 {

    private static int n;
    private static BigInteger answer = BigInteger.ZERO;
    private static String brackets;
    private static BigInteger[][] d;
    private static int opened = 0;
    private static BigInteger cur = BigInteger.ZERO;

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

    private static Boolean openedBrackets(char curBracket, StringBuilder temp, int i) {
        if (opened < n) {
            cur = d[2 * n - i - 1][opened + 1].shiftLeft((2 * n - i - opened - 2) / 2);
        } else {
            cur = BigInteger.ZERO;
        }
        if (brackets.charAt(i) == curBracket) {
            temp.append(curBracket);
            opened++;
            return true;
        }
        answer = answer.add(cur);
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("brackets2num2.in")));
        PrintWriter writer = new PrintWriter("brackets2num2.out");
        brackets = reader.readLine();
        n = brackets.length() / 2;
        makeD(n);
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 2 * n; i++) {
            if (openedBrackets('(', temp, i)) {
                continue;
            }
            if (temp.length() > 0 && temp.charAt(temp.length() - 1) == '(' && opened - 1 >= 0) {
                cur = d[2 * n - i - 1][opened - 1].shiftLeft((2 * n - i - opened) / 2);
            } else {
                cur = BigInteger.ZERO;
            }
            if (brackets.charAt(i) == ')') {
                temp.deleteCharAt(temp.length() - 1);
                opened--;
                continue;
            }
            answer = answer.add(cur);

            if (openedBrackets('[', temp, i)) {
                continue;
            }
            temp.deleteCharAt(temp.length() - 1);
            opened--;
        }
        writer.print(answer);
        writer.close();
    }
}

