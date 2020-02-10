package Algorithms.bst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Parking {

    private static Node[] t;
    private static final int INF = Integer.MAX_VALUE;
    private static final int ZERO = 0;
    private static final Node NEURAL = new Node(INF, INF);
    private static int n;

    private static void build(int v, int vl, int vr) {
        if (vl == vr) {
            t[v] = new Node(ZERO, vl);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        build(2 * v + 1, vl, vm);
        build(2 * v + 2, vm + 1, vr);
        t[v] = t[2 * v + 1].merge(t[2 * v + 2]);
    }

    private static void modify(int v, int vl, int vr, int pos, int val) {
        if (vl == vr) {
            if (val == ZERO) {
                t[v] = new Node(ZERO, vl);
            } else {
                t[v] = new Node(INF, INF);
            }
            return;
        }
        int vm = vl + (vr - vl) / 2;
        if (pos <= vm) {
            modify(2 * v + 1, vl, vm, pos, val);
        } else {
            modify((2 * v + 2), vm + 1, vr, pos, val);
        }
        t[v] = t[2 * v + 1].merge(t[2 * v + 2]);
    }

    private static Node query(int v, int vl, int vr, int l, int r) {
        if (vr < l || (r < vl)) {
            return NEURAL;
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        Node ql = query(2 * v + 1, vl, vm, l, r);
        Node qr = query(2 * v + 2, vm + 1, vr, l, r);
        return ql.merge(qr);
    }

    private static int findIndex(int i) {
        Node right = query(0, 0, n - 1, i, n - 1);
        Node left = query(0, 0, n - 1, 0, i - 1);

        // Если справа занято, то ищем слева //
        if (right.min == INF) {
            return left.freePosition;
        }
        return right.freePosition;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("parking.in"));
        PrintWriter writer = new PrintWriter("parking.out");
        n = scanner.nextInt();
        t = new Node[4 * n];
        build(0, 0, n - 1);
        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            switch (scanner.next()) {
                case "enter":
                    int index = findIndex(scanner.nextInt() - 1);
                    writer.println(index + 1);
                    modify(0, 0, n - 1, index, 1);
                    break;
                case "exit":
                    modify(0, 0, n - 1, scanner.nextInt() - 1, 0);
            }
        }
        writer.close();
    }

    static class Node {
        int min;
        int freePosition;

        Node(int min, int freePosition) {
            this.min = min;
            this.freePosition = freePosition;
        }

        Node merge(Node second) {
            return new Node(Math.min(min, second.min), Math.min(freePosition, second.freePosition));
        }
    }
}
