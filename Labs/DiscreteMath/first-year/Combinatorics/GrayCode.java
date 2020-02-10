package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GrayCode {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream("gray.in"));
        PrintWriter writer = new PrintWriter("gray.out");
        int n = scanner.nextInt();
        int maxSize = (int) Math.pow(2, n);
        String[] codes = new String[maxSize];
        String ONE = "1";
        String ZERO = "0";
        codes[0] = ZERO;
        codes[1] = ONE;
        int curSize = 2;
        if (maxSize == 2) {
            writer.println(ZERO + '\n' + ONE);
            writer.close();
        } else {
            while (curSize < maxSize) {
                for (int i = curSize - 1; i >= 0; i--) {
                    codes[curSize + curSize - i - 1] = ONE + codes[i];
                    codes[i] = ZERO + codes[i];
                }
                curSize *= 2;
            }
            for (String cur : codes) {
                writer.println(cur);
            }
            writer.close();
        }
    }
}
