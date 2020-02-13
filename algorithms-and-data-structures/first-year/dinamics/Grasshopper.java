package Algorithms.dinamics;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class Grasshopper {
    static private long values[];
    static private long money[];
    static private int path[];

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader("input.txt");
        Stack<Integer> reverse = new Stack<>();
        PrintWriter writer = new PrintWriter("output.txt");
        int n = reader.nextInt();
        int k = reader.nextInt();
        makeArrays(n);
        for (int i = 2; i < n; i++) {
            values[i] = reader.nextInt();
        }
        values[n] = 0;
        values[1] = 0;
        for (int i = 2; i <= n; i++) {
            int min = Math.min(k, i);
            money[i] = money[i - 1] + values[i];
            path[i] = i - 1;
            for (int j = 2; j <= min; j++) {
                if (money[i] < money[i - j] + values[i]) {
                    money[i] = money[i - j] + values[i];
                    path[i] = i - j;
                }
            }
        }
        writer.print(money[n] + "\n");
        int index = n;
        int count = -1;
        while (index > 0) {
            reverse.push(index);
            count++;
            index = path[index];
        }
        writer.print(count + "\n");
        while (!reverse.isEmpty()) {
            writer.print(reverse.pop() + " ");
        }
        writer.close();
    }

    static private void makeArrays(int n) {
        money = new long[n + 1];
        path = new int[n + 1];
        values = new long[n + 1];
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader(String name) throws FileNotFoundException {
            br = new BufferedReader(new
                    InputStreamReader(new
                    FileInputStream(name)));
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
