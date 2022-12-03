package tokens

import visitors.TokenVisitor
import java.util.function.BinaryOperator
import java.util.function.DoubleBinaryOperator

enum class Operation : BinaryOperator<Double>, DoubleBinaryOperator, Token {
    ADD {
        override fun apply(lhs: Double, rhs: Double): Double = lhs + rhs
    },
    SUBTRACT {
        override fun apply(lhs: Double, rhs: Double): Double = lhs - rhs
    },
    MULTIPLY {
        override fun apply(lhs: Double, rhs: Double): Double = lhs * rhs
    },
    DIVIDE {
        override fun apply(lhs: Double, rhs: Double): Double = lhs / rhs
    };

    override fun applyAsDouble(lhs: Double, rhs: Double) = apply(lhs, rhs)
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
    override fun toString(): String = when (this) {
        ADD -> "+"
        SUBTRACT -> "-"
        MULTIPLY -> "*"
        DIVIDE -> "/"
    }

    fun priority(): Int = when (this) {
        ADD, SUBTRACT -> 1
        MULTIPLY, DIVIDE -> 2
    }
}

val charToOperation = mapOf(
        '+' to Operation.ADD,
        '-' to Operation.SUBTRACT,
        '*' to Operation.MULTIPLY,
        '/' to Operation.DIVIDE
)
