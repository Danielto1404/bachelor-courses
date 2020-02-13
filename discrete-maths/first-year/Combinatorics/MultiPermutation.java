package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.Arrays;

public class MultiPermutation {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("nextmultiperm.in")));
        PrintWriter writer = new PrintWriter("nextmultiperm.out");
        int n = Integer.parseInt(reader.readLine());
        String[] a = reader.readLine().split("\\s");
        int[] permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = Integer.parseInt(a[i]);
        }
        nextMulti(permutation);
        for (int i : permutation) {
            writer.print(i + " ");
        }
        writer.close();
    }

    private static int[] nextMulti(int[] a) {
        int i = a.length - 2;
        while ((i >= 0) && a[i] >= a[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = i + 1;
            while (j < a.length - 1 && a[j + 1] > a[i]) {
                j++;
            }
            swap(a, i, j);
            reverse(a, i + 1);
        } else {
            Arrays.fill(a, 0);
        }
        return a;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void reverse(int[] a, int i) {
        int left = i, right = a.length - 1;
        while (left < right)
            swap(a, left++, right--);
    }
}

