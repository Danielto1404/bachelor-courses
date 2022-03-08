import random
import unittest

from server.constants.text import Models
from server.schemas.models import get_root, ModelDescription, Model, add_model, models, get_model


def random_char():
    return random.choice('ABCDEFGH')


def random_string(length):
    return ''.join([random_char() for _ in range(length)])


def random_description():
    return ModelDescription(header=random_string(10),
                            text=random_string(80))


def random_description_list(length):
    return [random_description() for _ in range(length)]


def random_model():
    return Model(name=random_string(10),
                 descriptions=random_description_list(10))


class ModelsIntegrationTest(unittest.TestCase):
    def setUp(self):
        self.models = models()

    def test_root_response(self):
        self.assertEqual(Models.WELCOME, get_root())

    def test_add_model_then_get_by_index(self):
        model = random_model()
        length = len(self.models)
        self.assertNotIn(model, self.models)
        add_model(model)
        self.assertIn(model, self.models)
        self.assertEqual(length + 1, len(self.models))

    def test_multiple_adds(self):
        amount = 100
        length = len(self.models)
        for _ in range(amount):
            model = random_model()
            self.assertNotIn(model, self.models)
            add_model(model)
            self.assertIn(model, self.models)

        self.assertEqual(length + amount, len(self.models))

    def test_multiple_add_and_get(self):
        amount = 100
        length = len(self.models)
        for i in range(amount):
            model = random_model()
            add_model(model)
            self.assertEqual(model, get_model(length + i))
