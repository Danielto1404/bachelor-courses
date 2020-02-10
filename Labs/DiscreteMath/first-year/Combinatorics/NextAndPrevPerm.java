package Discrete_Mathematics.Combinatorics;

import java.io.*;

public class NextAndPrevPerm {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("nextperm.in")));
        PrintWriter writer = new PrintWriter("nextperm.out");
        reader.readLine();
        String[] tmp = reader.readLine().split("\\s");
        int[] cur1 = new int[tmp.length];
        int[] cur2 = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            cur1[i] = Integer.parseInt(tmp[i]);
            cur2[i] = cur1[i];
        }
        int[] x = prevPerm(cur1);
        int[] r = nextPermutation(cur2);
        for (int f : x) {
            writer.print(f + " ");
        }
        writer.print("\n");
        for (int l : r) {
            writer.print(l + " ");
        }
        writer.close();
    }

    private static int[] prevPerm(int[] cur) {
        final int size = cur.length;
        for (int i = size - 2; i >= 0; i--) {
            if (cur[i] > cur[i + 1]) {
                int maxIndex = i + 1;
                for (int j = i + 1; j < size; j++) {
                    if (cur[j] > cur[maxIndex] && cur[j] < cur[i]) {
                        maxIndex = j;
                    }
                }
                swap(cur, i, maxIndex);
                reverse(cur, i + 1, size - 1);
                return cur;
            }
        }
        return new int[size];
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void reverse(int[] a, int l, int r) {
        int left = l, right = r;
        while (left < right)
            swap(a, left++, right--);
    }

    private static int[] nextPermutation(int[] a) {
        int index = a.length - 2;
        while (index != -1 && a[index] > a[index + 1]) {
            index--;
        }
        if (index == -1)
            return new int[a.length];
        int k = a.length - 1;
        while (a[index] >= a[k]) {
            k--;
        }
        swap(a, index, k);
        int left = index + 1, right = a.length - 1;
        reverse(a, left, right);
        return a;
    }

}