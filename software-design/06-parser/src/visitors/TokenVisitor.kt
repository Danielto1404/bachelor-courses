package visitors

import tokens.NumberToken
import tokens.Brace
import tokens.Operation

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: Brace)
    fun visit(token: Operation)
}