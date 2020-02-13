package Discrete_Mathematics.Encoding;

import java.io.*;
import java.util.*;

public class 4___LZW {
    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("lzw.in");
        Scanner sc = new Scanner(in);
        String line = sc.nextLine();
        int size = line.length();
        ArrayList<String> dictionary = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            char curLet = (char) ('a' + i);
            dictionary.add((curLet + ""));
        }
        StringBuilder index = new StringBuilder();
        StringBuilder curStream = new StringBuilder(line.charAt(0));
        for (int i = 0; i < size; i++) {
            StringBuilder tmp = new StringBuilder(curStream);
            tmp.append(line.charAt(i));
            if (i == size - 1) {
                if (dictionary.contains(tmp.toString())) index.append(dictionary.indexOf(tmp.toString()));
                else {
                    index.append(dictionary.indexOf(curStream.toString())).append(" ").
                            append(dictionary.indexOf(line.charAt(i) + ""));
                }
            } else {
                if (dictionary.contains(tmp.toString())) {
                    curStream = new StringBuilder(tmp);
                } else {
                    dictionary.add(tmp.toString());
                    index.append(dictionary.indexOf(curStream.toString()) + " ");
                    curStream = new StringBuilder(line.charAt(i) + "");
                }
            }
        }
        PrintWriter writer = new PrintWriter("lzw.out");
        writer.print(index);
        writer.close();
    }
}



