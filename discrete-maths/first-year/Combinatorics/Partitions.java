package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Partitions {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("partition.in"));
        PrintWriter writer = new PrintWriter("partition.out");
        ArrayList<Integer> part = new ArrayList<>();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            part.add(1);
        }
        writer.print("1");
        for (int i = 1; i < part.size(); i++) {
            writer.print("+1");
        }
        writer.print("\n");
        ArrayList<Integer> tmp = part;
        while ((tmp = partitions(tmp, n)) != null) {
            for (int i = 0; i < tmp.size(); i++) {
                if (i != 0) writer.print("+" + tmp.get(i));
                else writer.print(tmp.get(i));
            }
            writer.print("\n");
        }
        writer.close();
    }

    private static ArrayList<Integer> partitions(ArrayList<Integer> cur, int n) {
        if (cur.get(0) == n) {
            return null;
        } else {
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
            return cur;
        }
    }
}
