package search;

public class BinarySearchMissing {

    //  PRE: i < j => a[i] >= a[j]
    // POST: ℝ = i  ∧  a[i - 1] > x >= a[i]
    public static int iterativeBinarySearch(int x, int a[]) {
        int l = 0, r = a.length;
        // INV: a[0]..a[l - 1] > x >= a[r]..a[a.length - 1]
        while (l < r) {
            // INV  ∧  l < r
            int m = (l + r) / 2;
            // l <= m < r

            if (x >= a[m]) {
                // INV  ∧  x >= a[m]
                r = m;
                // a[0]..a[l - 1] > x >= a[m]..a[a.length - 1]
            } else {
                // INV  ∧  x < a[m]
                l = m + 1;
                // a[0]..a[m] > x >= a[r]..a[a.length - 1]
            }
        }
        // POST: INV  ∧  l >= r
        //       => a[l - 1] > x >= a[r]  ∧  l - 1 >= r - 1
        //       => a[r - 1] > x >= a[r]
        //       => R = r
        //       r < a.length  ∧  a[r] == x  => r
        //       r >= a.length  v  a[r] != x => -r - 1
        return (r < a.length) && (a[r] == x) ? r : (-r - 1);
    }

    public static int recursiveBinarySearch(int x, int a[]) {
        return recursiveBinarySearchImpl(x, a, 0, a.length);
    }

    //  PRE: i < j => a[i] >= a[j]
    // POST: ℝ = i  ∧  a[i - 1] > x >= a[i]
    //  INV: a[0]..a[l - 1] > x >= a[r]..a[a.length - 1]
    private static int recursiveBinarySearchImpl(int x, int a[], int l, int r) {
        if (l >= r) {
            //  INV  ∧  l >= r
            //  => a[l - 1] > x >= a[r]  ∧  l - 1 >= r - 1
            //  => a[r - 1] > x >= a[r]
            //  => R = r
            //       r < a.length  ∧  a[r] == x  => r
            //       r >= a.length  v  a[r] != x => -r - 1
            return (r < a.length) && (a[r] == x) ? r : (-r - 1);
        }
        // INV  ∧  l < r
        int m = (l + r) / 2;
        // l <= m < r

        if (x >= a[m]) {
            // INV  ∧  x >= a[m]
            // a[0]..a[l - 1] > x >= a[m]..a[a.length - 1]
            return recursiveBinarySearchImpl(x, a, l, m);
        } else {
            // INV  ∧  x < a[m]
            // a[0]..a[m] > x >= a[r]..a[a.length - 1]
            return recursiveBinarySearchImpl(x, a, m + 1, r);
        }
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            int x = Integer.parseInt(args[0]);
            int[] a = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                a[i - 1] = Integer.parseInt(args[i]);
            }
            int iterativeResult = iterativeBinarySearch(x, a);
            int recursiveResult = recursiveBinarySearch(x, a);
            System.out.println(iterativeResult == recursiveResult ? iterativeResult : "Try again");
        } else {
            System.out.println("Usage java : <number> <array> ");
        }

    }
}