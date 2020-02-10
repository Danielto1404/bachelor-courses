package Algorithms.disjoinset;

import java.util.Scanner;

public class Clans {
    private static int[] parent;
    private static int[] rank;
    private static int[] before;
    private static int[] values;

    private static void makeSet(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];
        before = new int[n + 1];
        values = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            before[i] = 0;
            values[i] = 0;
            rank[i] = 1;
        }
    }

    private static int findID(int x) {
        while (x != parent[x]) {
            x = parent[x];
        }
        return x;
    }

    private static void join(int x, int y) {
        int xID = findID(x);
        int yID = findID(y);
        if (xID != yID) {
            if (rank[yID] < rank[xID]) {
                parent[yID] = xID;
                before[yID] = values[xID];
            } else {
                if (rank[xID] == rank[yID]) {
                    rank[yID]++;
                }
                parent[xID] = yID;
                before[xID] = values[yID];
            }
        }
    }

    private static int getValue(int x) {
        int value = 0;
        value += values[x];
        while (x != parent[x]) {
            value += (values[parent[x]] - before[x]);
            x = parent[x];
        }
        return value;
    }

    private static void add(int element, int value) {
        int elementID = findID(element);
        values[elementID] += value;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        makeSet(n);
        for (int i = 0; i < m; i++) {
            String operation = sc.next();
            switch (operation) {
                case "add":
                    add(sc.nextInt(), sc.nextInt());
                    break;
                case "join":
                    join(sc.nextInt(), sc.nextInt());
                    break;
                case "get":
                    int a = sc.nextInt();
                    System.out.println(getValue(a));
            }
        }
    }
}