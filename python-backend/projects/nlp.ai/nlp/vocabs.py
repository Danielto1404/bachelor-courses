import pickle
from pathlib import Path
from typing import Iterable, Union

from nlp.vocab_internals.decorators import get_item_or_exception
from nlp.vocab_internals.exceptions import VocabTypesException, IndexNotInVocab, TokenNotInVocab

__all__ = [
    'Vocab', 'CharVocab'
]


class Vocab:
    def __init__(self, add_special_tokens: bool = True):
        self._index2token = {}
        self._token2index = {}

        if add_special_tokens:
            self._special_tokens = [self.EOS, self.SOS]
            for index, token in enumerate(self.special_tokens):
                self._index2token[index] = token
                self._token2index[token] = index
        else:
            self._special_tokens = []

    def save(self, path: Union[str, Path]):
        """
        Saves vocabulary state.
        """
        with open(path, 'wb') as vocab_stream:
            pickle.dump(self, vocab_stream)

    @staticmethod
    def load(path: Union[str, Path]):
        """
        Loads predefined vocabulary.
        """
        with open(path, 'rb') as vocab_stream:
            return pickle.load(vocab_stream)

    @property
    def EOS(self) -> str:
        """
        End of sentence token
        :return:
        """
        return '<EOS>'

    @property
    def SOS(self) -> str:
        """
        Start of sentence token
        :return:
        """
        return '<SOS>'

    @property
    def special_tokens(self) -> list:
        """
        :return: List of special tokens.
        """
        return self._special_tokens

    @get_item_or_exception(exception=IndexNotInVocab)
    def index2token(self, index):
        return self._index2token[index]

    @get_item_or_exception(exception=TokenNotInVocab)
    def token2index(self, token):
        return self._token2index[token]

    def encode(self, tokens):
        return [self.token2index(token) for token in tokens]

    def decode(self, indices, to_str=False):
        decoded = [self.index2token(index) for index in indices]
        return ''.join(decoded) if to_str else decoded

    def __len__(self):
        assert len(self._index2token) == len(self._token2index)
        return len(self._index2token) + len(self.special_tokens)

    def __contains__(self, token):
        return token in self._token2index


class CharVocab(Vocab):
    def __init__(self, chars, *args, **kwargs):
        super().__init__(*args, **kwargs)
        if isinstance(chars, set):
            self.chars = chars
        elif isinstance(chars, Iterable):
            self.chars = set(chars)
        else:
            raise VocabTypesException(expected_type='iterable', got_type=type(chars))

        index_offset = len(self._index2token)
        self._index2token = {**self._index2token, **{i + index_offset: c for i, c in enumerate(self.chars)}}
        self._token2index = {**self._token2index, **{c: i + index_offset for i, c in enumerate(self.chars)}}
