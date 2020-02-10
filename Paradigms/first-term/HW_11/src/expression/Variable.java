package expression;

public class Variable implements TripleExpression {
    private String name;

    public Variable(String name) {
        assert name != null : "ERROR: The name of variable is null!";
        this.name = name;
    }

    public int evaluate(int x, int y, int z) {
        assert name.equals("x") || name.equals("y") || name.equals("z") : "ERROR: Variable has wrong name!";
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        return 0;
    }
}
