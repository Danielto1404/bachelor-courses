package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeMap;

public class ChainCode {
    private final static int INITIAL = -1;

    public static void main(String[] a) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("chaincode.in"));
        PrintWriter writer = new PrintWriter("chaincode.out");
        int n = sc.nextInt();
        TreeMap<String, Integer> codes = new TreeMap<>();
        StringBuilder tmpZero = new StringBuilder();
        for (int i = 0; i < n; i++) {
            tmpZero.append("0");
        }
        String cur = tmpZero.toString();
        while ((cur = generateChain(n, cur, codes)) != null) {
            writer.println(cur);
        }
        writer.close();
    }

    private static String generateChain(int n, String curCode, TreeMap<String, Integer> map) {
        String currentCode = shift(curCode).substring(0, n - 1);
        String ZERO = currentCode + "0";
        String ONE = currentCode + "1";
        if (!map.containsKey(ONE)) {
            map.put(ONE, INITIAL);
            return ONE;
        }
        if (!map.containsKey(ZERO)) {
            map.put(ZERO, INITIAL);
            return ZERO;
        } else {
            return null;
        }
    }

    private static String shift(String line) {
        StringBuilder cur1 = new StringBuilder(line.substring(0, 1));
        StringBuilder cur2 = new StringBuilder(line.substring(1));
        cur1.reverse();
        cur2.reverse();
        StringBuilder our = cur1.append(cur2);
        return our.reverse().toString();
    }
}

