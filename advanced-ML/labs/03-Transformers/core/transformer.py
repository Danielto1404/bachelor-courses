import torch
import torch.nn as nn
from tqdm import trange

from core.text.loader import data, vv


class Transformer(nn.Module):
    def __init__(self, epochs):
        super().__init__()

        self._encoder = nn.TransformerEncoder(
            encoder_layer=nn.TransformerEncoderLayer(d_model=4, nhead=4),
            num_layers=3
        )

        self._epochs = epochs
        self._optimizer = torch.optim.Adam(self._encoder.parameters(), lr=1e-3)
        self._criterion = nn.CrossEntropyLoss()

    def forward(self, x):
        # SEQUENCE_LENGTH x BATCH_SIZE x EMBEDDING_SIZE
        return self._encoder(x)

    def learn(self, char2char, batch_size=32):
        # TODO:- Fix data
        loader = data
        progress = trange(self._epochs, desc='Epoch')
        for epoch in progress:
            for source_sentence, target_sentence in loader:
                self._optimizer.zero_grad()
                predictions = self.forward(source_sentence).squeeze()
                error = self._criterion(predictions, target_sentence)
                error.backward()
                self._optimizer.step()

                progress.set_postfix_str('err: {}'.format(error.detach().item()))


# tt = Transformer(epochs=400)

# tt.learn('')
#
# xx = torch.tensor([
#     # [[]],
#     [[0, 0, 1, 0]]
# ]).float()
#
# re = tt.forward(xx)
# print(re)
# print(re.argmax())
# print(vv.vocab)
# print(vv.index2char(re.argmax().item()))
# print(vv.index2char(2))
