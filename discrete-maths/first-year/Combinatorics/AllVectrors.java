package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.Scanner;

public class AllVectrors {
    public static int getBit(int number, int shift) {
        return (number >> shift) % 2;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("allvectors.in"));
        int n = sc.nextInt();
        PrintWriter writer = new PrintWriter("allvectors.out");
        int size = (int) Math.pow(2, n);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < n; j++) {
                writer.print(getBit(i, n - j - 1));
            }
            writer.println();
        }
        writer.close();
    }
}
