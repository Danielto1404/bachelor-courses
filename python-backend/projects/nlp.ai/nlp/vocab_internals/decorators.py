def get_item_or_exception(exception):
    def getter(func):
        def error_raiser(self, item):
            try:
                return func(self, item)
            except KeyError:
                raise exception(item)

        return error_raiser

    return getter
