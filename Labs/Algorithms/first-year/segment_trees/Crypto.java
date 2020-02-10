package Algorithms.bst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Crypto {
    private static int r;
    private static Matrix[] t;
    private static final Matrix NEURAL = new Matrix(1, 0, 0, 1);

    private static void build(int v, int vl, int vr, Matrix[] matrices) {
        if (vl == vr) {
            t[v] = matrices[vl];
            return;
        }
        int vm = vl + (vr - vl) / 2;
        build(2 * v + 1, vl, vm, matrices);
        build(2 * v + 2, vm + 1, vr, matrices);
        t[v] = t[2 * v + 1].multiply(t[2 * v + 2]);
    }

    private static Matrix query(int v, int vl, int vr, int l, int r) {
        if (r < vl || vr < l) {
            return NEURAL;
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        return query(2 * v + 1, vl, vm, l, r).multiply(query(2 * v + 2, vm + 1, vr, l, r));
    }

    private static Matrix scan(Scanner scanner) {
        return new Matrix(scanner.nextLong(), scanner.nextLong(), scanner.nextLong(), scanner.nextLong());
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream("crypto.in"));
        PrintWriter writer = new PrintWriter("crypto.out");
        r = sc.nextInt();
        int n = sc.nextInt();
        int m = sc.nextInt();
        Matrix[] matrices = new Matrix[n];
        t = new Matrix[4 * n];
        for (int i = 0; i < n; i++) {
            matrices[i] = scan(sc);
        }
        build(0, 0, n - 1, matrices);
        for (int i = 0; i < m; i++) {
            int left = sc.nextInt() - 1;
            int right = sc.nextInt() - 1;
            query(0, 0, n - 1, left, right).show(writer);
        }
        writer.close();
    }

    private static class Matrix {

        private long a;
        private long b;
        private long c;
        private long d;

        Matrix(long a, long b, long c, long d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        Matrix multiply(Matrix matrix) {
            return new Matrix(
                    (a * matrix.a + b * matrix.c) % r,
                    (a * matrix.b + b * matrix.d) % r,
                    (c * matrix.a + d * matrix.c) % r,
                    (c * matrix.b + d * matrix.d) % r
            );
        }

        private void show(PrintWriter writer) {
            writer.print(a + " " + b + "\n" + c + " " + d + "\n\n");
        }
    }
}
