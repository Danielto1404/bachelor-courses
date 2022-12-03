package tokenizers

import parsers.Beginning
import parsers.End
import parsers.State
import tokens.Token

class ArithmeticTokenizerException(message: String) : RuntimeException(message)

class ArithmeticExpressionTokenizer(input: String) : Tokenizer {
    private var state: State = Beginning(input, 0)

    override fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()
        while (state !is End) {
            state = state.next(tokens)
        }
        return tokens
    }
}