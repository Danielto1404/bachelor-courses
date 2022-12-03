import unittest
from datetime import datetime

from src.time_utils import get_time_ranges, int_timestamp


class TimeUtilsTest(unittest.TestCase):
    def setUp(self):
        # 1 september 2021 13:30
        self.now = int_timestamp(
            datetime(year=2021, month=9, day=1, hour=13, minute=30)
        )

    def test_time_range_splits_correctly_hours(self):
        now = int_timestamp(datetime(year=2021, month=9, day=1, hour=13, minute=30))
        got_times = get_time_ranges(search_hours=4, now=now)
        expected_time = [
            (
                int_timestamp(datetime(year=2021, month=9, day=1, hour=12, minute=30)),
                int_timestamp(datetime(year=2021, month=9, day=1, hour=13, minute=30))
            ),
            (
                int_timestamp(datetime(year=2021, month=9, day=1, hour=11, minute=30)),
                int_timestamp(datetime(year=2021, month=9, day=1, hour=12, minute=30))
            ),
            (
                int_timestamp(datetime(year=2021, month=9, day=1, hour=10, minute=30)),
                int_timestamp(datetime(year=2021, month=9, day=1, hour=11, minute=30))
            ),
            (
                int_timestamp(datetime(year=2021, month=9, day=1, hour=9, minute=30)),
                int_timestamp(datetime(year=2021, month=9, day=1, hour=10, minute=30))
            ),
        ]

        self.assertEqual(expected_time, got_times)

    def test_time_splits_correctly_zero_hours(self):
        self.assertEqual([], get_time_ranges(search_hours=0, now=self.now))
