package calculator;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Calculator {

    private final static String EXIT = "q";
    private final static String TERMINATING = "terminating...";


    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        while (true) {
            String input = reader.nextLine();
            if (input.equals(EXIT)) {
                System.out.println(TERMINATING);
                return;
            }

            System.out.println(new CalculatorParser().parse(input).val);
        }
    }
}
