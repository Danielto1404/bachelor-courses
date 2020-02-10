package Algorithms.bst;

import java.util.Arrays;
import java.util.Scanner;

public class GreatWall {

    private static Wall[] t;
    private static final long INF = Long.MAX_VALUE;
    private static final int UNDEF = -1;
    private static final int ZERO = 0;
    private static final Wall NEURAL = new Wall(INF, UNDEF);
    private static long[] tSet;

    private static void build(int v, int vl, int vr) {
        if (vl == vr) {
            t[v] = new Wall(ZERO, vl);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        build((2 * v + 1), vl, vm);
        build(2 * v + 2, vm + 1, vr);
        t[v] = t[2 * v + 1].merge(t[2 * v + 2]);
    }

    private static void setValue(int v, int vl, int vr, int l, int r, int set) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return;
        }
        if (l <= vl && (vr <= r)) {
            tSet[v] = (set);
            push(v, vl, vr);
            return;
        }
        int vm = (vl + (vr - vl) / 2);
        setValue(2 * v + 1, vl, vm, l, r, set);
        setValue(2 * v + 2, vm + 1, vr, l, r, set);
        t[v] = t[2 * v + 1].merge(t[2 * v + 2]);
    }

    private static Wall query(int v, int vl, int vr, int l, int r) {
        push(v, vl, vr);
        if (vr < l || r < vl) {
            return NEURAL;
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        Wall ql = query(2 * v + 1, vl, vm, l, r);
        Wall qr = query(2 * v + 2, vm + 1, vr, l, r);
        return ql.merge(qr);
    }

    private static void push(int v, int vl, int vr) {
        if (tSet[v] != INF) {
            if (t[v].min < tSet[v]) {

                // Ставим t[v].posOfMin так как на текущем подотрезке это позиция минимума
                t[v] = new Wall(tSet[v], t[v].posOfMin);
                if (vl != vr) {
                    tSet[2 * v + 1] = tSet[v];
                    tSet[2 * v + 2] = tSet[v];
                }
            }
            tSet[v] = INF;
        }
    }

    private static void init(int n) {
        t = new Wall[4 * n];
        tSet = new long[4 * n];
        build(0, 0, n - 1);
        Arrays.fill(tSet, INF);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        init(n);
        for (int i = 0; i < m; i++) {
            String cmd = scanner.next();
            int left = scanner.nextInt() - 1;
            int right = scanner.nextInt() - 1;
            if ("defend".equals(cmd)) {
                setValue(0, 0, n - 1, left, right, scanner.nextInt());
            } else {
                Wall cur = query(0, 0, n - 1, left, right);
                System.out.println(cur.min + " " + (cur.posOfMin + 1));
            }
        }
    }

    public static class Wall {
        long min;
        int posOfMin;

        Wall(long min, int posOfMin) {
            this.min = min;
            this.posOfMin = posOfMin;
        }

        Wall merge(Wall wall) {
            int position = wall.min <= min ? wall.posOfMin : posOfMin;
            return new Wall(Math.min(min, wall.min), position);
        }
    }
}
