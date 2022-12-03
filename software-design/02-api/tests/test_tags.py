import unittest

from src.posts import VKPostsSearchParameters
from src.time_utils import valid_search_hours_range


class TestTags(unittest.TestCase):
    @unittest.expectedFailure
    def test_negative_hours_fails(self):
        return VKPostsSearchParameters(query='CT.ITMO', hours_range=-1)

    @unittest.expectedFailure
    def test_zero_hours_fails(self):
        return VKPostsSearchParameters(query='CT.ITMO', hours_range=0)

    @unittest.expectedFailure
    def test_hours_not_integer_fails(self):
        return VKPostsSearchParameters(query='CT.ITMO', hours_range=2.3)

    @unittest.expectedFailure
    def test_hours_not_in_valid_range_fails(self):
        return VKPostsSearchParameters(query='CT.ITMO', hours_range=239)

    def test_accepts_only_one_day_hours_range(self):
        return [VKPostsSearchParameters(query='CT.ITMO', hours_range=i) for i in valid_search_hours_range()]
