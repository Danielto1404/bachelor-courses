package parser;

import exceptions.*;
import expression.TripleExpression;
import object.*;
import operation.operators.*;
import types.Types;

public class ExpressionParser<T> implements GenericParser<T> {
    private static final char END = '\0';
    private String expression;
    private int curPos;
    private int len;

    private Types<T> parser;


    public ExpressionParser(Types<T> parser) {
        this.parser = parser;
    }

    private boolean checkPos() {
        return curPos < len;
    }

    private String getExpression() {
        return expression.substring(0, len - 1);
    }

    private char curChar() {
        return expression.charAt(curPos);
    }

    private void next() {
        curPos++;
        skipSpace();
    }

    private void skipSpace() {
        while (checkPos() && Character.isWhitespace(curChar())) {
            curPos++;
        }
    }

    private String getNumber() {
        StringBuilder number = new StringBuilder();
        while (checkPos() && Character.isDigit(curChar())) {
            number.append(curChar());
            next();
        }
        skipSpace();
        return number.toString();
    }

    private boolean test(char c) {
        boolean result = curChar() == c;
        if (result) {
            next();
        }
        skipSpace();
        return result;
    }

    private String getIdentifier() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < len && Character.isLetter(expression.charAt(index + curPos))) {
            sb.append(expression.charAt(curPos + index));
            index++;
        }
        return sb.toString();
    }

    private void expect(char symbol) throws FormatParserException {
        if (symbol != curChar()) {
            if (curChar() == END) {
                throw new MissingParenthesis("closing", curPos, getExpression());
            } else {
                throw new UnexpectedCharacter(curChar(), curPos, getExpression());
            }
        } else {
            next();
        }
    }

    private TripleExpression<T> getConst(String number) throws FormatParserException {
        try {
            return new Const<>(parser.parse2Digit(number));
        } catch (NumberFormatException NFE) {
            throw new FormatParserException("Illegal constant at index " +
                    (curPos - number.length() + 1) + "\n" + number + "\n");
        }
    }

    private TripleExpression<T> resultFromUnaryOperation() throws FormatParserException {
        String identifier = getIdentifier();
        curPos += identifier.length();
        switch (identifier) {
            case "square":
                return new Square<>(unary());
            case "abs":
                return new Abs<>(unary());
            default:
                throw new UnknownFunctionName(identifier, curPos - identifier.length(), getExpression());
        }
    }

    private TripleExpression<T> resultInBrackets() throws FormatParserException {
        next();
        TripleExpression<T> currentResult = addAndSub();
        expect(')');
        return currentResult;
    }

    private TripleExpression<T> addAndSub() throws FormatParserException {
        TripleExpression<T> currentResult = mulAndDiv();
        while (true) {
            if (test('+')) {
                currentResult = new Add<>(currentResult, mulAndDiv());
            } else if (test('-')) {
                currentResult = new Subtract<>(currentResult, mulAndDiv());
            } else {
                return currentResult;
            }
        }
    }

    private TripleExpression<T> mulAndDiv() throws FormatParserException {
        TripleExpression<T> currentResult = unary();
        while (true) {
            if (test('*')) {
                currentResult = new Multiply<>(currentResult, unary());
            } else if (test('/')) {
                currentResult = new Divide<>(currentResult, unary());
            } else if (curChar() == 'm') {
                String identifier = getIdentifier();
                if (identifier.equals("mod")) {
                    curPos += 3;
                    currentResult = new Mod<>(currentResult, unary());
                } else {
                    throw new UnknownFunctionName(identifier, curPos, getExpression());
                }
            } else {
                return currentResult;
            }
        }
    }

    private TripleExpression<T> unary() throws FormatParserException {
        skipSpace();
        char curSymbol = curChar();
        switch (curSymbol) {
            case 'a':
            case 's':
            case 'c':
                return resultFromUnaryOperation();
            case '(':
                return resultInBrackets();
            case '-':
                next();
                if (Character.isDigit(curChar())) {
                    return getConst("-" + getNumber());
                } else {
                    return new Negate<>(unary());
                }
            case 'x':
            case 'y':
            case 'z':
                next();
                return new Variable<>(String.valueOf(curSymbol));
            default:
                if (Character.isDigit(curChar())) {
                    return getConst(getNumber());
                } else {
                    throw new MissingArgumentException(curPos, getExpression());
                }
        }
    }

    public TripleExpression<T> parse(String s) throws FormatParserException {
        curPos = 0;
        expression = s + END;
        len = expression.length();
        TripleExpression<T> result = addAndSub();
        expect(END);
        return result;
    }
}