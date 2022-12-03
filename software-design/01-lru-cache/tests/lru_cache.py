import unittest

from lrucache.lru_cache import LRUCache


def none(_):
    return None


class LRUCacheTest(unittest.TestCase):

    @staticmethod
    def filled_cache(capacity, amount_to_fill):
        """
        Fills cache with int values as keys and doubled values as values.
        :return:
        """
        cache = LRUCache(capacity=capacity)
        for i in range(amount_to_fill):
            cache.get(i, lambda x: x * 2)
        return cache

    @unittest.expectedFailure
    def test_zero_capacity(self):
        LRUCache(capacity=0)

    @unittest.expectedFailure
    def test_negative_capacity(self):
        LRUCache(capacity=-10)

    @unittest.expectedFailure
    def test_non_integer_capacity(self):
        LRUCache(capacity='10')

    def test_get_when_presented(self):
        cache = self.filled_cache(capacity=5, amount_to_fill=6)
        self.assertEqual(cache.get(1, retriever=none), 2)
        self.assertEqual(cache.get(2, retriever=none), 4)
        self.assertEqual(cache.get(3, retriever=none), 6)
        self.assertEqual(cache.get(4, retriever=none), 8)
        self.assertEqual(cache.get(5, retriever=none), 10)

    def test_get_stores_queries(self):
        cache = self.filled_cache(capacity=5, amount_to_fill=5)
        self.assertEqual(cache.get(10, retriever=lambda _: 239), 239)
        self.assertEqual(cache.get(10, retriever=lambda _: -239), 239)

    def test_remove_old_queries(self):
        cache = self.filled_cache(capacity=5, amount_to_fill=5)
        cache.get(10, retriever=lambda _: 239)
        self.assertNotIn(0, cache)
        cache.get(11, retriever=lambda _: 239)
        self.assertNotIn(1, cache)
        cache.get(10, retriever=lambda _: 239)
        self.assertIn(2, cache)
        cache.get(11, retriever=lambda _: 239)
        self.assertIn(3, cache)
        cache.get(10, retriever=lambda _: 239)

    def test_stores_no_more_then_capacity_elements(self):
        self.assertEqual(self.filled_cache(capacity=5, amount_to_fill=10).size, 5)


if __name__ == '__main__':
    unittest.main()
