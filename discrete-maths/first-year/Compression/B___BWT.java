package Discrete_Mathematics.Encoding;

import java.io.*;
import java.util.*;

public class 2___BWT {
    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("bwt.in");
        Scanner sc = new Scanner(in);
        String line = sc.nextLine();
        int size = line.length();
        String[] shifts = new String[size];
        shifts[0] = line;
        for (int i = 1; i < size; i++) {
            shifts[i] = cycleShift(shifts[i - 1]);
        }
        Arrays.sort(shifts);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < size; i++) {
            out.append(shifts[i].charAt(size - 1));
        }
        PrintWriter writer = new PrintWriter("bwt.out");
        writer.println(out);
        writer.close();
    }

    private static String cycleShift(String line) {
        StringBuilder cur1 = new StringBuilder(line.substring(0, 1));
        StringBuilder cur2 = new StringBuilder(line.substring(1));
        cur1.reverse();
        cur2.reverse();
        StringBuilder our = cur1.append(cur2);
        return our.reverse().toString();
    }
}

