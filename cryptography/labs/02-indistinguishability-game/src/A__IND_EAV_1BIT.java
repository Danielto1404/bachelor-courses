import java.util.Scanner;

public class A__IND_EAV_1BIT {

    private static final Scanner sc = new Scanner(System.in);
    private static final int ALL_ROUNDS = 10_000;

    private static int correctAnswersForZero = 0;
    private static int correctAnswersForOne = 0;


    private static int getBit() {
        return Integer.parseInt(sc.nextLine());
    }


    private static boolean isAnswerCorrect() {
        return sc.nextLine().equals("YES");
    }


    public static void main(String[] args) {

        for (int i = 0; i < ALL_ROUNDS; ++i) {

            int c = getBit();

            int keyBit = correctAnswersForZero > correctAnswersForOne ? 0 : 1;
            System.out.println(c ^ keyBit);

            if (isAnswerCorrect()) {
                int dummy = keyBit == 0 ? ++correctAnswersForZero : ++correctAnswersForOne;
            } else {
                int dummy = keyBit == 0 ? ++correctAnswersForOne : ++correctAnswersForZero;
            }
        }

        sc.close();
    }
}
