package parser;


import exceptions.FormatParserException;
import expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */

public interface GenericParser<T> {
    TripleExpression<T> parse(String expression) throws FormatParserException;
}