package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.ArrayList;

public class Num2Part {
    private static final int MAX_SIZE = 102;
    private static long[][] d;
    private static int N;
    private static int R;

    public static void main(String[] args) throws IOException {
       /* BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("num2part.in")));

        N = Integer.parseInt(help[0]);
        R = Integer.parseInt(help[1]);*/
        N = 13;
        R = 0;
        PrintWriter writer = new PrintWriter(System.out);
        //  String[] help = reader.readLine().split("\\s");
        ArrayList<Integer> partitions = new ArrayList<>();
        makeDin();
       // System.out.println(d[100][1]);
        findPartition(partitions);
        /*for (int i = 0; i < partitions.size() - 1; i++) {
            writer.print(partitions.get(i) + "+");
        }*/
        System.out.println(d[13][1]);
        writer.print(partitions.get(partitions.size() - 1));
        writer.close();
    }

    private static void findPartition(ArrayList<Integer> partitions) {
        int sum = 0;
        int curNum = 1;
        final int n = N;
        while (sum < n) {
            if (R < (d[N][curNum] - d[N][curNum + 1])) {
                N -= curNum;
                sum += curNum;
                partitions.add(curNum);
            } else {
                R -= (d[N][curNum] - d[N][curNum + 1]);
                curNum++;
            }
        }
    }


    private static void makeDin() {
        d = new long[MAX_SIZE][MAX_SIZE];
        for (int i = 1; i < MAX_SIZE; i++) {
            for (int j = 1; j < MAX_SIZE; j++) {
                if (i == j) d[i][j] = 1;
                else if (i < j) d[i][j] = 0;
            }
        }
        for (int i = 1; i < MAX_SIZE; i++)
            for (int j = MAX_SIZE - 1; j >= 1; j--) {
                if (i > j) {
                    d[i][j] = 0;
                    d[i][j] += d[i][j + 1];
                    d[i][j] += d[i - j][j];
                }
            }
    }
}
