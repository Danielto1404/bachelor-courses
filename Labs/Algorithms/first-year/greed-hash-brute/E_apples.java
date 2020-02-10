import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class E_apples {
    static class Apple {
        int dec, inc, index;

        Apple(int dec, int inc, int index) {
            this.dec = dec;
            this.inc = inc;
            this.index = index;
        }

        static int compare(Apple one, Apple two) {
            int oneDelta = one.inc - one.dec;
            int twoDelta = two.inc - two.dec;
            if (oneDelta < 0) {
                if (twoDelta < 0) {
                    return one.inc > two.inc ? -1 : 1;
                } else return 1;
            }
            if (twoDelta < 0) {
                return -1;
            } else return one.dec < two.dec ? -1 : 1;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("apples.in"));
        PrintWriter writer = new PrintWriter("apples.out");
        int n = scanner.nextInt(), height = scanner.nextInt(), iter = 0;
        Apple[] apples = new Apple[n];
        for (int i = 0; i < apples.length; ++i) {
            apples[i] = new Apple(scanner.nextInt(), scanner.nextInt(), i + 1);
        }
        Arrays.sort(apples, Apple::compare);
        boolean isPossible = true;
        StringBuilder ans = new StringBuilder();
        while (isPossible && iter < n) {
            height -= apples[iter].dec;
            isPossible = height > 0;
            height += apples[iter].inc;
            ans.append(apples[iter].index).append(" ");
            ++iter;
        }
        writer.println(isPossible ? ans : -1);
        writer.close();
    }
}
