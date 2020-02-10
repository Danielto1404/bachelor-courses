package Algorithms.bst;

import java.util.Scanner;

public class Painter {
    private static Quaternion[] t;
    private static int[] tSet;
    private static final int undef = -1;
    private static final Quaternion NEURAL = new Quaternion(undef, undef, 0, 0);
    private static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    private static final int MAX_SIZE = 4_000_000;
    private static final int length = MAX_SIZE / 4 - 1;
    private static final int offset = 500_000;

    private static void build() {
        t = new Quaternion[MAX_SIZE];
        tSet = new int[MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            t[i] = ZERO;
            tSet[i] = undef;
        }
    }

    private static void set(int v, int vl, int vr, int l, int r, int set) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return;
        }
        if (l <= vl && (vr <= r)) {
            tSet[v] = set;
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        set(2 * v + 1, vl, vm, l, r, set);
        set(2 * v + 2, vm + 1, vr, l, r, set);
        t[v] = t[2 * v + 1].merge(t[2 * v + 2]);
    }

    private static void push(int v, int vl, int vr) {
        int value = tSet[v];
        if (value != undef) {
            t[v] = new Quaternion(value, value, value, (vr - vl + 1) * value);
            if (vl != vr) {
                tSet[2 * v + 1] = tSet[v];
                tSet[2 * v + 2] = tSet[v];
            }
        }
        tSet[v] = undef;
    }

    private static Quaternion query(int v, int vl, int vr, int l, int r) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return NEURAL;
        }
        if (l <= vl && (vr <= r)) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        Quaternion ql = query(2 * v + 1, vl, vm, l, r);
        Quaternion qr = query(2 * v + 2, vm + 1, vr, l, r);
        return ql.merge(qr);
    }

    private static void scanAndSet(Scanner scanner) {
        String op = scanner.next();
        int left = scanner.nextInt() + offset;
        int curOffset = scanner.nextInt() - 1;
        if (op.equals("W")) {
            set(0, 0, length, left, left + curOffset, 0);
        } else {
            set(0, 0, length, left, left + curOffset, 1);
        }
        Quaternion cur = query(0, 0, length, 0, length);
        System.out.println(cur.count + " " + cur.sum);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        build();
        for (int i = 0; i < n; i++) {
            scanAndSet(scanner);
        }
    }

    static class Quaternion {
        int left;
        int right;
        int count;
        int sum;

        Quaternion(int left, int right, int count, int sum) {
            this.left = left;
            this.right = right;
            this.count = count;
            this.sum = sum;
        }

        Quaternion merge(Quaternion quaternion) {
            int cnt = count + quaternion.count;
            if (right == quaternion.left && right == 1) {
                cnt--;
            }
            return new Quaternion(left, quaternion.right, cnt, sum + quaternion.sum);
        }
    }
}
