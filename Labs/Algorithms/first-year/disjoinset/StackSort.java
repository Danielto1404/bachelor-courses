package Algorithms.disjoinset;

import java.io.*;
import java.util.*;

public class StackSort {
    public static void main(String[] args) {
        FastReader reader = new FastReader();
        Stack<Integer> stack = new Stack<>();
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<Integer> stacked = new ArrayList<>();
        int n = reader.nextInt();
        for (int i = 0; i < n; i++) {
            int curNumber = reader.nextInt();
            if (stack.empty()) {
                stack.push(curNumber);
                commands.add("push");
            } else {
                while (!stack.empty() && curNumber > stack.peek()) {
                    stacked.add(stack.pop());
                    commands.add("pop");
                }
                stack.push(curNumber);
                commands.add("push");
            }
        }
        while (!stack.empty()) {
            stacked.add(stack.pop());
            commands.add("pop");
        }
        for (int i = 0; i < n - 1; i++) {
            if (stacked.get(i) > stacked.get(i + 1)) {
                System.out.println("impossible");
                return;
            }
        }
        for (String operation : commands) {
            System.out.println(operation);
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        private FastReader() {
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