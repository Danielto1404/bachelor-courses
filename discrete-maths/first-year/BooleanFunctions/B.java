package Discrete_Mathematics.BooleanFunctions;

import java.io.*;

public class B {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] values = reader.readLine().split("\\s");
        int n = Integer.parseInt(values[0]);
        int k = Integer.parseInt(values[1]);
        int[][] matrix = new int[1000][1000];
        for (int i = 0; i < k; i++) {
            values = reader.readLine().split("\\s");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(values[j]);
            }
        }
        for (int y = 0; y < k; y++) {
            for (int j = 0; j < k; j++) {
                int countFirst = 0, countSecond, in = 0, jn = 0, t = 0;
                for (int i = 0; i < n; i++) {
                    if (matrix[j][i] != -1) {
                        countFirst++;
                        t = matrix[j][i];
                        in = i;
                    }
                }
                if (countFirst == 1 && t == 1) {
                    for (jn = 0; jn < k; jn++) {
                        countSecond = 0;
                        if (matrix[jn][in] == 1) {
                            for (int l = 0; l < n; l++) {
                                matrix[jn][l] = -1;
                            }
                        }
                        if (matrix[jn][in] == 0) {
                            for (int i = 0; i < n; i++) {
                                if (matrix[jn][i] != -1) {
                                    countSecond++;
                                }
                            }
                            if (countSecond == 1) {
                                matrix[jn][in] = -Integer.MAX_VALUE;
                            } else {
                                matrix[jn][in] = -1;
                            }
                        }
                    }
                }
                if (countFirst == 1 && t == 0) {
                    for (jn = 0; jn < k; jn++) {
                        countSecond = 0;
                        if (matrix[jn][in] == 0) {
                            for (int r = 0; r < n; r++) {
                                matrix[jn][r] = -1;
                            }
                        }
                        if (matrix[jn][in] == 1) {
                            for (int i = 0; i < n; i++) {
                                if (matrix[jn][i] != -1) {
                                    countSecond++;
                                }
                            }
                            if (countSecond == 1) {
                                matrix[jn][in] = -Integer.MAX_VALUE;
                            } else {
                                matrix[jn][in] = -1;
                            }
                        }
                    }
                }
            }
        }
        int count = 0;
        for (int j = 0; j < k; j++) {
            for (int i = 0; i < n; i++) {
                if (matrix[j][i] == -Integer.MAX_VALUE) count++;
            }
        }
        if (count > 0) System.out.println("YES");
        else System.out.println("NO");
    }
}
