package Algorithms.dinamics;

import java.io.*;

public class SkyScraper {
    private static PrintWriter writer;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("skyscraper.in"));
        String[] numbers = in.readLine().split("\\s");
        int N = Integer.parseInt(numbers[0]);
        int limit = Integer.parseInt(numbers[1]);
        int[] firstCost = new int[N];
        int[] isDefined = new int[N];
        for (int i = 0; i < N; i++) {
            numbers = in.readLine().split("\\s");
            firstCost[N - 1 - i] = Integer.parseInt(numbers[0]);
            isDefined[N - 1 - i] = i + 1;
        }
        in.close();
        writer = new PrintWriter(new BufferedWriter(new FileWriter("skyscraper.out")));
        nextTrip(N, true, firstCost, limit, isDefined, 0);
        writer.close();
    }

    private static void nextTrip(int cowCounter, boolean check, int[] countFinalCost, int limit,
                                 int[] isDefined, int trying) {
        if (cowCounter == 0) {
            return;
        }
        int[] sums = new int[1 << cowCounter];
        for (int i = 0; i < (1 << cowCounter); i++) {
            for (int j = 0; j < cowCounter; j++) {
                if ((i & (1 << j)) != 0) {
                    sums[i] += countFinalCost[j];
                }
            }
        }
        int[] prev = new int[1 << cowCounter];
        int allSums = (1 << cowCounter) - 1;
        int counter = 1;
        while (true) {
            if (counter > 1) {
                for (int i = 0; i < (1 << cowCounter); i++) {
                    for (int j = 0; j < cowCounter; j++) {
                        if ((i & (1 << j)) == 0) {
                            prev[i | (1 << j)] = Math.max(prev[i | (1 << j)], prev[i]);
                        }
                    }
                }
            }
            if (sums[allSums] - prev[allSums] <= limit) {
                if (check && trying == 0) {
                    writer.println(counter);
                    trying++;
                }
                for (int I = 0; I < (1 << cowCounter); I++) {
                    if (sums[I] == prev[I] && sums[allSums] - prev[I] <= limit) {
                        int cT = allSums & (~I);
                        writer.print(Integer.bitCount(cT));
                        for (int i = cowCounter - 1; i >= 0; i--) {
                            if ((cT & (1 << i)) != 0) {
                                writer.print(" " + isDefined[i]);
                            }
                        }
                        writer.println();
                        int tmp = 0;
                        for (int i = 0; i < cowCounter; i++) {
                            if ((I & (1 << i)) != 0) {
                                isDefined[tmp] = isDefined[i];
                                countFinalCost[tmp] = countFinalCost[i];
                                tmp++;
                            }
                        }
                        nextTrip(tmp, true, countFinalCost, limit, isDefined, trying);
                        return;
                    }
                }
            }
            for (int i = 0; i < (1 << cowCounter); i++) {
                prev[i] = sums[i] - prev[i] <= limit ? sums[i] : 0;
            }
            counter++;
        }
    }

}