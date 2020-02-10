package Algorithms.bst;

import java.util.Scanner;

public class SparseTable {
    private static int[] logTable;
    private static int[][] table;
    private static int n;
    private static int m;
    private static int left;
    private static int right;
    private static int res;

    private static void init(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        int a1 = scanner.nextInt();
        left = scanner.nextInt();
        right = scanner.nextInt();
        setTables(a1);
    }

    private static void setTables(int a1) {

        // Filling log_table
        logTable = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            logTable[i] = logTable[i / 2] + 1;
        }
        table = new int[logTable[logTable.length - 1] + 1][n + 1];

        // Filling SparseTable
        table[0][0] = a1;
        for (int i = 1; i < n; i++) {
            table[0][i] = (23 * table[0][i - 1] + 21563) % 16714589;
        }
        for (int line = 1; line < table.length; line++) {
            for (int i = 0; i + (1 << line) <= table[0].length; i++) {
                table[line][i] = Math.min(table[line - 1][i], table[line - 1][i + (1 << (line - 1))]);
            }
        }
    }

    private static void set(int i) {
        left = ((17 * left + 751 + res + 2 * i) % n) + 1;
        right = ((13 * right + 593 + res + 5 * i) % n) + 1;
    }

    public static void main(String[] args) {
        init(new Scanner(System.in));
        int l = Math.min(left, right) - 1;
        int r = Math.max(left, right);
        int log = logTable[r - l];
        res = Math.min(table[log][l], table[log][r - (1 << log)]);
        for (int i = 2; i <= m; i++) {
            set(i - 1);
            l = Math.min(left, right) - 1;
            r = Math.max(left, right);
            log = logTable[r - l];
            res = Math.min(table[log][l], table[log][r - (1 << log)]);
        }
        System.out.println(left + " " + right + " " + res);
    }
}
