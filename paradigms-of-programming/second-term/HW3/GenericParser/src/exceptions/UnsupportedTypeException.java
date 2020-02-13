package exceptions;

public class UnsupportedTypeException extends Throwable {
    public UnsupportedTypeException(String message) {
        super("Unknown type : " + message);
    }
}