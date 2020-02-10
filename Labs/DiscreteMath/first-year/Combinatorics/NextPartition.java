package Discrete_Mathematics.Combinatorics;//package MATH_LAB_2;

import java.io.*;
import java.util.ArrayList;

public class NextPartition {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("/Users/daniilkorolev/Documents/JavaWorkingSpace/src/input.txt")));
        PrintWriter writer = new PrintWriter(System.out);
        String[] num = reader.readLine().split("['+=]");
        int n = Integer.parseInt(num[0]);
        ArrayList<Integer> partition = new ArrayList<>();
        for (int i = 1; i < num.length; i++) {
            partition.add(Integer.parseInt(num[i]));
        }
        if (partition.get(0) == n) {
            writer.write("No solution");
        } else {
            partitions(partition);
            writer.write(n + "=");
            int index = 0;
            while (index < partition.size() - 1) {
                writer.print(partition.get(index) + "+");
                index++;
            }
            writer.print(partition.get(index));
        }
        writer.close();
        System.out.println(partition);
    }

    private static void partitions(ArrayList<Integer> cur) {
        int size = cur.size() - 1;
        cur.set(size, cur.get(size) - 1);
        cur.set(size - 1, cur.get(size - 1) + 1);
        if (cur.get(cur.size() - 2) > cur.get(cur.size() - 1)) {
            cur.set(cur.size() - 2, cur.get(cur.size() - 1) + cur.get(cur.size() - 2));
            cur.remove(size);
        } else {
            while (cur.get(cur.size() - 2) * 2 <= cur.get(cur.size() - 1)) {
                cur.add(cur.get(cur.size() - 1) - cur.get(cur.size() - 2));
                cur.set(cur.size() - 2, cur.get(cur.size() - 3));
            }
        }

    }
}
