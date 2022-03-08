import enum


class Homepage(enum.Enum):
    WELCOME = 'Welcome to nlp.ai website. Train, Run & Explore best NLP models.'


class Generation(enum.Enum):
    WELCOME = 'Here you can run your pre trained models for text generation or train a model with your text corpus.'


class Models(enum.Enum):
    WELCOME = 'Here is list of models that you can read about'


class Dialog(enum.Enum):
    WELCOME = 'Here you can test out our dialog pretrained models.'
