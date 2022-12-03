package visitors

import parsers.ExpressionParserException
import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import java.util.*

class CalculationException(message: String) : RuntimeException(message)

class ExpressionCalcVisitor : TokenVisitor {
    private val stack = ArrayDeque<Double>()
    override fun visit(token: NumberToken) = stack.addFirst(token.value.toDouble())
    override fun visit(token: Brace) = throw ExpressionParserException("Invalid position for brace: $token")
    override fun visit(token: Operation) {
        if (stack.size < 2) {
            throw CalculationException("Invalid position for operation: $token")
        }
        val rhs = stack.removeFirst()
        val lhs = stack.removeFirst()
        stack.addFirst(token.applyAsDouble(lhs, rhs))
    }

    fun getResult(): Double {
        if (stack.size != 1) {
            throw CalculationException("Stack size != 1 at the final stage, please check correctness of expression")
        }
        return stack.removeFirst()
    }
}