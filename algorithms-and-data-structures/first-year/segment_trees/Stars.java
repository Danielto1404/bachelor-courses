package Algorithms.bst;

import java.util.Scanner;

public class Stars {
    private static int n;
    private static int x, y, z, x1, y1, z1;
    private static long value;
    private static long[][][] stars;

    private static long countStars(int x, int y, int z) {
        long ans = 0;
        for (int i = x; i >= 0; i = (i & (i + 1)) - 1)
            for (int j = y; j >= 0; j = (j & (j + 1)) - 1)
                for (int k = z; k >= 0; k = (k & (k + 1)) - 1)
                    ans += stars[i][j][k];
        return ans;
    }

    private static long countStarsInCube(int x, int y, int z, int x1, int y1, int z1) {
        long all = countStars(x1, y1, z1);
        long small = countStars(x - 1, y - 1, z - 1);
        long withOneBoarder =
                        countStars(x1, y1, z - 1) +
                        countStars(x1, y - 1, z1) +
                        countStars(x - 1, y1, z1);
        long withTwoBoarder =
                        countStars(x - 1, y - 1, z1) +
                        countStars(x - 1, y1, z - 1) +
                        countStars(x1, y - 1, z - 1);
        return all - small - withOneBoarder + withTwoBoarder;
    }

    private static void addStars(int x, int y, int z, long value) {
        for (int i = x; i < n; i = i | (i + 1))
            for (int j = y; j < n; j = j | (j + 1))
                for (int k = z; k < n; k = k | (k + 1))
                    stars[i][j][k] += value;
    }

    private static void setBoardersAndValues(boolean change, Scanner scanner) {
        x = scanner.nextInt();
        y = scanner.nextInt();
        z = scanner.nextInt();
        if (change) {
            value = scanner.nextLong();
        } else {
            x1 = scanner.nextInt();
            y1 = scanner.nextInt();
            z1 = scanner.nextInt();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        stars = new long[n][n][n];
        while (true) {
            switch (scanner.nextInt()) {
                case 1:
                    setBoardersAndValues(true, scanner);
                    addStars(x, y, z, value);
                    break;
                case 2:
                    setBoardersAndValues(false, scanner);
                    System.out.println(countStarsInCube(x, y, z, x1, y1, z1));
                    break;
                case 3:
                    return;
            }
        }
    }
}
