package visitors

import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import tokens.Token

class ExpressionPrintVisitor : TokenVisitor {
    private fun wsPrint(token: Token) = print("$token ")
    override fun visit(token: NumberToken) = wsPrint(token)
    override fun visit(token: Brace) = wsPrint(token)
    override fun visit(token: Operation) = wsPrint(token)
}