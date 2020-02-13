package Discrete_Mathematics.Combinatorics;

import java.io.*;
import java.util.ArrayList;

public class Telemetry {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("telemetry.in")));
        PrintWriter writer = new PrintWriter("telemetry.out");
        String[] num = reader.readLine().split("\\s");
        int n = Integer.parseInt(num[0]);
        int k = Integer.parseInt(num[1]);
        ArrayList<String> codes = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            codes.add(i + "");
        }
        int curSize = k;
        final int size = (int) Math.pow(k, n);
        int mirror;
        while (curSize < size) {
            mirror = 0;
            while (mirror < k - 1) {
                for (int i = 0; i < curSize; i++) {
                    if (mirror % 2 == 0) {
                        codes.add(codes.get(curSize - i - 1) + (mirror + 1));
                        if (mirror == k - 2) {
                            codes.set(curSize - i - 1, codes.get(curSize - i - 1) + 0);
                        }
                    } else {
                        codes.add(codes.get(i) + (mirror + 1));
                        if (mirror == k - 2) {
                            codes.set(i, codes.get(i) + 0);
                        }
                    }

                }
                mirror++;
            }
            curSize *= k;
        }
        for (String code : codes) {
            writer.println(code);
        }
        writer.close();

    }
}
