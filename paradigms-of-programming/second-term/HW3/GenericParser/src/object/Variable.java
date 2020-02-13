package object;

import expression.TripleExpression;
import types.Types;

public class Variable<T> implements TripleExpression<T> {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public T evaluate(T x, T y, T z, Types<T> operation) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new ArithmeticException("Incorrect variable name");
        }
    }
}
