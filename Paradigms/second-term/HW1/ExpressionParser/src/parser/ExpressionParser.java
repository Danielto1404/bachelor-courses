package parser;

import exceptions.*;
import expression.TripleExpression;
import object.*;
import operation.checked.*;

public class ExpressionParser implements Parser {
    private static final char END = '\0';
    private String expression;
    private int curPos;
    private int len;

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

    private void expect(char symbol) throws FormatParserException {
        if (checkPos() && symbol != curChar()) {
            if (curChar() == END) {
                throw new MissingParenthesis("closing", curPos, getExpression());
            } else {
                throw new UnexpectedCharacter(curChar(), curPos, getExpression());
            }
        } else {
            next();
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
        int i = 0;
        while (i < len && Character.isLetter(expression.charAt(i + curPos))) {
            sb.append(expression.charAt(curPos + i));
            i++;
        }
        return sb.toString();
    }

    private TripleExpression getConst(String number) throws FormatParserException {
        try {
            return new Const(Integer.parseInt(number));
        } catch (NumberFormatException NFE) {
            throw new FormatParserException("Illegal constant at index " +
                    (curPos - number.length() + 1) + "\n" + number + "\n");
        }
    }

    private TripleExpression getResultFromFunction() throws FormatParserException {
        String identifier = getIdentifier();
        curPos += identifier.length();
        switch (identifier) {
            case "low":
                return new Low(unary());
            case "high":
                return new High(unary());
            default:
                throw new UnknownFunctionName(identifier, curPos - identifier.length(), getExpression());
        }
    }

    private TripleExpression resultInBrackets() throws FormatParserException {
        next();
        TripleExpression currentResult = addAndSubtract();
        if (!checkPos()) {
            throw new MissingParenthesis("closing", curPos, getExpression());
        }
        expect(')');
        return currentResult;
    }

    private TripleExpression addAndSubtract() throws FormatParserException {
        TripleExpression currentResult = mulAndDiv();
        while (true) {
            if (test('+')) {
                currentResult = new CheckedAdd(currentResult, mulAndDiv());
            } else if (test('-')) {
                currentResult = new CheckedSubtract(currentResult, mulAndDiv());
            } else {
                return currentResult;
            }
        }
    }

    private TripleExpression mulAndDiv() throws FormatParserException {
        TripleExpression currentResult = unary();
        while (true) {
            if (test('*')) {
                currentResult = new CheckedMultiply(currentResult, unary());
            } else if (test('/')) {
                currentResult = new CheckedDivide(currentResult, unary());
            } else {
                return currentResult;
            }
        }
    }

    private TripleExpression unary() throws FormatParserException {
        skipSpace();
        char c = curChar();
        switch (c) {
            case 'l':
            case 'h':
                return getResultFromFunction();
            case '(':
                return resultInBrackets();
            case '-':
                next();
                if (Character.isDigit(curChar())) {
                    String number = getNumber();
                    return getConst("-" + number);
                } else {
                    return new CheckedNegate(unary());
                }
            case 'x':
            case 'y':
            case 'z':
                next();
                return new Variable(String.valueOf(c));
            default:
                if (Character.isDigit(curChar())) {
                    return getConst(getNumber());
                } else {
                    throw new MissingArgumentException(curPos, getExpression());
                }
        }
    }

    public TripleExpression parse(String s) throws FormatParserException {
        curPos = 0;
        expression = s + END;
        len = expression.length();
        TripleExpression result = addAndSubtract();
        expect(END);
        return result;
    }
}