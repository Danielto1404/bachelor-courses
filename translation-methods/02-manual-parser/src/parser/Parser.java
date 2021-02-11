package parser;

import lexer.Lexer;
import lexer.Lexer.Token;
import lexer.LexerException;
import tree.EpsNode;
import tree.Node;

public class Parser {

    private final Lexer lexer;

    /**
     * Constructor for Parser.
     *
     * @param lexer {@link Lexer} for input file.
     */
    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * Constructor for parsing {@link String} input.
     *
     * @param input {@link String} input stream with code.
     */
    public Parser(String input) {
        this.lexer = new Lexer(input);
    }


    public static final String START_NONTERMINAL = "START";
    public static final String DEFINITION_NONTERMINAL = "DEFINITION";
    public static final String DECORATOR_NONTERMINAL = "DECORATOR";
    public static final String ARGS_NONTERMINAL = "ARGS";
    public static final String REST_ARGS_NONTERMINAL = "REST_ARGS";
    public static final String FUN_NAME_NONTERMINAL = "FUN_NAME";

    /**
     * Parse Python definition function line code with decorator support.
     *
     * @return Parsed {@link Node} with all filled children, term {@link Boolean} values and names.
     * @throws LexerException  in case unexpected token occurs.
     * @throws ParserException in case unexpected token occurs.
     */
    public final Node parse() throws LexerException, ParserException {
        lexer.nextToken();
        Node parsed = start();
        if (!lexer.isEndToken()) {
            throw new ParserException(Token.END, lexer.token, lexer.index - lexer.tokenValue.length() - 1);
        }

        return parsed;
    }

    private Node terminal(Token expected) throws ParserException, LexerException {
        if (expected != lexer.token) {
            throw new ParserException(expected, lexer.tokenValue, lexer.index - lexer.tokenValue.length() - 1);

        }
        String value = lexer.token == Token.WORD ? lexer.tokenValue : lexer.token.name();

        lexer.nextToken();

        return new Node(value);
    }

    private Node start() throws ParserException, LexerException {
        return new Node(
                START_NONTERMINAL,
                decorator(),
                definition()
        );
    }

    private Node decorator() throws LexerException, ParserException {
        if (lexer.token == Token.AT) {
            return new Node(
                    DECORATOR_NONTERMINAL,
                    terminal(Token.AT),
                    terminal(Token.WORD),
                    terminal(Token.NEW_LINE)
            );
        }
        return new EpsNode();
    }

    private Node definition() throws LexerException, ParserException {
        return new Node(
                DEFINITION_NONTERMINAL,
                terminal(Token.DEF),
                funName()
        );
    }

    private Node funName() throws LexerException, ParserException {
        return new Node(
                FUN_NAME_NONTERMINAL,
                terminal(Token.WORD),
                terminal(Token.OPENED_BRACKET),
                args(),
                terminal(Token.CLOSED_BRACKET),
                terminal(Token.EQUALITY)
        );
    }

    private Node args() throws LexerException, ParserException {
        if (lexer.token == Token.WORD) {
            return new Node(
                    ARGS_NONTERMINAL,
                    terminal(Token.WORD),
                    restArgs()
            );
        }
        return new EpsNode();
    }

    private Node restArgs() throws LexerException, ParserException {
        if (lexer.token == Token.COMMA) {
            return new Node(
                    REST_ARGS_NONTERMINAL,
                    terminal(Token.COMMA),
                    args()
            );
        }
        return new EpsNode();
    }
}
