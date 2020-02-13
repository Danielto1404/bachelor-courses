package Algorithms.dinamics;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class MaxIncreasingSequence {
    private static int pos;
    private static long[] length;
    private static int[] prev;

    public static void main(String[] args) {
        FastReader reader = new FastReader();
        final int n = reader.nextInt();
        final long[] sequence = new long[n];
        length = new long[n];
        prev = new int[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = reader.nextLong();
        }
        for (int i = 0; i < n; i++) {
            length[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; j++)
                if (sequence[j] < sequence[i] && length[i] < 1 + length[j]) {
                    length[i] = 1 + length[j];
                    prev[i] = j;
                }
        }
        System.out.println(findMax());
        for (int el : findElements()) {
            System.out.print(sequence[el] + " ");
        }

    }

    private static long findMax() {
        long maxLen = length[0];
        pos = 0;
        for (int i = 1; i < length.length; i++) {
            if (length[i] > maxLen) {
                pos = i;
                maxLen = length[i];
            }
        }
        return maxLen;
    }

    private static ArrayList<Integer> findElements() {
        ArrayList<Integer> elements = new ArrayList<>();
        while (pos != -1) {
            elements.add(pos);
            pos = prev[pos];
        }
        Collections.reverse(elements);
        return elements;
    }

    private static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}
