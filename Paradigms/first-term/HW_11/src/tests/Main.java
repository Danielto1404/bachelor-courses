package tests;

import expression.parser.*;

import java.util.*;

/**
 * @author: Daniil Korolev
 * created: 09.12.2018 15:42:43
 */

public class Main {
    private static List<Boolean> testResult = new ArrayList<>();
    private static int numberOfTests = 1;

    private static void border() {
        System.out.println("________________\n");
    }

    private static void testNumber() {
        System.out.printf("Test #%d:\n", numberOfTests++);
    }

    private static void result() {
        if (!testResult.contains(false)) {
            System.out.println("Testing completed successfully!");
        } else {
            System.out.println("TESTING FAILED!!!");
            int testNumber = 0;
            for (boolean currentIsCorrect : testResult) {
                ++testNumber;
                if (!currentIsCorrect) {
                    System.out.printf("Wrong answer on test #%d\n", testNumber);
                }
            }
        }
        System.out.println("====");
    }

    private static void standardTest(ExpressionParser parser) {
        int tmp;

        testNumber();
        System.out.print("\t" + (tmp = parser.parse("x * (x \n\n\n\n\n- 2)*x + 1")
                .evaluate(5, 0, 0)));
        System.out.printf(" == %d\n", 76);
        testResult.add(tmp == 76);
        border();
    }

    private static void shiftsTest(ExpressionParser parser) {
        int tmp;

        testNumber();
        System.out.print("\t" + (tmp = parser.parse("((((1         \n\n\n  << 5 + 3))))")
                .evaluate(5, 0, 0)));
        System.out.printf(" == %d\n", 256);
        testResult.add(tmp == 256);
        border();
    }

    private static void bitwiseTest(ExpressionParser parser) {
        int tmpA, tmpB;

        testNumber();
        System.out.print("\t" + (tmpA = parser.parse("6 & 1 + \t\t\r2").evaluate(5, 0, 0)));
        System.out.print(" == " + (tmpB = parser.parse("6 & \r(1 + 2)").evaluate(5, 0, 0)));
        System.out.printf(" == %d\n", 2);
        testResult.add(tmpA == tmpB && tmpA == 2);
        border();

        testNumber();
        System.out.print("\t" + (tmpA = parser.parse("6 ^ 1 + \t2").evaluate(5, 0, 0)));
        System.out.print(" == " + (tmpB = parser.parse("6 \n^ (1 + 2)").evaluate(5, 0, 0)));
        System.out.printf(" == %d\n", 5);
        testResult.add(tmpA == tmpB && tmpA == 5);
        border();

        testNumber();
        System.out.print("\t" + (tmpA = parser.parse("6 |\t 1 + 2").evaluate(5, 0, 0)));
        System.out.print(" == " + (tmpB = parser.parse("6 | (1 + 2)").evaluate(5, 0, 0)));
        System.out.printf(" == %d\n", 7);
        testResult.add(tmpA == tmpB && tmpA == 7);
        border();
    }

    private static void notCountTest(ExpressionParser parser) {
        int tmp;

        testNumber();
        System.out.print("\t" + (tmp = parser.parse("~-\r5").evaluate(3, 2, 2)));
        System.out.printf(" == %d\n", 4);
        testResult.add(tmp == 4);
        border();

        testNumber();
        System.out.print("\t" + (tmp = parser.parse("count \n-5").evaluate(2, 2, 8)));
        System.out.printf(" == %d\n", 31);
        testResult.add(tmp == 31);
        border();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ExpressionParser testParser = new ExpressionParser();

        standardTest(testParser);
        shiftsTest(testParser);
        bitwiseTest(testParser);
        notCountTest(testParser);

        result();
        long finishTime = System.currentTimeMillis();
        System.out.println("Time: " + (finishTime - startTime) + " ms");
    }
}
