import torch.nn as nn


class CharLSTM(nn.Module):

    def __init__(self,
                 vocab_size: int,
                 embeddings_size: int,
                 hidden_size: int,
                 layers: int = 1,
                 dropout: float = 0.3,
                 bidirectional: bool = False):
        super().__init__()
        self.embeddings = nn.Embedding(vocab_size, embeddings_size)
        self.lstm_cells = nn.LSTM(input_size=embeddings_size,
                                  hidden_size=hidden_size,
                                  num_layers=layers,
                                  dropout=dropout,
                                  bidirectional=bidirectional)
        self.projection = nn.Linear(2 * hidden_size if bidirectional else hidden_size, vocab_size)
        self.bidirectional = bidirectional

    def forward(self, input_ids):
        embeddings = self.embeddings(input_ids)
        outputs, _ = self.lstm_cells(embeddings)
        logits = self.projection(outputs)
        return logits
