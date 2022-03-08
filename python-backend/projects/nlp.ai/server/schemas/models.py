from typing import List, Any

from pydantic.dataclasses import dataclass

__all__ = [
    'models', 'ModelDescription', 'Model',
    'add_model', 'add_description', 'get_root',
    'get_model', 'get_model_descriptions', 'get_model_name',
]

from pydantic.main import BaseModel

from server.constants.text import Models


@dataclass
class ModelDescription:
    """
    Representation of single model description
    """
    header: str
    text: str

@dataclass
class Model:
    name: str
    descriptions: List[ModelDescription]
    #
    # def __init__(self, name: str, descriptions: List[ModelDescription], **data: Any):
    #     super().__init__(**data)
    #     self.name = name
    #     self.descriptions = descriptions


_models = [
    Model('bert', [ModelDescription('Hi i am huge transformer model', '')]),
    Model('elmo', [ModelDescription('Hi i am bidirectional recurrent context based neural network', '')]),
    Model('word2vec', [ModelDescription('Hi i am word context based neural network', '')]),
    Model('tf-dif', [ModelDescription('Hi i am simple counter', '')]),
    Model('gpt-3', [ModelDescription('Hi i am the best model with 175 billion parameters', '')])
]


def get_root():
    return Models.WELCOME


def models():
    return _models


def get_model(index):
    return _models[index]


def get_model_descriptions(index):
    return _models[index].descriptions


def get_model_name(index):
    return _models[index].name


def add_model(model: Model):
    _models.append(model)


def add_description(index, description: ModelDescription):
    _models[index].descriptions.append(description)
