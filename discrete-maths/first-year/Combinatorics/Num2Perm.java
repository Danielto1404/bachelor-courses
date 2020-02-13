package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.ArrayList;

public class Num2Perm {
    final static long[] factorials = {
            1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800,
            479001600, 6227020800L,
            87178291200L,
            1307674368000L,
            20922789888000L,
            355687428096000L,
            6402373705728000L
    };

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("/Users/daniilkorolev/Documents/JavaWorkingSpace/src/input.txt")));
        PrintWriter writer = new PrintWriter(System.out);
        String[] nk = reader.readLine().split("\\s");
        int n = Integer.parseInt(nk[0]);
        long k = Long.parseLong(nk[1]);
        ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Integer> notUsed = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            notUsed.add(i + 1);
        }
        long l = 0;
        int var = n - 1;
        while (index.size() != n) {
            int i = 0;
            while (notUsed.size() >= 1) {
                if (l + i * factorials[var] <= k && k < l + factorials[var] * (i + 1)) {
                    l += i * factorials[var];
                    index.add(notUsed.get(i));
                    notUsed.remove(i);
                    var--;
                    break;
                }
                i++;
            }
        }
        for (int anINDEX : index) {
            writer.print(anINDEX + " ");
        }
        writer.close();
    }
}
