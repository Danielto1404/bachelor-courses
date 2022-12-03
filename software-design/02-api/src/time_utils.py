from datetime import timedelta, datetime


def unix_time():
    """
    Returns current Unix time as an integer value.
    """
    return int_timestamp(datetime.now())


def int_timestamp(date: datetime):
    """
    Returns timestamp for given datetime object.
    """
    return int(date.timestamp())


def hours_delta_in_seconds(hours):
    """
    Return total seconds in given number of hours as an integer value.
    """
    return int(timedelta(hours=hours).total_seconds())


def get_time_ranges(search_hours: int, now=unix_time()):
    """
    :param now: Unix time, default is datetime.now()
    :param search_hours: hours to search.
    """
    return [(now - hours_delta_in_seconds(hour + 1), now - hours_delta_in_seconds(hour))
            for hour in range(search_hours)]


def valid_search_hours_range():
    """
    Returns a list of available clocks to collect information from the vk api.
    """
    return list(range(1, 25))
