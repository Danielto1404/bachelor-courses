package Algorithms.bst;

import java.util.Arrays;
import java.util.Scanner;

public class RMQ2 {

    private static long[] t;
    private static long[] tAdd;
    private static long[] tSet;
    private final static long INF = Long.MAX_VALUE;

    private static void build(int v, int vl, int vr, long[] a) {
        if (vl == vr) {
            t[v] = a[vl];
            return;
        }
        int vm = vl + (vr - vl) / 2;
        build((2 * v) + 1, vl, vm, a);
        build(2 * v + 2, vm + 1, vr, a);
        t[v] = Math.min(t[(2 * v) + 1], t[2 * v + 2]);
    }

    private static long query(int v, int vl, int vr, int l, int r) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return Long.MAX_VALUE;
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        long ql = query(2 * v + 1, vl, vm, l, r);
        long qr = query(2 * v + 2, vm + 1, vr, l, r);
        return Math.min(ql, qr);
    }

    private static void setValue(int v, int vl, int vr, int l, int r, long set) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return;
        }
        if (l <= vl && vr <= r) {
            tSet[v] = set;
            tAdd[v] = INF;
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        setValue(2 * v + 1, vl, vm, l, r, set);
        setValue(2 * v + 2, vm + 1, vr, l, r, set);
        t[v] = Math.min(t[2 * v + 1], t[2 * v + 2]);
    }

    private static void addValue(int v, int vl, int vr, int l, int r, long add) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return;
        }
        if (l <= vl && vr <= r) {
            tAdd[v] = add;
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        addValue(2 * v + 1, vl, vm, l, r, add);
        addValue(2 * v + 2, vm + 1, vr, l, r, add);
        t[v] = Math.min(t[2 * v + 1], t[2 * v + 2]);
    }

    private static void push(int v, int vl, int vr) {
        if (tSet[v] != INF) {
            t[v] = tSet[v];
            if (vl != vr) {
                tSet[2 * v + 1] = tSet[v];
                tSet[2 * v + 2] = tSet[v];
                tAdd[2 * v + 1] = 0;
                tAdd[2 * v + 2] = 0;
            }
            tSet[v] = INF;
        }
        if (tAdd[v] != INF) {
            t[v] += tAdd[v];
            if (vl != vr) {
                if (tAdd[2 * v + 1] == INF) {
                    tAdd[2 * v + 1] = tAdd[v];
                } else {
                    tAdd[2 * v + 1] += tAdd[v];
                }
                if (tAdd[2 * v + 2] == INF) {
                    tAdd[2 * v + 2] = tAdd[v];
                } else {
                    tAdd[2 * v + 2] += tAdd[v];
                }
            }
            tAdd[v] = INF;
        }
    }

    private static void init(int n, Scanner reader) {
        t = new long[4 * n];
        tAdd = new long[4 * n];
        tSet = new long[4 * n];
        Arrays.fill(tAdd, Long.MAX_VALUE);
        Arrays.fill(tSet, Long.MAX_VALUE);
        long[] elements = new long[n];
        for (int i = 0; i < n; i++) {
            elements[i] = (reader.nextLong());
        }
        build(0, 0, n - 1, elements);
    }


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        init(n, reader);
        while (true) {
            try {
                String operation = reader.next();
                int left = reader.nextInt() - 1;
                int right = reader.nextInt() - 1;
                switch (operation) {
                    case "set":
                        setValue(0, 0, n - 1, left, right, reader.nextLong());
                        break;
                    case "add":
                        addValue(0, 0, n - 1, left, right, reader.nextLong());
                        break;
                    case "min":
                        System.out.println(query(0, 0, n - 1, left, right));
                        break;
                }
            } catch (Exception ignored) {
                break;
            }
        }
    }
}