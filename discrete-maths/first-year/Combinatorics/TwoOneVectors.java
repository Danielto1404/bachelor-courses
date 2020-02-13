package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TwoOneVectors {
    public static int getBit(int number, int shift) {
        return (number >> shift) % 2;
    }

    public static void main(String[] a) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("vectors.in"));
        PrintWriter writer = new PrintWriter("vectors.out");
        final int n = sc.nextInt();
        final int size = (int) Math.pow(2, n);
        String[] codes = new String[size];
        ArrayList<Integer> index = new ArrayList<>();
        boolean check;
        int count = 0;
        for (int i = 0; i < size; i++) {
            StringBuilder curBitLine = new StringBuilder();
            for (int j = 0; j < n; j++) {
                curBitLine.append(getBit(i, n - j - 1));
            }
            codes[i] = curBitLine.toString();
        }
        for (int i = 0; i < size; i++) {
            String[] temp = codes[i].split("0");
            check = true;
            for (String cur : temp) {
                if (!cur.isEmpty() && cur.contains("11")) check = false;
            }
            if (check) {
                count++;
                index.add(i);
            }
        }
        writer.println(count);
        for (Integer anIndex : index) {
            writer.println(codes[anIndex]);
        }
        writer.close();
    }
}
