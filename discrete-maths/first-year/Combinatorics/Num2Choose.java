package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class Num2Choose {
    static BigInteger[] factorial = new BigInteger[31];
    static ArrayList<Integer> choose = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("/Users/daniilkorolev/Documents/JavaWorkingSpace/src/input.txt")));
        PrintWriter writer = new PrintWriter(System.out);
        String[] num = reader.readLine().split("\\s");
        int n = Integer.parseInt(num[0]);
        int k = Integer.parseInt(num[1]);
        long m = Long.parseLong(num[2]);
        makeFactorials(factorial);
        numToChoose(n, k, m);
        for (int anIN : choose) {
            writer.print(anIN + " ");
        }
        writer.close();
    }

    private static void numToChoose(int n, int k, long m) {
        int cur = 1;
        while (k > 0 && n >= 0) {
            if (m < C_N_K(n - 1, k - 1)) {
                choose.add(cur);
                k--;
            } else {
                m -= C_N_K(n - 1, k - 1);
            }
            n--;
            cur++;
        }
    }

    private static long C_N_K(int n, int k) {
        if (n < k) {
            return 0;
        }
        return Long.parseLong((factorial[n].divide(factorial[n - k])).divide(factorial[k]).toString());
    }

    private static void makeFactorials(BigInteger[] factorial) {
        factorial[0] = BigInteger.ONE;
        for (int i = 1; i < factorial.length; i++) {
            factorial[i] = factorial[i - 1].multiply(new BigInteger(i + ""));
        }
    }
}
