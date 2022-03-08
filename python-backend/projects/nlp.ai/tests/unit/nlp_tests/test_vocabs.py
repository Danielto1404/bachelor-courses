import unittest

from nlp.vocabs import CharVocab

ALPHABET = 'ABCDEFGH'


class CharVocabTest(unittest.TestCase):
    def setUp(self):
        self.vocab = CharVocab(chars=ALPHABET)

    def test_special_symbols_in_vocab(self):
        self.assertIn('<EOS>', self.vocab)
        self.assertIn('<SOS>', self.vocab)

    def test_given_chars_in_vocab(self):
        for char in ALPHABET:
            self.assertIn(char, self.vocab)

    def test_len_vocab_with_special_chars(self):
        self.assertEqual(len(self.vocab),
                         len(self.vocab.special_tokens) + len(self.vocab._index2token))

    def test_random_char_not_in_vocab(self):
        char = '#239#'
        self.assertNotIn(char, self.vocab)

    def test_index_to_token_equal_token_to_index(self):
        for char in ALPHABET:
            index = self.vocab.token2index(char)
            token = self.vocab.index2token(index)
            self.assertEqual(char, token)

        for char in self.vocab.special_tokens:
            index = self.vocab.token2index(char)
            token = self.vocab.index2token(index)
            self.assertEqual(char, token)

    def test_len_without_special_symbols(self):
        self.vocab = CharVocab(chars=ALPHABET, add_special_tokens=False)
        self.assertEqual(len(self.vocab), len(self.vocab._index2token))

    def test_special_tokens_not_in_vocab(self):
        self.vocab = CharVocab(chars=ALPHABET, add_special_tokens=False)
        self.assertEqual([], self.vocab.special_tokens)

    def test_encode_decode_bijection(self):
        text = 'ABC'
        encoded = self.vocab.encode(text)
        decoded = self.vocab.decode(encoded, to_str=True)
        self.assertEqual(text, decoded)

    @unittest.expectedFailure
    def test_encode_char_not_presented(self):
        text = '#239#'
        self.vocab.encode(text)

    @unittest.expectedFailure
    def test_decode_index_not_presented(self):
        indices = [0, 1, 2, 1000]
        self.vocab.decode(indices)
