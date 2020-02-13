package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.ArrayList;

public class Part2Num {
    private static long[][] din;
    private static final int DEFAULT_SIZE = 101;
    private static int N = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new
                FileInputStream("part2num.in")));
        PrintWriter writer = new PrintWriter("part2num.out");
        String[] help = reader.readLine().split("[+]");
        ArrayList<Integer> partitions = new ArrayList<>();
        for (String cur : help) {
            partitions.add(Integer.parseInt(cur));
            N += Integer.parseInt(cur);
        }
        makeDin();
        writer.print(getNum(partitions));
        writer.close();
    }

    private static void makeDin() {
        din = new long[DEFAULT_SIZE][DEFAULT_SIZE];
        for (int i = 1; i < DEFAULT_SIZE; i++) {
            for (int j = 1; j < DEFAULT_SIZE; j++) {
                if (i == j) din[i][j] = 1;
                else if (i < j) din[i][j] = 0;
            }
        }
        for (int i = 1; i < DEFAULT_SIZE; i++)
            for (int j = DEFAULT_SIZE - 1; j >= 1; j--) {
                if (i > j) {
                    din[i][j] = 0;
                    din[i][j] += din[i][j + 1];
                    din[i][j] += din[i - j][j];
                }
            }
    }

    private static long getNum(ArrayList<Integer> partitions) {
        int numOfPart = 0, last = 0, sum = 0;
        for (Integer partition : partitions) {
            for (int j = last; j < partition; j++) {
                if (N - sum - j > 0)
                    numOfPart += din[N - sum - j][j];
            }
            sum += partition;
            last = partition;
        }
        return numOfPart;
    }
}