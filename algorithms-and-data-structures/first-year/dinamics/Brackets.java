package Algorithms.dinamics;

import java.util.Scanner;

public class Brackets {
    private static int n;
    private static String brackets;
    private static int[][] sub;
    private static int[][] path;
    private static final int inf = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        brackets = sc.nextLine();
        n = brackets.length();
        if (!brackets.equals("")) {
            makeSub();
            findPath(0, n - 1);
        }
    }

    private static boolean check(int l, int r) {
        return brackets.charAt(l) == '(' && brackets.charAt(r) == ')'
                ||
                brackets.charAt(l) == '[' && brackets.charAt(r) == ']'
                ||
                brackets.charAt(l) == '{' && brackets.charAt(r) == '}';
    }

    private static void makeSub() {
        sub = new int[n][n];
        path = new int[n][n];
        for (int right = 0; right < n; right++) {
            for (int left = right; left >= 0; left--) {
                if (left == right) {
                    sub[left][right] = 1;
                } else {
                    int bestCost = inf;
                    int curPath = -1;
                    if (check(left, right)) {
                        bestCost = sub[left + 1][right - 1];
                    }
                    for (int k = left; k < right; k++) {
                        if (sub[left][k] + sub[k + 1][right] < bestCost) {
                            bestCost = sub[left][k] + sub[k + 1][right];
                            curPath = k;
                        }
                    }
                    sub[left][right] = bestCost;
                    path[left][right] = curPath;
                }
            }
        }
    }

    private static void findPath(int l, int r) {
        if (sub[l][r] == r - l + 1) {
            return;
        }
        if (sub[l][r] == 0) {
            for (int i = l; i <= r; i++) {
                System.out.print(brackets.charAt(i));
            }
            return;
        }
        if (path[l][r] == -1) {
            System.out.print(brackets.charAt(l));
            findPath(l + 1, r - 1);
            System.out.print(brackets.charAt(r));
            return;
        }
        findPath(l, path[l][r]);
        findPath(path[l][r] + 1, r);
    }
}

