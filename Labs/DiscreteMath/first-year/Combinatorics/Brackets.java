package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Brackets {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("brackets.in"));
        PrintWriter writer = new PrintWriter("brackets.out");
        int n = sc.nextInt();
        ArrayList<String> brackets = new ArrayList<>();
        generateBracket(n, 0, 0, "", brackets);
        long x = System.currentTimeMillis();
        for (String bracketSeq : brackets) {
            writer.println(bracketSeq);
        }
        writer.close();
    }

    private static void generateBracket(int size, int opened, int closed, String prefix, ArrayList<String> brackets) {
        if (prefix.length() == 2 * size) {
            brackets.add(prefix);
        } else {
            if (opened < size) {
                generateBracket(size, opened + 1, closed, prefix + '(', brackets);
            }
            if (opened > closed) {
                generateBracket(size, opened, closed + 1, prefix + ')', brackets);
            }
        }
    }
}
