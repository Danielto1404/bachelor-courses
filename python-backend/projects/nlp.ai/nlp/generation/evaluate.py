from typing import Optional

import torch

from nlp.vocabs import Vocab


def load_model(path):
    return torch.load(path, map_location='cpu')


class TextGenerator:
    def __init__(self, model_path, vocab_path):
        """
        model_path: `Union[str, Path]`
            text generation pretrained model path.
        vocab: `Vocab`
            predefined vocabulary path.
        """
        self.model = load_model(path=model_path).eval()
        self.vocab = Vocab.load(path=vocab_path)

    def generate(self,
                 prefix: str,
                 text_length: int = 100,
                 context_length: Optional[int] = None):
        """
        Generates text with given sentence begging.

        prefix: `str`
            sentence beginning of brand new generated text.
        text_length: `int`
            amount of tokens to generate.
        context_length: `Optional[int]`, default=None
            length of context that is used by the model to generate new sentence token, takes last `context_length`
            tokens to generate next token in the sentence.

        @:exception `VocabError`, `IndexError`

        :return: Generated text
        """
        text = prefix

        for _ in range(text_length):
            to_encode = text if context_length != -1 else text[-context_length:]
            input_ids = self.vocab.encode(to_encode)
            input_ids = torch.tensor([input_ids])

            with torch.no_grad():
                output = self.model(input_ids)
                last_layer = output[0][-1]
                token_id = last_layer.argmax().cpu().item()
                token = self.vocab.index2token(token_id)

                text += token

        return text
