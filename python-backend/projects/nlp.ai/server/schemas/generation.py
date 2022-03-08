import torch
from pydantic.dataclasses import dataclass

from nlp.file_manager import generation_pretrained_path
from nlp.generation.evaluate import TextGenerator
from nlp.generation.models import CharLSTM
from nlp.generation.trainers import CharLSTMTrainer
from nlp.loaders import TextDataloader
from nlp.vocabs import CharVocab


# Evaluation arguments
@dataclass
class TextGenerationConfiguration:
    prefix: str = 'Здравствуй!'
    text_length: int = 100
    context_length: int = 20


@dataclass
class EvaluationConfig:
    model_id: int
    arguments: TextGenerationConfiguration = TextGenerationConfiguration()


# Train arguments
@dataclass
class TrainerConfiguration:
    epochs: int = 1
    seqlen: int = 20
    learning_rate: float = 1e-3
    weights_decay: float = 1e-3
    batch_size: int = 32
    shuffle: bool = True


@dataclass
class ModelTrainConfiguration:
    embeddings_size: int = 64
    hidden_size: int = 64
    layers: int = 1
    dropout: float = 0.3
    bidirectional: bool = False


@dataclass
class TrainingConfig:
    text: str
    train_config: TrainerConfiguration = TrainerConfiguration()
    model_config: ModelTrainConfiguration = ModelTrainConfiguration()


def run_evaluator(config: EvaluationConfig) -> str:
    """
    Generates text according to given configuration parameters.

    :param config: evaluation configuration params
    :return: generated text
    """
    generator = TextGenerator(
        model_path=generation_pretrained_path / f'model_{config.model_id}.pt',
        vocab_path=generation_pretrained_path / f'vocab_{config.model_id}.vocab'
    )

    text = generator.generate(
        prefix=config.arguments.prefix,
        text_length=config.arguments.text_length,
        context_length=config.arguments.context_length
    )

    return text


# TODO: - Decide how to notify user about progress while model is training.
def run_trainer(config: TrainingConfig):
    """
    Trains CharLSTM model with given training parameters.

    param: config: **TrainingConfig**
        training parameters.
    return: (vocab, model, trainer, text_dataloader)
        vocab: **CharVocab**
            created char vocabulary from given text chars.
        model: **CharLSTM**
            model trained on given text corpus.
        trainer : **CharLSTMTrainer**
            trainer instance which was used for model training.
        text_dataloader : **TextDataloader**
            dataloader which was used as train data iterator.
    """
    text = config.text

    vocab = CharVocab(chars=text, add_special_tokens=False)

    train_config = config.train_config
    model_config = config.model_config

    model = CharLSTM(
        vocab_size=len(vocab),
        embeddings_size=model_config.embeddings_size,
        hidden_size=model_config.hidden_size,
        layers=model_config.layers,
        dropout=model_config.dropout,
        bidirectional=model_config.bidirectional
    )

    trainer = CharLSTMTrainer(
        model=model,
        vocab_size=len(vocab),
        epochs=train_config.epochs,
        learning_rate=train_config.learning_rate,
        weights_decay=train_config.weights_decay,
    )

    text_dataloader = TextDataloader(
        text=text,
        vocab=vocab,
        seqlen=train_config.seqlen,
        batch_size=train_config.batch_size,
        shuffle=train_config.shuffle,
    )

    trainer.train(text_dataloader.get_torch_loader())

    # Hardcoded by now
    vocab.save(generation_pretrained_path / f'vocab_1.vocab')
    torch.save(model, generation_pretrained_path / f'model_1.pt')

    return vocab, model, trainer, text_dataloader
