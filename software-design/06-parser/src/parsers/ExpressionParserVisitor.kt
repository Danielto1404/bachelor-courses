package parsers

import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import tokens.Token
import visitors.TokenVisitor

class ExpressionParserException(message: String) : RuntimeException(message)

class ExpressionParserVisitor : TokenVisitor {

    private val tokens = mutableListOf<Token>()

    private val stack = ArrayDeque<Token>()

    private fun <T> ArrayDeque<T>.removeWhile(predicate: (T) -> Boolean): List<T> {
        val result = takeWhile(predicate)
        repeat(result.size) { removeFirst() }
        return result
    }

    override fun visit(token: NumberToken) {
        tokens.add(token)
    }

    override fun visit(token: Brace) {
        if (token == Brace.LEFT) {
            stack.addFirst(token)
            return
        }

        tokens.addAll(stack.removeWhile { it !is Brace })
        stack.removeFirstOrNull() ?: throw ExpressionParserException("Invalid expression")
    }

    override fun visit(token: Operation) {
        tokens.addAll(stack.removeWhile { it is Operation && it.priority() >= token.priority() })
        stack.addFirst(token)
    }

    fun getPostfixExpression(): List<Token> {
        tokens.addAll(stack)
        stack.clear()
        return tokens
    }
}