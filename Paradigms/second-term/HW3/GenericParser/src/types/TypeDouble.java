package types;

import exceptions.ArithmeticParserException;

public class TypeDouble implements Types<Double> {

    public Double add(final Double x, final Double y) {
        return (x + y);
    }

    public Double subtract(final Double x, final Double y) {
        return (x - y);
    }

    public Double multiply(final Double x, final Double y) {
        return (x * y);
    }

    public Double divide(final Double x, final Double y) {
        return (x / y);
    }

    public Double min(final Double x, final Double y) {
        return (Math.min(x, y));
    }

    public Double max(final Double x, final Double y) {
        return (Math.max(x, y));
    }

    public Double mod(final Double x, final Double y) {
        return (x % y);
    }

    public Double abs(final Double x) {
        return Math.abs(x);
    }

    public Double negate(final Double x) {
        return -x;
    }

    public Double square(final Double x) {
        return (x * x);
    }

    public Double parse2Digit(String str) throws ArithmeticParserException {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            throw new ArithmeticParserException("Can't parse " + str + " to Double");
        }
    }
}
