import torch.nn as nn
import torch.optim as optimizers

from nlp.generation.models import CharLSTM


class CharLSTMTrainer:
    def __init__(self,
                 model: CharLSTM,
                 vocab_size: int,
                 learning_rate: float = 1e-3,
                 weights_decay: float = 1e-3,
                 epochs: int = 1,
                 logging_level: int = 0):
        self.vocab_size = vocab_size
        self.logging_level = logging_level

        self.model = model.train()
        self.epochs = epochs
        self.learning_rate = learning_rate

        self._loss = nn.CrossEntropyLoss()
        self._optimizer = optimizers.Adam(self.model.parameters(), lr=self.learning_rate, weight_decay=weights_decay)

    def train(self, text_dataloader):
        for epoch in range(self.epochs):
            for input_chars, target_chars in text_dataloader:
                self._optimizer.zero_grad()
                predicted_chars = self.model(input_chars)
                loss = self._loss(predicted_chars.transpose(1, 2), target_chars)
                loss.backward()
                self._optimizer.step()
