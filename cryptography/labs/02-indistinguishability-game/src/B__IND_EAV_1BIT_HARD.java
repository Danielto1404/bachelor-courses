import java.util.Scanner;

public class B__IND_EAV_1BIT_HARD {

    private static final Scanner sc = new Scanner(System.in);

    private static final int ALL_ROUNDS = 10_000;
    private static final int TEST_ROUNDS = 2_000;
    private static final int MAX_PERIOD_LENGTH = 1_000;

    private static int getBit() {
        return Integer.parseInt(sc.nextLine());
    }

    private static boolean isAnswerCorrect() {
        return sc.nextLine().equals("YES");
    }

    private static void skip() {
        sc.nextLine();
    }

    public static void main(String[] args) {

        int[] testKeys = new int[TEST_ROUNDS];

        for (int i = 0; i < TEST_ROUNDS; ++i) {
            int c = getBit();
            System.out.println(c);
            testKeys[i] = isAnswerCorrect() ? 0 : 1;
        }

        int period = 0;

        for (int periodLength = 1; periodLength <= MAX_PERIOD_LENGTH; ++periodLength) {

            boolean isFound = true;

            for (int i = 0; i <= testKeys.length; ++i) {
                if (i + periodLength >= testKeys.length) break;

                if (testKeys[i] != testKeys[i + periodLength]) {
                    isFound = false;
                    break;
                }
            }

            if (isFound)
                period = periodLength;
        }

        for (int i = TEST_ROUNDS; i < ALL_ROUNDS; ++i) {
            int c = getBit();
            System.out.println(c ^ testKeys[i % period]);
            skip();
        }

        sc.close();
    }
}
