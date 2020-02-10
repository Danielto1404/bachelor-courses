package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AntiGray {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream("antigray.in"));
        int n = scanner.nextInt();
        PrintWriter writer = new PrintWriter("antigray.out");
        int size = (int) Math.pow(3, n - 1);
        Map<String, Integer> codes = new TreeMap<>();
        String[] threeCodes = new String[size];
        generateCodes(codes, n, 0, "0");
        copyN(codes, n, threeCodes);
        for (String cur1 : threeCodes) {
            String cur2 = sum3(cur1);
            String cur3 = sum3(cur2);
            writer.println(cur1 + "\n" + cur2 + "\n" + cur3);
        }
        writer.close();
    }

    private static String sum3(String cur) {
        int[] curBits = new int[cur.length()];
        for (int i = 0; i < curBits.length; i++) {
            curBits[i] = (Integer.parseInt(cur.charAt(i) + "") + 1) % 3;
        }
        StringBuilder curVector = new StringBuilder();
        for (int bits : curBits) {
            curVector.append(bits);
        }
        return curVector.toString();
    }

    private static void generateCodes(Map<String, Integer> a, int maxSize, int curSize, String curCode) {
        if (maxSize > curSize) {
            if (a.containsKey(curCode)) {
                a.put(curCode + "0", 0);
                a.put(curCode + "1", 0);
                a.put(curCode + "2", 0);
            } else {
                a.put(curCode, 0);
            }
            generateCodes(a, maxSize, curSize + 1, curCode + "0");
            generateCodes(a, maxSize, curSize + 1, curCode + "1");
            generateCodes(a, maxSize, curSize + 1, curCode + "2");
        }
    }

    private static void copyN(Map<String, Integer> a, int n, String[] copied) {
        int k = 0;
        for (String cur : a.keySet()) {
            if (cur.length() == n) {
                copied[k] = cur;
                k++;
            }
        }
    }
}
