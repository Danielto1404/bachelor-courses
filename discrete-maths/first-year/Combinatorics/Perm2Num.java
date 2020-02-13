package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.ArrayList;

public class Perm2Num {
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
        int n = Integer.parseInt(reader.readLine());
        String[] cur = reader.readLine().split("\\s");
        ArrayList<Integer> permutation = new ArrayList<>();
        ArrayList<Integer> isUsed = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            permutation.add(Integer.parseInt(cur[i]));
            isUsed.add(i + 1);
        }
        long l = 0;
        int var = (n - 1);
        while (isUsed.size() >= 1) {
            int index = isUsed.indexOf(permutation.get(0));
            l += index * factorials[var];
            isUsed.remove(index);
            permutation.remove(0);
            var--;
        }
        writer.print(l);
        writer.close();
    }

}
