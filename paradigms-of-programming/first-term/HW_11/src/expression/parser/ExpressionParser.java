package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {
    private String expression;
    private String name;
    private int currentIndex = 0;
    private int value = 0;

    private enum Token {
        ADD, AND, CLOSE_BRACE, CONST, COUNT, DIV, BEGIN, ERR, MUL, NOT,
        NEGATE, OPEN_BRACE, OR, SUB, VAR, XOR, SHIFT_LEFT, SHIFT_RIGHT
    }

    private Token currentToken = Token.ERR;

    private void skipSpaces() {
        while (currentIndex < expression.length() && Character.isWhitespace(expression.charAt(currentIndex))) {
            ++currentIndex;
        }
    }

    private void nextToken() {
        skipSpaces();
        if (currentIndex >= expression.length()) {
            currentToken = Token.BEGIN;
            return;
        }

        char c = expression.charAt(currentIndex);
        switch (expression.charAt(currentIndex)) {
            case '+':
                currentToken = Token.ADD;
                break;
            case '-':
                if (currentToken == Token.CONST || currentToken == Token.VAR || currentToken == Token.CLOSE_BRACE) {
                    currentToken = Token.SUB;
                } else {
                    currentToken = Token.NEGATE;
                }
                break;
            case '/':
                currentToken = Token.DIV;
                break;
            case '*':
                currentToken = Token.MUL;
                break;
            case '(':
                currentToken = Token.OPEN_BRACE;
                break;
            case ')':
                currentToken = Token.CLOSE_BRACE;
                break;
            case '&':
                currentToken = Token.AND;
                break;
            case '|':
                currentToken = Token.OR;
                break;
            case '^':
                currentToken = Token.XOR;
                break;
            case '~':
                currentToken = Token.NOT;
                break;
            default:
                if (Character.isDigit(c)) {
                    int leftBorder = currentIndex;
                    while (currentIndex < expression.length() && Character.isDigit(expression.charAt(currentIndex))) {
                        ++currentIndex;
                    }

                    value = Integer.parseUnsignedInt(expression.substring(leftBorder, currentIndex));
                    currentToken = Token.CONST;
                    --currentIndex;
                } else if (currentIndex + 2 <= expression.length() && expression.substring(currentIndex, currentIndex + 2).equals("<<")) {
                    currentIndex += 2;
                    currentToken = Token.SHIFT_LEFT;
                } else if (currentIndex + 2 <= expression.length() && expression.substring(currentIndex, currentIndex + 2).equals(">>")) {
                    currentIndex += 2;
                    currentToken = Token.SHIFT_RIGHT;
                } else if (currentIndex + 5 <= expression.length() && expression.substring(currentIndex, currentIndex + 5).equals("count")) {
                    currentIndex += 4;
                    currentToken = Token.COUNT;
                } else if (Character.isLetter(expression.charAt(currentIndex))) {
                    int leftBorder = currentIndex;
                    while (currentIndex < expression.length() && Character.isLetter(expression.charAt(currentIndex))) {
                        ++currentIndex;
                    }

                    name = expression.substring(leftBorder, currentIndex);
                    currentToken = Token.VAR;
                    --currentIndex;
                } else {
                    currentToken = Token.ERR;
                }
        }
        ++currentIndex;
    }

    private TripleExpression unary() {
        nextToken();
        TripleExpression res;

        switch (currentToken) {
            case CONST:
                res = new Const(value);
                nextToken();
                break;
            case VAR:
                res = new Variable(name);
                nextToken();
                break;
            case NEGATE:
                res = new Negate(unary());
                break;
            case NOT:
                res = new Not(unary());
                break;
            case COUNT:
                res = new Count(unary());
                break;
            case OPEN_BRACE:
                res = shifts();
                nextToken();
                break;
            default:
                return new Const(0);
        }
        return res;
    }

    private TripleExpression mulAndDiv() {
        TripleExpression res = unary();
        while (true) {
            switch (currentToken) {
                case MUL:
                    res = new Multiply(res, unary());
                    break;
                case DIV:
                    res = new Divide(res, unary());
                    break;
                default:
                    return res;
            }
        }
    }

    private TripleExpression addAndSub() {
        TripleExpression res = mulAndDiv();
        while (true) {
            switch (currentToken) {
                case ADD:
                    res = new Add(res, mulAndDiv());
                    break;
                case SUB:
                    res = new Subtract(res, mulAndDiv());
                    break;
                default:
                    return res;
            }
        }
    }

    private TripleExpression and() {
        TripleExpression res = addAndSub();
        while (true) {
            if (currentToken == Token.AND) {
                res = new And(res, and());
            } else {
                return res;
            }
        }
    }

    private TripleExpression xor() {
        TripleExpression res = and();
        while (true) {
            if (currentToken == Token.XOR) {
                res = new Xor(res, and());
            } else {
                return res;
            }
        }
    }

    private TripleExpression or() {
        TripleExpression res = xor();
        while (true) {
            if (currentToken == Token.OR) {
                res = new Or(res, xor());
            } else {
                return res;
            }
        }
    }

    private TripleExpression shifts() {
        TripleExpression res = or();
        while (true) {
            if (currentToken == Token.SHIFT_LEFT) {
                res = new ShiftLeft(res, or());
            } else if (currentToken == Token.SHIFT_RIGHT) {
                res = new ShiftRight(res, or());
            } else {
                return res;
            }
        }
    }

    public TripleExpression parse(final String expression) {
        assert expression != null : "Expression is null";
        currentIndex = 0;
        this.expression = expression;
        currentToken = Token.ERR;
        return shifts();
    }
}
