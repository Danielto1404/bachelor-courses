package parsers

import tokenizers.ArithmeticTokenizerException
import tokens.*

abstract class State(val inputString: String,
                     var position: Int) {

    val symbol: Char
        get() = inputString[position]

    abstract fun next(tokens: MutableList<Token>): State

    open fun whitespaceOrEnd(): State? = when {
        position >= inputString.length -> End(inputString, position)
        Character.isWhitespace(symbol) -> Whitespace(inputString, position)
        else -> null
    }
}

class Beginning(input: String, position: Int) : State(input, position) {

    override fun next(tokens: MutableList<Token>): State {
        whitespaceOrEnd()?.let { return it }

        val token = when (symbol) {
            in '0'..'9' -> return Number(inputString, position)
            in charToBraceType.keys -> charToBraceType[symbol]
            in charToOperation.keys -> charToOperation[symbol]
            else -> throw ArithmeticTokenizerException("Invalid char '$symbol'")
        }
        token?.let { tokens.add(it) }
        position++
        return this
    }
}

class End(input: String, position: Int) : State(input, position) {
    override fun next(tokens: MutableList<Token>) = this
}

class Number(input: String, position: Int) : State(input, position) {

    private var stringNumber = String()
    private fun parseNumber() = Integer.parseInt(stringNumber)

    override fun next(tokens: MutableList<Token>): State {
        val checked = whitespaceOrEnd()
        return when {
            checked != null -> {
                tokens.add(NumberToken(parseNumber()))
                checked
            }
            !Character.isDigit(symbol) -> {
                tokens.add(NumberToken(parseNumber()))
                Beginning(inputString, position)
            }
            else -> {
                val res = Number(inputString, position + 1)
                res.stringNumber += stringNumber + symbol
                res
            }
        }
    }
}

class Whitespace(input: String, position: Int) : State(input, position) {
    override fun next(tokens: MutableList<Token>): State = when {
        position >= inputString.length -> End(inputString, position)
        Character.isWhitespace(symbol) -> {
            position++
            this
        }
        else -> if (Character.isDigit(symbol)) Number(inputString, position) else Beginning(inputString, position)
    }
}