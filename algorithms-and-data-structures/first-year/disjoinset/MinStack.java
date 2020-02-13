package Algorithms.disjoinset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MinStack {
    static int size = 0;

    public static void main(String[] args) {
        FastReader reader = new FastReader();
        int n = reader.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            int cur = reader.nextInt();
            if (cur == 1) {
                int x = reader.nextInt();
                size++;
                if (size == 1) {
                    array[size - 1] = x;
                } else {
                    if (x < array[size - 2])
                        array[size - 1] = x;
                    else
                        array[size - 1] = array[size - 2];
                }
            } else if (cur == 2) {
                size--;
            } else {
                System.out.println(array[size - 1]);
            }
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
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
    }
}
