package Discrete_Mathematics.BooleanFunctions;

import java.util.ArrayList;
import java.util.Scanner;

public class F {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int size = (int) Math.pow(2, n);
        String[] table = new String[size];
        ArrayList<Integer> coefficient = new ArrayList<>();
        int firstIndex = 0;
        for (int k = 0; k < size; k++) {
            table[k] = sc.next();
            coefficient.add(sc.nextInt());
        }
        while (firstIndex < size - 1) {
            for (int i = firstIndex; i < size - 1; i++) {
                int cur;
                cur = coefficient.get(i) ^ coefficient.get(i + 1);
                coefficient.add(cur);
            }
            firstIndex++;
            int toRemove = size - 1;
            while (toRemove >= firstIndex) {
                coefficient.remove(firstIndex);
                toRemove--;
            }
        }
        for (int i = 0; i < size; i++) {
            System.out.println(table[i] + " " + coefficient.get(i));
        }
    }
}


