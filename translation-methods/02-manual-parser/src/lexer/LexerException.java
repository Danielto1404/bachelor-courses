package lexer;

public class LexerException extends Exception {

    public LexerException(String message) {
        super(message);
    }

    public LexerException(int position, String expected, String actual) {
        this("[ Error position: " + position + " ] " +
                " [ Expected: " + expected + " ] " +
                " [ Actual: " + actual + " ]"
        );
    }

}

