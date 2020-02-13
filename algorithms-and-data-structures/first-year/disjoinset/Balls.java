package Algorithms.disjoinset;

import java.util.Scanner;

public class Balls {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] colors = sc.nextLine().split("\\s");
        int n = Integer.parseInt(colors[0]);
        StringBuilder line = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            line.append(colors[i]);
        }
        int iterator = 0;
        int countDeleted = 0;
        try {
            while (line.length() > 2) {
                if (line.charAt(iterator) == line.charAt(iterator + 1)
                        && line.charAt(iterator + 1) == line.charAt(iterator + 2)) {
                    int index = iterator + 2;
                    countDeleted += 3;
                    while (line.charAt(index) == line.charAt(index + 1)) {
                        index++;
                        countDeleted++;
                    }
                    line.delete(iterator, index + 1);
                    iterator -= 2;
                } else {
                    iterator++;
                }
            }
        } catch (Exception e) {
        }
        System.out.println(countDeleted);
    }
}