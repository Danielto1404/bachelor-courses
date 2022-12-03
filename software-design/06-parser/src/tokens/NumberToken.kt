package tokens

import visitors.TokenVisitor

class NumberToken(val value: Int) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
    override fun toString() = value.toString()
}