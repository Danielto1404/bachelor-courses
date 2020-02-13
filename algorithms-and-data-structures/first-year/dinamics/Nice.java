package Algorithms.dinamics;

import java.io.*;

public class Nice {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("nice.in")));
        File out = new File("nice.out");
        PrintWriter writer = new PrintWriter(out, "UTF8");
        String[] numbers = in.readLine().split("\\s");
        in.close();
        int n = Integer.parseInt(numbers[0]);
        int m = Integer.parseInt(numbers[1]);
        int min = Math.min(n, m);
        int max = Math.max(n, m);
        int one = 1;
        for (int i = 1; i <= min; i++) {
            one <<= 1;
        }
        int a[][] = new int[max + 1][one + 1];
        for (int i = 0; i < one; i++) {
            a[1][i] = 1;
        }
        for (int i = 2; i <= max; i++) {
            for (int j = 0; j < one; j++) {
                for (int k = 0; k < one; k++) {
                    if (checkSquare(j, k, min)) {
                        a[i][k] = a[i - 1][j] + a[i][k];
                    }
                }
            }
        }
        int result = 0;
        for (int i = 0; i < one; i++) {
            result += a[max][i];
        }
        writer.println(result);
        writer.close();
    }

    private static boolean checkSquare(int x, int y, int m) {
        int second[] = new int[m + 1];
        int first[] = new int[m + 1];
        for (int i = 1; i < m + 1; i++) {
            first[i] = x & 1;
            second[i] = y & 1;
            x >>= 1;
            y >>= 1;
            if ((i > 1) && (first[i] == first[i - 1]) && (first[i] == second[i]) && (first[i] == second[i - 1])) {
                return false;
            }
        }
        return true;
    }
}
