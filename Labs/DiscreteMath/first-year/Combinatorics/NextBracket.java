package Discrete_Mathematics.Combinatorics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NextBracket {
    public static void main(String[] a) throws IOException {
        Scanner sc = new Scanner(new FileInputStream("nextbrackets.in"));
        PrintWriter writer = new PrintWriter("nextbrackets.out");
        String curLine = sc.nextLine();
        writer.print(nextBracket(curLine));
        writer.close();
    }

    private static String nextBracket(String currentBracketSequence) {
        int counterClose = 0;
        int counterOpen = 0;
        for (int i = currentBracketSequence.length() - 1; i >= 0; i--) {
            if (currentBracketSequence.charAt(i) == '(') {
                counterOpen++;
                if (counterClose > counterOpen)
                    break;
            } else
                counterClose++;
        }
        StringBuilder nowSeq = new StringBuilder(currentBracketSequence);
        nowSeq.delete(currentBracketSequence.length() - counterClose - counterOpen, currentBracketSequence.length());
        String to = nowSeq.toString();
        if (to.equals("")) {
            return "-";
        } else {
            nowSeq.append(')');
        }
        for (int j = 1; j <= counterOpen; j++)
            nowSeq.append('(');
        for (int j = 1; j <= counterClose - 1; j++)
            nowSeq.append(')');
        return nowSeq.toString();
    }
}