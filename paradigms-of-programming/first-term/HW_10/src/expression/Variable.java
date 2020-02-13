package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Variable implements NewExpression {
    private String name;

    public Variable(String newName) {
        name = newName;
    }

    public strictfp double evaluate(double x) {
        return x;
    }

    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new RuntimeException("Illegal variable");
        }
    }
}