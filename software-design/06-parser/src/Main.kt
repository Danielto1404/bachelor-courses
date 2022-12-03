import parsers.ExpressionParserVisitor
import tokenizers.ArithmeticExpressionTokenizer
import tokens.Token
import visitors.ExpressionCalcVisitor
import visitors.ExpressionPrintVisitor
import visitors.TokenVisitor

fun TokenVisitor.acceptAll(tokens: List<Token>): TokenVisitor {
    tokens.forEach { it.accept(this) }
    return this
}

fun main() {
    val input = readLine() ?: return

    var tokens = ArithmeticExpressionTokenizer(input).tokenize()
    println("Infix tokens order: $tokens")

    val parserVisitor = ExpressionParserVisitor()
    parserVisitor.acceptAll(tokens)
    tokens = parserVisitor.getPostfixExpression()
    println("Postfix tokens order: $tokens")

    print("PrintVisitor: ")
    ExpressionPrintVisitor().acceptAll(tokens)

    val calculationVisitor = ExpressionCalcVisitor()
    calculationVisitor.acceptAll(tokens)

    println("\nExpression result: ${calculationVisitor.getResult()}")
}