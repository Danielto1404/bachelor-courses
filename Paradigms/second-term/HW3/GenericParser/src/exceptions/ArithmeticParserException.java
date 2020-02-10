package exceptions;


public class ArithmeticParserException extends ArithmeticException {
    public ArithmeticParserException(String message) {
        super(message + "\n");
    }
}
