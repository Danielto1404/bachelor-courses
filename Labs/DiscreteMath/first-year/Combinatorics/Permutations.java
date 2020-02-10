package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Permutations {
    public static void main(String[] a) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("permutations.in"));
        PrintWriter writer = new PrintWriter("permutations.out");
        int n = sc.nextInt();
        int[] definedArray = new int[n];
        for (int i = 0; i < n; i++) {
            definedArray[i] = i + 1;
            writer.print(i + 1 + " ");
        }
        writer.print("\n");
        while (nextPermutation(definedArray, n)) {
            for (int i : definedArray) {
                writer.print(i + " ");
            }
            writer.print("\n");
        }
        writer.close();
    }

    private static boolean nextPermutation(int[] a, int n) {
        int check = n - 2;
        while (check != -1 && a[check] >= a[check + 1]) {
            check--;
        }
        if (check == -1)
            return false;
        int k = n - 1;
        while (a[check] >= a[k]) k--;
        swap(a, check, k);
        int left = check + 1, rigth = n - 1;
        while (left < rigth)
            swap(a, left++, rigth--);
        return true;
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
}