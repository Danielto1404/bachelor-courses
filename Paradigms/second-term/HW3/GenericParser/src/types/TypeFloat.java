package types;

import exceptions.ArithmeticParserException;

public class TypeFloat implements Types<Float> {

    public Float add(final Float x, final Float y) {
        return (x + y);
    }

    public Float subtract(final Float x, final Float y) {
        return (x - y);
    }

    public Float multiply(final Float x, final Float y) {
        return (x * y);
    }

    public Float divide(final Float x, final Float y) {
        return (x / y);
    }

    public Float min(final Float x, final Float y) {
        return (Math.min(x, y));
    }

    public Float max(final Float x, final Float y) {
        return (Math.max(x, y));
    }

    public Float mod(final Float x, final Float y) {
        return (x % y);
    }

    public Float abs(final Float x) {
        return Math.abs(x);
    }

    public Float negate(final Float x) {
        return -x;
    }

    public Float square(final Float x) {
        return (x * x);
    }

    public Float parse2Digit(String str) throws ArithmeticParserException {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            throw new ArithmeticParserException("Can't parse " + str + " to Float");
        }
    }
}
