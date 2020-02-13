package Discrete_Mathematics.BooleanFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class C {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int maxSize = (int) Math.pow(2, 5);
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            matrix.add(new ArrayList<Integer>());
            for (int j = 0; j < 5; j++) {
                matrix.get(i).add(getBit(i, 5 - j - 1));
            }
        }
        int k = Integer.parseInt(sc.next());
        boolean[] isOk = new boolean[5];
        Arrays.fill(isOk, false);
        for (int i = 0; i < k; i++) {
            int curNumbers = Integer.parseInt(sc.next());
            String truth = sc.next();
            int curBits[] = new int[(int) Math.pow(2, curNumbers)];
            for (int j = 0; j < curBits.length; j++) {
                curBits[j] = Integer.parseInt(truth.substring(j, j + 1));
            }
            isOk[0] = isOk[0] || nonConserveZero(curBits);
            isOk[1] = isOk[1] || nonConserveOne(curBits);
            isOk[2] = isOk[2] || !isLinear(curBits);
            isOk[3] = isOk[3] || !SD(curBits);
            isOk[4] = isOk[4] || !MONO(curBits, matrix);
        }
        for (int i = 0; i < 5; i++) {
            if (!isOk[i]) {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
    }

   public static int getBit(int number, int shift) {
        return (number >> shift) % 2;
    }

    public static boolean nonConserveZero(int[] values) {
        return values[0] == 1;
    }

    public static boolean nonConserveOne(int[] values) {
        return values[values.length - 1] == 0;
    }

    public static boolean isLinear(int[] values) {
        int[] coefficient = makePoly(values);
        int k = 1;
        for (int i = 1; i < coefficient.length; i++) {
            if (i == k) {
                k *= 2;
            } else if (coefficient[i] == 1) return false;
        }
        return true;
    }

    public static int[] makePoly(int[] values) {
        ArrayList<Integer> coefficient = new ArrayList<>();
        int curSize = values.length;
        int firstIndex = 0;
        for (int i = 0; i < curSize; i++) {
            coefficient.add(values[i]);
        }
        while (firstIndex < curSize - 1) {
            for (int i = firstIndex; i < curSize - 1; i++) {
                int cur;
                cur = coefficient.get(i) ^ coefficient.get(i + 1);
                coefficient.add(cur);
            }
            firstIndex++;
            int toRemove = values.length - 1;
            while (toRemove >= firstIndex) {
                coefficient.remove(firstIndex);
                toRemove--;
            }
        }
        int[] b = new int[values.length];
        for (int i = 0; i < coefficient.size(); i++) {
            b[i] = coefficient.get(i);
        }
        return b;
    }

    public static boolean SD(int[] values) {
        int size = values.length;
        if (size == 1) return false;
        for (int i = 0; i < size / 2; i++) {
            if (values[i] == values[size - i - 1]) return false;
        }
        return true;
    }

    public static boolean dominate(ArrayList<Integer> firstLine, ArrayList<Integer> secondLine) {
        for (int i = 0; i < firstLine.size(); i++) {
            if (firstLine.get(i) > secondLine.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean MONO(int[] values, ArrayList<ArrayList<Integer>> table) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0) {
                for (int j = i + 1; j < values.length; j++) {
                    if (dominate(table.get(i), table.get(j)) && values[i] > values[j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
