import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class SubSets {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream("subsets.in"));
        PrintWriter writer = new PrintWriter("subsets.out");
        Map<String, Integer> sets = new TreeMap<>();
        int n = scanner.nextInt();
        int[] a;
        for (int i = 1; i <= n; i++) {
            a = new int[i];
            for (int j = 1; j <= i; j++) {
                a[j - 1] = j;
            }
            StringBuilder set = new StringBuilder();
            for (int num : a) {
                if (num < 10) {
                    set.append(num + " ");
                } else set.append("9" + num);
            }
            sets.put(set.toString(), 0);
            ;
            while ((a = nextChoose(a, i, n)) != null) {
                set = new StringBuilder();
                for (int num : a) {
                    if (num < 10) {
                        set.append(num + " ");
                    } else set.append("9" + num);
                }
                sets.put(set.toString(), 0);
            }
        }
        writer.print("\n");
        for (String curSet : sets.keySet()) {
            if (!curSet.contains("910")) {
                writer.println(curSet);
            } else {
                for (String cur : curSet.split("\\s")) {
                    if (!cur.equals("910") && !cur.isEmpty()) {
                        writer.print(cur + " ");
                    } else writer.print("10");
                }
                writer.print("\n");
            }
        }
        writer.close();
    }

    private static int[] nextChoose(int[] a, int k, int n) {
        int[] b = new int[k + 1];
        for (int i = 0; i < k; i++) {
            b[i] = a[i];
        }
        b[k] = n + 1;
        int i = k - 1;
        while ((i >= 0) && (b[i + 1] - b[i] < 2))
            i--;
        if (i >= 0) {
            b[i]++;
            for (int j = i + 1; j < k; j++) {
                b[j] = b[j - 1] + 1;
            }
            for (i = 0; i < k; i++)
                a[i] = b[i];
            return a;
        }
        return null;
    }
}

