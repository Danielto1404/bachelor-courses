import torch
import torch.nn.functional as F


class CharVocabulary:
    def __init__(self, max_size=None, tokenizer=None):
        self.max_size = max_size
        self.tokenizer = tokenizer

        self.__start = '[START]'
        self.__start_idx = 0

        self.__end = '[END]'
        self.__end_idx = 1

        self.__char2index = {
            self.__start: self.__start_idx,
            self.__end: self.__end_idx
        }

        self.__index2char = {
            self.__start_idx: self.__start,
            self.__end_idx: self.__end
        }

    def build(self, chars):
        for c in set(chars):
            index = self.__len__()
            self.__char2index[c] = index
            self.__index2char[index] = c

        return self

    @property
    def vocab(self):
        return self.__char2index

    def char2index(self, char):
        index = self.__char2index.get(char)
        if index is None:
            raise NotInVocabException('Char', char)
        return index

    def index2char(self, index):
        char = self.__index2char.get(index)
        if char is None:
            raise NotInVocabException('Index', index)
        return char

    def __one_hot_char(self, char) -> torch.Tensor:
        index = self.char2index(char)
        index = torch.Tensor([index]).long()
        return F.one_hot(index, num_classes=len(self))

    def one_hot(self, input_chars) -> torch.Tensor:
        if isinstance(input_chars, list):
            pass
            # map(_)

    @property
    def START_TOKEN(self):
        return self.__start

    @property
    def END_TOKEN(self):
        return self.__end

    __getitem__ = char2index
    __call__ = __one_hot_char

    def __len__(self):
        assert len(self.__char2index) == len(self.__index2char), """
        char2index dict length not equal to index2char dict length.
        Please rebuild vocabulary.
        """
        return len(self.__char2index)


class NotInVocabException(IndexError):
    def __init__(self, name, index):
        super().__init__('{}: "{}" not represented in vocabulary'.format(name, index))
