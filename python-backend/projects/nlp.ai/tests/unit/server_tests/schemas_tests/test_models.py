import unittest

from server.schemas.models import *


class ModelTest(unittest.TestCase):
    def setUp(self):
        self.models = models()

    def test_get_model_by_index_method(self):
        for i in range(len(self.models)):
            self.assertEqual(get_model(i), self.models[i])

    @unittest.expectedFailure
    def test_get_model_by_invalid_index(self):
        for i in range(-10, 0):
            get_model(i)

    def test_get_model_name_method(self):
        for i, model in enumerate(self.models):
            self.assertEqual(get_model_name(i), model.name)

    @unittest.expectedFailure
    def test_get_model_name_by_invalid_index(self):
        for i in range(-10, 0):
            get_model_name(i)

    def test_get_model_descriptions_method(self):
        for i, model in enumerate(self.models):
            self.assertEqual(get_model_descriptions(i), model.descriptions)

    @unittest.expectedFailure
    def test_get_model_description_by_invalid_index(self):
        for i in range(-10, 0):
            get_model_descriptions(i)

    def test_adding_model_method(self):
        model = Model(name='test name',
                      descriptions=[ModelDescription(header='large model', text='finally i am gonna be added')])
        add_model(model)
        self.assertEqual(model, self.models[-1])

    def test_add_description_method(self):
        desc = ModelDescription(header='header', text='text')
        for i in range(len(self.models)):
            add_description(i, desc)

        for model in self.models:
            self.assertIn(desc, model.descriptions)
