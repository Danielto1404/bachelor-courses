package search;

public class BinarySearch {
    public static void main(String[] args) {
        //PRE: args.length >= 1 && ∀i in [1..args.length) : a[i] >= a[i + 1]
        if (args.length > 1) {
            // PRE: args[0] - number
            // POST:  ℝ = args[0]
            int x = Integer.parseInt(args[0]);
            // PRE: left bound == 1 && right bound == args.length - 1
            int l = 1;
            int r = args.length - 1;
            // PRE: left bound in [1..args.length -1] && right bound in [1..args.length - 1]
            // && ∀i = 1..n : a[i + 1] <= a[i]
            // POST:  ℝ = index : a[index] == x || index = min(i), ∀i in [1..args.length) : a[i] <= x
            while (l < r) {
                int m = l + (r - l) / 2;
                // PRE: ∀ (l,r) >= 1 && l <= r : l <= m <= r
                // ∀i : i >= m: x >= args[i] => move right bound to middle.
                // We have to find x in [l, m]
                if (Integer.parseInt(args[m]) <= x) {
                    r = m;
                }
                // Otherwise : ∀i i <= m : x < args[i] => we have to find x in [m + 1, r]
                else {
                    l = m + 1;
                }
                //POST:
            }
            // PRE: args[args.length - 1] <= args[i], ∀i in [1..args.length -2] && args[args.length - 1] > x =>
            // => ∀i in [1..args.length): args[i] > x => ℝ = args.length - 1
            // POST: ℝ = args.length - 1
            if (Integer.parseInt(args[args.length - 1]) > x) {
                r = args.length;
            }
            // PRE: args[1] >= args[i], ∀i in [2..args.length - 1] && args[1] < x => x
            // => x > args[i], ∀i in [1..args.length - 1]
            // POST: ℝ = 1
            else if (Integer.parseInt(args[1]) < x) {
                r = 1;
            }
            // POST: l >= r && index = r - 1
            System.out.println(r - 1);
        }
        // args.length <= 1 => we don't have element's in our array => writeln(0)
        else {
            System.out.println(0);
        }
    }
}
