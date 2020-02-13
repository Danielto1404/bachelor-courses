package Algorithms.disjoinset;

import java.util.Scanner;

public class NonJoinedSets {
    private static int[] parents;
    private static int[] minValues;
    private static int[] maxValues;
    private static int[] rank;
    private static int[] numbers;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int n = Integer.parseInt(sc.nextLine());
            makeSet(n);
            String curString;
            while (sc.hasNextLine()) {
                curString = sc.nextLine();
                if (curString.equals("")) return;
                String[] commands = curString.split("\\s");
                if (commands[0].equals("union")) {
                    int a = Integer.parseInt(commands[1]);
                    int b = Integer.parseInt(commands[2]);
                    union(a, b);
                } else if (commands[0].equals("get")) {
                    int a = Integer.parseInt(commands[1]);
                    System.out.println(values(a));
                }
            }
        } catch (Exception ex) {
        }
    }

    private static String values(int x) {
        int index = getID(x);
        return minValues[index] + " " + maxValues[index] + " " + numbers[index];
    }

    private static void union(int a, int b) {
        a = getID(a);
        b = getID(b);
        if (a == b) return;
        if (rank[a] < rank[b]) {
            minValues[b] = Math.min(minValues[a], minValues[b]);
            maxValues[b] = Math.max(maxValues[a], maxValues[b]);
            numbers[b] += numbers[a];
            parents[a] = b;
        } else {
            if (rank[a] == rank[b]) {
                rank[a]++;
            }
            minValues[a] = Math.min(minValues[a], minValues[b]);
            maxValues[a] = Math.max(maxValues[a], maxValues[b]);
            numbers[a] += numbers[b];
            parents[b] = a;
        }
    }

    private static int getID(int x) {
        if (parents[x] == x) {
            return x;
        }
        return parents[x] = getID(parents[x]);
    }

    private static void makeSet(int n) {
        int maxSize = n + 1;
        parents = new int[maxSize];
        minValues = new int[maxSize];
        maxValues = new int[maxSize];
        rank = new int[maxSize];
        numbers = new int[maxSize];
        for (int i = 1; i <= n; i++) {
            parents[i] = i;
            maxValues[i] = i;
            minValues[i] = i;
            numbers[i] = 1;
            rank[i] = 1;
        }
    }
}
