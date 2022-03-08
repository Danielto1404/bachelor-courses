import torch
from torch.utils.data import DataLoader, TensorDataset

from nlp.vocabs import Vocab


class TextDataloader:
    def __init__(self,
                 text: str,
                 vocab: Vocab,
                 seqlen: int = 20,
                 batch_size: int = 32,
                 shuffle: bool = True):
        self.vocab = vocab
        self.seqlen = seqlen
        self.batch_size = batch_size
        self.shuffle = shuffle

        self.text, self.sources, self.targets = self.__preprocess__(text)

    def __preprocess__(self, text):
        num_seq = len(text) // self.seqlen
        assert num_seq > 0, 'Input text must be longer then sequence length'
        text = text[:num_seq * self.seqlen]
        encoded = self.vocab.encode(text)
        encoded = torch.tensor(encoded)
        sources = encoded.view(-1, self.seqlen)[:-1]
        targets = encoded[1:-self.seqlen + 1].view(-1, self.seqlen)
        return text, sources, targets

    def get_torch_loader(self):
        return DataLoader(dataset=TensorDataset(self.sources, self.targets),
                          batch_size=self.batch_size,
                          shuffle=self.shuffle)
