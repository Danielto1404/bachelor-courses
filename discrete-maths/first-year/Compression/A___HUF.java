import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class 1___HUF {
    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("huffman.in");
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();
        int i = 0, j = 0;
        long ans = 0;
        long[] a = new long[n];
        long[] b = new long[n];
        for (int k = 0; k < n; k++) {
            a[k] = sc.nextLong();
            b[k] = Integer.MAX_VALUE;
        }
        Arrays.sort(a);
        for (int k = 0; k < n; k++) {
            if (i < n - 1 && j < n - 1 && a[i] + a[i + 1] <= b[j] + b[j + 1] && a[i] + a[i + 1] <= a[i] + b[j]) {
                b[k] = a[i] + a[i + 1];
                ans += b[k];
                i += 2;
                continue;
            }
            if (i < n - 1 && j < n - 1 && b[j] + b[j + 1] <= b[j] + a[i] && b[j] + b[j + 1] < a[i] + a[i + 1]) {
                b[k] = b[j] + b[j + 1];
                ans += b[k];
                j += 2;
                continue;
            }
            if (i < n - 1 && j < n - 1 && a[i] + b[j] <= a[i] + a[i + 1] && a[i] + b[j] <= b[j] + b[j + 1]) {
                b[k] = b[j] + a[i];
                i++;
                j++;
                ans += b[k];
                continue;
            }
            if (i == n - 1 && j < n - 1 && a[i] + b[j] < b[j] + b[j + 1]) {
                b[k] = a[i] + b[j];
                i++;
                j++;
                ans += b[k];
                continue;
            }
            if (j == n - 1 && i < n - 1 && a[i] + b[j] < a[i] + a[i + 1]) {
                b[k] = b[j] + a[i];
                i++;
                j++;
                ans += b[k];
                continue;
            }
            if (i == n - 1 && j == n - 1) {
                b[k] = a[i] + b[j];
                i++;
                j++;
                ans += b[k];
                continue;
            }
            if (i >= n && j < n - 1) {
                b[k] = b[j] + b[j + 1];
                j += 2;
                ans += b[k];
            }
        }
        PrintWriter writer = new PrintWriter("huffman.out");
        writer.print(ans - Integer.MAX_VALUE - b[n - 2]);
        writer.close();
    }
}

