package tokens

import visitors.TokenVisitor

enum class Brace : Token {
    LEFT, RIGHT;

    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
    override fun toString() = when (this) {
        LEFT -> "("
        RIGHT -> ")"
    }
}

val charToBraceType = mapOf(
        '(' to Brace.LEFT,
        ')' to Brace.RIGHT
)
