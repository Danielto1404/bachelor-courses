import re


class Tokenizer:
    def __init__(self, words_pattern, sentence_pattern):
        self.word_pattern = re.compile(words_pattern)
        self.sentence_pattern = re.compile(sentence_pattern)

    def split_chars(self, sentences):
        pass