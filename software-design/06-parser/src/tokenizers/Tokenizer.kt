package tokenizers

import tokens.Token

interface Tokenizer {
    fun tokenize(): List<Token>
}