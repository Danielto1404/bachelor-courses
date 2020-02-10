package Algorithms.dinamics;

import java.util.Scanner;

public class HorsePhone {
    private static long[][] ends;
    private static int n;
    private static final int mod = (int) Math.pow(10, 9);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        ends = new long[10][n + 1];
        makePhones();
        System.out.println(find());
    }

    private static void makePhones() {
        for (int i = 0; i <= 9; i++) {
            if (i != 0 && i != 8) {
                ends[i][1] = 1;
            }
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int curEnd : getVar(j)) {
                    if (curEnd != -1) {
                        ends[j][i] = (ends[j][i] + ends[curEnd][i - 1]) % mod;
                    } else {
                        ends[5][i] = 0;
                    }
                }
            }
        }
    }


    private static long find() {
        long s = 0;
        for (int i = 0; i <= 9; i++) {
            s = (s + ends[i][n]) % mod;
        }
        return s;
    }

    private static int[] getVar(int i) {
        switch (i) {
            case 0:
                return new int[]{4, 6};
            case 1:
                return new int[]{6, 8};
            case 2:
                return new int[]{7, 9};
            case 3:
                return new int[]{4, 8};
            case 4:
                return new int[]{0, 3, 9};
            case 5:
                return new int[]{-1};
            case 6:
                return new int[]{0, 1, 7};
            case 7:
                return new int[]{2, 6};
            case 8:
                return new int[]{1, 3};
            case 9:
                return new int[]{2, 4};
        }
        return new int[1];
    }
}
