package Algorithms.bst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseRMQ {
    private static final int INF = Integer.MAX_VALUE;
    private static int[] tSet;
    private static int[] t;
    private static int left;
    private static int right;
    private static int res;

    private static Scanner reader = new Scanner(System.in);
    private static PrintWriter writer;

    static {
        try {
            reader = new Scanner(new FileInputStream("rmq.in"));
            writer = new PrintWriter("rmq.out");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int n;

    private static void init(int n) {
        t = new int[4 * n];
        tSet = new int[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tSet[i] = INF;
            t[i] = INF;
        }
    }

    private static int query(int v, int vl, int vr, int l, int r) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return Integer.MAX_VALUE;
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        int ql = query(2 * v + 1, vl, vm, l, r);
        int qr = query(2 * v + 2, vm + 1, vr, l, r);
        return Math.min(ql, qr);
    }

    private static void setValue(int v, int vl, int vr, int l, int r, int set) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return;
        }
        if (l <= vl && vr <= r) {
            tSet[v] = (set);
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        setValue((2 * v) + 1, vl, vm, l, r, set);
        setValue(2 * v + 2, vm + 1, vr, l, r, set);
        t[v] = Math.min(t[2 * v + 1], (t[2 * v + 2]));
    }

    private static void push(int v, int vl, int vr) {
        if (tSet[v] != INF) {
            t[v] = tSet[v];
            if (vl != vr) {
                tSet[2 * v + 1] = tSet[v];
                tSet[2 * v + 2] = tSet[v];
            }
            tSet[v] = INF;
        }
    }

    private static void read() {
        left = reader.nextInt() - 1;
        right = reader.nextInt() - 1;
        res = reader.nextInt();
    }

    private static int getQuery(int left, int right) {
        return query(0, 0, n - 1, left, right);
    }

    private static void show() {
        writer.println("consistent");
        for (int i = 0; i < n; i++) {
            writer.print(getQuery(i, i) + " ");
        }
        writer.close();
    }


    public static void main(String[] args) {
        n = reader.nextInt();
        int m = reader.nextInt();
        init(n);
        Triple[] requests = new Triple[m];
        for (int i = 0; i < m; i++) {
            read();
            requests[i] = new Triple(res, left, right);
        }
        Arrays.sort(requests, Triple::compare);
        for (int i = 0; i < m; i++) {
            int l, r, value;
            Triple cur = requests[i];
            value = cur.result;
            l = cur.left;
            r = cur.right;
            setValue(0, 0, n - 1, l, r, value);
        }
        for (int i = 0; i < m; i++) {
            Triple cur = requests[i];
            int value = cur.result;
            int l = cur.left;
            int r = cur.right;
            if (value != getQuery(l, r)) {
                writer.print("inconsistent");
                writer.close();
                return;
            }
        }
        show();
    }

    static class Triple {
        final int result;
        final int left;
        final int right;

        Triple(int result, int left, int right) {
            this.result = result;
            this.left = left;
            this.right = right;
        }

        static int compare(Triple left, Triple right) {
            return Integer.compare(left.result, right.result);
        }
    }
}

