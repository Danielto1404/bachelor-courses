class VocabException(Exception):
    pass


class ItemNotInVocab(VocabException):
    def __init__(self, item_name, item):
        super().__init__(f'Given {item_name}: {item} not presented in vocab')


class IndexNotInVocab(ItemNotInVocab):
    def __init__(self, index):
        super(IndexNotInVocab, self).__init__('index', index)


class TokenNotInVocab(ItemNotInVocab):
    def __init__(self, token):
        super(TokenNotInVocab, self).__init__('token', token)


class VocabTypesException(VocabException):
    def __init__(self, expected_type, got_type):
        super(VocabTypesException, self) \
            .__init__(f'Unknown type exception, expected {expected_type} | got: {got_type}')
