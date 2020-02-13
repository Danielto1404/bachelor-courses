package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NextVector {
    public static void main(String[] a) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("nextvector.in"));
        PrintWriter writer = new PrintWriter("nextvector.out");
        String vector = sc.nextLine();
        final int[] bitsPrev = new int[vector.length()];
        final int[] bitsNext = new int[vector.length()];
        for (int i = 0; i < vector.length(); i++) {
            bitsPrev[i] = Integer.parseInt(vector.charAt(i) + "");
            bitsNext[i] = Integer.parseInt(vector.charAt(i) + "");
        }
        int[] prev = previousVector(bitsPrev);
        int[] next = nextVector(bitsNext);
        if (prev != null) {
            for (int bit : prev) {
                writer.print(bit);
            }
        } else {
            writer.print("-");
        }
        writer.print("\n");
        if (next != null) {
            for (int bit : next) {
                writer.print(bit);
            }
        } else {
            writer.print("-");
        }
        writer.close();
    }

    private static int[] nextVector(int[] a) {
        int n = a.length - 1;
        while (n >= 0 && a[n] != 0) {
            a[n] = 0;
            n--;
        }
        if (n == -1)
            return null;
        a[n] = 1;
        return a;
    }

    private static int[] previousVector(int[] a) {
        int n = a.length - 1;
        while (n >= 0 && a[n] != 1) {
            n--;
        }
        if (n == -1) return null;
        else {
            a[n] = 0;
            for (int i = n + 1; i < a.length; i++) {
                a[i] = 1;
            }
            return a;
        }
    }
}
