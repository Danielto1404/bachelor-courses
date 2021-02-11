package lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Lexer {

    /**
     * Represent all tokens for Python definition function code with decorator support.
     */
    public enum Token {
        START,
        AT,
        NEW_LINE,
        DEF,
        WORD,
        OPENED_BRACKET,
        CLOSED_BRACKET,
        EQUALITY,
        COMMA,
        END;

        public String toString() {
            switch (this) {
                case START -> {
                    return "START_OF_STREAM";
                }
                case AT -> {
                    return "@";
                }
                case NEW_LINE -> {
                    return "NL";
                }
                case DEF -> {
                    return "'def'";
                }
                case WORD -> {
                    return "letter (letter | digit | _)*";
                }
                case OPENED_BRACKET -> {
                    return "'('";
                }
                case CLOSED_BRACKET -> {
                    return "')'";
                }
                case EQUALITY -> {
                    return "'='";
                }
                case COMMA -> {
                    return "','";
                }
                case END -> {
                    return "END_OF_STREAM";
                }
            }
            return "UNDEFINED_TOKEN";
        }
    }

    /**
     * Returns current token
     */
    public Token token = Token.START;
    /**
     * Returns current token string value
     */
    public String tokenValue = null;
    /**
     * Represents pointer to actual stream char.
     */
    public int index;

    private char symbol = START;
    private final Reader reader;

    private final static char EOF = (char) (-1);
    private final static char START = (char) (-239);
    private final static String END_OF_INPUT = "END_OF_INPUT";

    private final static String NL = "\n";
    private final static String AT = "@";
    private final static String OPENED_BRACKET = "(";
    private final static String CLOSED_BRACKET = ")";
    private final static String EQUALITY = "=";
    private final static String COMMA = ",";
    private final static String DEF_KEYWORD = "def";

    /**
     * Construct that takes file reader.
     *
     * @param reader File reader.
     */
    public Lexer(Reader reader) {
        this.reader = reader;
        this.index = 0;
    }

    /**
     * Constructor for string input.
     *
     * @param input String input with code.
     */
    public Lexer(String input) {
        this(new StringReader(input));
    }

    /**
     * Checks end of the stream.
     *
     * @return boolean value indication whether current token is equal to the END of stream.
     */
    public boolean isEndToken() {
        return token == Token.END;
    }

    /**
     * Consumes stream and returns next token.
     * Set parsed token to {@link #token} if token parsed successfully.
     * Set string value to {@link #tokenValue} if token parsed successfully.
     *
     * @return next token after current token if stream satisfies.
     * @throws LexerException in case of unexpected token.
     */
    public Token nextToken() throws LexerException {

        skipSpaces();

        if (symbol == EOF) {
            token = Token.END;
            tokenValue = END_OF_INPUT;
            return token;
        }

        if (symbol == START) {
            read();
            return nextToken();
        }

        switch (String.valueOf(symbol)) {
            case AT -> {
                token = Token.AT;
                tokenValue = AT;
            }
            case NL -> {
                token = Token.NEW_LINE;
                tokenValue = "NEW LINE";
            }
            case OPENED_BRACKET -> {
                token = Token.OPENED_BRACKET;
                tokenValue = OPENED_BRACKET;
            }
            case CLOSED_BRACKET -> {
                token = Token.CLOSED_BRACKET;
                tokenValue = CLOSED_BRACKET;
            }
            case EQUALITY -> {
                token = Token.EQUALITY;
                tokenValue = EQUALITY;
            }
            case COMMA -> {
                token = Token.COMMA;
                tokenValue = COMMA;
            }
            default -> {
                tokenValue = getWord();
                if (DEF_KEYWORD.equals(tokenValue)) {
                    token = Token.DEF;
                } else {
                    token = Token.WORD;
                }
                return token;
            }
        }
        read();
        return token;
    }

    /**
     * Reads stream of bytes for given file reader.
     * Sets read char value to {@link #symbol}.
     *
     * @throws LexerMissedTokenException in case stream is ended.
     */
    private void read() throws LexerMissedTokenException {
        try {
            symbol = (char) reader.read();
        } catch (IOException ignored) {
            symbol = EOF;
            throw new LexerMissedTokenException(index, "char value", END_OF_INPUT);
        }
        ++index;
    }

    /**
     * @return Word satisfying "\w(\w\d_)* regex.
     * @throws LexerException in case starts not with letter or end of input occurred.
     */
    private String getWord() throws LexerException {

        StringBuilder word = new StringBuilder();
        if (!isLetter()) {
            throw new LexerMissedTokenException(index, "word starts with letter", String.valueOf(symbol));
        }
        while (isWordChar()) {
            word.append(symbol);
            read();
        }

        return word.toString();
    }


    /**
     * Skips spaces.
     *
     * @throws LexerException in case end of input occurred
     */
    private void skipSpaces() throws LexerException {
        while (Character.isWhitespace(symbol) && symbol != '\n') {
            read();
        }
    }

    /**
     * @return {@link Boolean} value indicating whether symbol is letter.
     */
    private boolean isLetter() {
        return Character.isLetter(symbol);
    }

    /**
     * @return {@link Boolean} value indicating whether symbol is digit.
     */
    private boolean isDigit() {
        return Character.isDigit(symbol);
    }

    /**
     * @return {@link Boolean} value indicating whether symbol is word char (letter / digit / _ ).
     */
    private boolean isWordChar() {
        return isLetter() || isDigit() || symbol == '_';
    }
}
