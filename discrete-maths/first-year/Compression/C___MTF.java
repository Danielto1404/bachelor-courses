package Discrete_Mathematics.Encoding;

import java.io.*;
import java.util.Scanner;

public class 3___MTF {
    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("mtf.in");
        Scanner sc = new Scanner(in);
        String line = sc.nextLine();
        int index = 0;
        StringBuilder builder = new StringBuilder();
        while (index < 26) {
            builder.append((char) ('a' + index));
            index++;
        }
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            String curLetter = line.substring(i, i + 1);
            index = builder.indexOf(curLetter);
            index++;
            sequence.append(index + " ");
            String substringOfAlphabet = builder.toString().substring(0, index);
            builder.delete(0, index);
            builder.insert(0, cycleShift(substringOfAlphabet, index - 1));
        }
        sequence.delete(sequence.length() - 1, sequence.length());
        PrintWriter writer = new PrintWriter("mtf.out");
        writer.println(sequence);
        writer.close();
    }

    public static String cycleShift(String line, int shift) {
        StringBuilder cur1 = new StringBuilder(line.substring(0, shift));
        StringBuilder cur2 = new StringBuilder(line.substring(shift));
        cur1.reverse();
        cur2.reverse();
        StringBuilder our = cur1.append(cur2);
        return our.reverse().toString();
    }
}


