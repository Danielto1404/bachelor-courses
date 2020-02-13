package tests;

import expression.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the value of х: ");
        int x = input.nextInt();
        System.out.print("Enter the value of y: ");
        int y = input.nextInt();
        System.out.print("Enter the value of z: ");
        int z = input.nextInt();

        int expressionResult = new Add( // x^2 - 3z + y^3
                new Subtract( // x^2 - 3z
                        new Multiply( // x^2
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply( // 3z
                                new Const(3),
                                new Variable("z")
                        )
                ),
                new Multiply( // y^3
                        new Multiply( // y^2
                                new Variable("y"),
                                new Variable("y")
                        ),
                        new Variable("y")
                )
        ).evaluate(x, y, z);

        int correctResult = x * x - 3 * z + y * y * y;

        System.out.println(expressionResult == correctResult ? "Result: " + correctResult : "ERROR!!!");
    }
}