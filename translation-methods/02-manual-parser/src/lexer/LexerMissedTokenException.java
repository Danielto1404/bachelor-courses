package lexer;

public class LexerMissedTokenException extends LexerException {

    public LexerMissedTokenException(int position, String expected, String actual) {
        super(position, expected, actual);
    }

}
