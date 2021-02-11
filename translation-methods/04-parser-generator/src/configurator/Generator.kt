package configurator

import generators.LexerGenerator
import generators.ParserGenerator

fun main() {

    val python = "src/test/python/python-function.king"
    val pythonNotLL1 = "src/test/python/PythonNotLL1.king"
    val calculator = "src/test/calculator/calculator.king"

//    generate(pythonNotLL1) // Not LL1 grammar
    generate(python)
    generate(calculator)
}

private fun generate(grammarPath: String) {
    configureGrammar(grammarPath)?.let {
        ParserGenerator(it).generate()
        LexerGenerator(it).generate()
    } ?: throw EmptyGrammarException(grammarPath)
}
