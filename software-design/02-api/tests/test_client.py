import random
import unittest
from unittest.mock import patch, MagicMock

from src.http.client import VkClient
from src.posts import VKPostsCounterManager, VKPostsSearchParameters


def gen_items(n):
    return [random.choice('ABCDEFGHIJKLMNO') for _ in range(n)]


def gen_response(total_count):
    return {
        'total_count': total_count,
        'items': gen_items(total_count)
    }


class StubClient(unittest.TestCase):

    def setUp(self):
        self.client = VkClient(api_token='magic_api_token')
        self.counter = VKPostsCounterManager(self.client)

    @patch('src.http.client.VkClient.request')
    def test_response_length(self, mock_request: MagicMock):
        response = self.counter.count_request(VKPostsSearchParameters(query='test', hours_range=4))
        self.assertEqual(len(response), 4)
        mock_request.assert_called()

    @patch('src.http.client.VkClient.request', side_effect=[
        gen_response(20), gen_response(30), gen_response(40), gen_response(50)
    ])
    def test_response_values(self, mock_request: MagicMock):
        response = self.counter.count_request(VKPostsSearchParameters(query='test', hours_range=4))
        self.assertEqual(len(response), 4)
        self.assertEqual(response, [20, 30, 40, 50])
        mock_request.assert_called()

    @patch('src.http.client.VkClient.request', side_effect=[
        gen_response(20), gen_response(30), gen_response(40), gen_response(50), {'items': gen_items(60)}
    ])
    @unittest.expectedFailure
    def test_has_no_total_count(self, mock_request: MagicMock):
        response = self.counter.count_request(VKPostsSearchParameters(query='test', hours_range=5))
        mock_request.assert_called()
