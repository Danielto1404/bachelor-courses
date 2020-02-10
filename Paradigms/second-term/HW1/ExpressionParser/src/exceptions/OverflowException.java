package exceptions;

public class OverflowException extends ArithmeticParserException {
    public OverflowException() {
        super("overflow");
    }
}
