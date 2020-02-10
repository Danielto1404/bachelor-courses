package expression;

public strictfp class Const implements NewExpression {
    private final double value;

    public Const(int newValue) {
        value = newValue;
    }

    public strictfp double evaluate(double x) {
        return value;
    }

    public int evaluate(int x) {
        return (int) value;
    }

    public int evaluate(int x, int y, int z) {
        return (int) value;
    }
}