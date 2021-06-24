from core.text.vocab import CharVocabulary
#
#
# class SentenceDataset:
#     def __init__(self, sentence, vocab: CharVocabulary):
#         self.vocab = vocab
#
#         self.src = [vocab.START_TOKEN] + sentence
#         self.tgs = sentence + [vocab.END_TOKEN]
#
#     def __getitem__(self, index):
#         source = self.src[index]
#         target = self.tgs[index]
#     def __len__(self):
#         return len(self.src)
#
# class TextDataset:
