package Algorithms.bst;

import java.util.Scanner;


public class RSQ {

    private static long[] t;

    private static void build(int v, int vl, int vr, long[] a) {
        if (vl == vr) {
            t[v] = a[vl];
            return;
        }
        int vm = vl + (vr - vl) / 2;
        build(2 * v + 1, (vl), vm, a);
        build(2 * v + 2, vm + 1, vr, a);
        t[v] = t[2 * v + 1] + t[2 * v + 2];
    }

    private static long query(int v, int vl, int vr, int l, int r) {
        if (r < vl || vr < l) {
            return 0;
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        return query(2 * v + 1, vl, vm, l, r) + query(2 * v + 2, vm + 1, vr, l, r);
    }

    private static void modify(int v, int vl, int vr, int pos, long val) {
        if (vl == vr) {
            t[v] = val;
            return;
        }
        int vm = vl + (vr - vl) / 2;
        if (pos <= vm) {
            modify(2 * v + 1, vl, vm, pos, val);
        } else {
            modify(2 * v + 2, vm + 1, vr, pos, val);
        }
        t[v] = t[2 * v + 1] + t[2 * v + 2];
    }


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        t = new long[4 * n];
        long[] elements = new long[n];
        for (int i = 0; i < n; i++) {
            elements[i] = reader.nextLong();
        }
        build(0, 0, n - 1, elements);
        while (true) {
            try {
                String operation = reader.next();
                int ind = reader.nextInt() - 1;
                switch (operation) {
                    case "sum":
                        System.out.println(query(0, 0, n - 1, ind, reader.nextInt() - 1));
                        break;
                    case "set":
                        modify(0, 0, n - 1, ind, reader.nextLong());
                }
            } catch (Exception ignored) {
                break;
            }
        }
    }
}


