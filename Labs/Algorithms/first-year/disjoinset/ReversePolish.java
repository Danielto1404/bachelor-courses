package Algorithms.disjoinset;

import java.util.Scanner;
import java.util.Stack;

public class ReversePolish {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        Scanner sc = new Scanner(System.in);
        String[] elements = sc.nextLine().split("\\s");
        for (String element : elements) {
            if (!check(element.charAt(0), stack)) {
                stack.push(Integer.parseInt(element));
            }
        }
        System.out.println(stack.peek());
    }

    private static boolean check(char sign, Stack<Integer> stack) {
        if (sign == '+' || sign == '-' || sign == '*') {
            int cur1 = stack.pop();
            int cur2 = stack.pop();
            if (sign == '+') {
                stack.push(cur1 + cur2);
            } else if (sign == '-') {
                stack.push(cur2 - cur1);
            } else stack.push(cur1 * cur2);
            return true;
        } else return false;
    }
}

