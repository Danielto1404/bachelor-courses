package exceptions;

public class IllegalOperationException extends ArithmeticParserException {
    public IllegalOperationException(final String message) {
        super("Illegal operation: " + message);
    }
}