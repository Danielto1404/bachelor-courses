package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Choose {
    public static void main(String[] a) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("choose.in"));
        PrintWriter writer = new PrintWriter("choose.out");
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] chosen = new int[k];
        for (int i = 0; i < k; i++) {
            chosen[i] = i + 1;
            writer.print(i + 1 + " ");
        }
        writer.print("\n");
        while (nextChoose(chosen, k, n) != null) {
            for (int i = 0; i < k; i++) {
                writer.print(chosen[i] + " ");
            }
            writer.print("\n");
        }
        writer.close();
    }

    private static int[] nextChoose(int[] a, int k, int n) {
        int[] b = new int[k + 1];
        for (int i = 0; i < k; i++) {
            b[i] = a[i];
        }
        b[k] = n + 1;
        int i = k - 1;
        while ((i >= 0) && (b[i + 1] - b[i] < 2))
            i--;
        if (i >= 0) {
            b[i]++;
            for (int j = i + 1; j < k; j++) {
                b[j] = b[j - 1] + 1;
            }
            for (i = 0; i < k; i++)
                a[i] = b[i];
            return a;
        }
        return null;
    }
}
