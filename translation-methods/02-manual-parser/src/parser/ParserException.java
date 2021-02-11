package parser;

import lexer.Lexer;

public class ParserException extends Exception {
    public ParserException(Lexer.Token expected, Lexer.Token actual, int position) {
        super("[ Error position: " + position + " ] " +
                " [ Expected: " + expected.name() + " ] " +
                " [ But got: " + actual.name() + " ]"
        );
    }

    public ParserException(Lexer.Token expected, String actualValue, int position) {
        super("[ Error position: " + position + " ] " +
                " [ Expected: " + expected.toString() + " ] " +
                " [ But got: " + actualValue + " ]"
        );
    }
}
