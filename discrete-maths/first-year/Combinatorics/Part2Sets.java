package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.ArrayList;

public class Part2Sets {
    protected static int k;
    private static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("part2sets.out");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void generate(ArrayList<String> result, int k, int number, int max, int offset) {
        if (number == max) {
            for (int i = 0; i < k; i++) {
                if (!result.get(i).isEmpty()) {
                    return;
                }
            }
            for (int i = 0; i < k; i++) {
                writer.printf("%s\n", result.get(i));
            }
            writer.print("\n");
            return;
        }
        for (int i = 0; i < Math.min(number - offset, k); i++) {
            String s = result.get(i);
            result.set(i, result.get(i) + (number) + " ");
            generate(result, k, number + 1, max, offset + (i + 1 == (number - offset) ? 0 : 1));
            result.set(i, s);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new
                FileInputStream("/Users/daniilkorolev/Documents/JavaWorkingSpace/src/test.txt")));
        String[] help = reader.readLine().split("\\s");
        int n = Integer.parseInt(help[0]);
        k = Integer.parseInt(help[1]);
        ArrayList<String> sets = new ArrayList<>(k);
        generate(sets, k, 1, n + 1, 0);
        writer.close();
    }
}
