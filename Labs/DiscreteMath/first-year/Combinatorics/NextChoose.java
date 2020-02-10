package Discrete_Mathematics.Combinatorics;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;

public class NextChoose {
    public static void main(String[] a) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("/Users/daniilkorolev/Documents/JavaWorkingSpace/src/input.txt"));
        PrintWriter writer = new PrintWriter(System.out);
        int n = sc.nextInt();
        int k = sc.nextInt();
        final int[] chosen = new int[k];
        for (int i = 0; i < k; i++) {
            chosen[i] = sc.nextInt();
        }
        int[] cur = nextChoose(chosen, k, n);
        if (!Arrays.equals(cur, chosen)) {
            writer.println(-1);
            writer.close();
        } else {
            for (int x : cur) {
                writer.print(x + " ");
            }
            writer.close();
        }
    }

    private static int[] nextChoose(int[] a, int k, int n) {
        int[] b = new int[k + 1];
        for (int i = 0; i < k; i++) {
            b[i] = a[i];
        }
        b[k] = n + 1;
        int i = k - 1;
        while ((i >= 0) && (b[i + 1] - b[i] < 2))
            i--;
        if (i >= 0) {
            b[i]++;
            for (int j = i + 1; j < k; j++) {
                b[j] = b[j - 1] + 1;
            }
            for (i = 0; i < k; i++)
                a[i] = b[i];
            return a;
        }
        return null;
    }
}
