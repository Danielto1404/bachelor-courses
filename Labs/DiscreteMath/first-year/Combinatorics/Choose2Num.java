package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class Choose2Num {
    static BigInteger[] factorial = new BigInteger[31];

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("/Users/daniilkorolev/Documents/JavaWorkingSpace/src/input.txt")));
        PrintWriter writer = new PrintWriter(System.out);
        String[] num = reader.readLine().split("\\s");
        int n = Integer.parseInt(num[0]);
        int k = Integer.parseInt(num[1]);
        ArrayList<Integer> choose = new ArrayList<>();
        choose.add(0);
        makeFactorials(factorial);
        num = reader.readLine().split("\\s");
        for (String cur : num) {
            choose.add(Integer.parseInt(cur));
        }
        writer.print(choose2num(choose, n, k));
        writer.close();
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

    private static int choose2num(ArrayList<Integer> choose, int n, int k) {
        int num = 0;
        for (int i = 1; i <= k; i++) {
            for (int j = choose.get(i - 1) + 1; j <= choose.get(i) - 1; j++) {
                num += C_N_K(n - j, k - i);
            }
        }
        return num;
    }
}
