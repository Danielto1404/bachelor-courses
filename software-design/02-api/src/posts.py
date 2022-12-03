from pydantic import BaseModel, Field, validator

from src.http.client import VkClient
from src.http.requests import VKNewsFeedSearchRequest
from src.time_utils import get_time_ranges


class VKSearchQuery(BaseModel):
    query: str


class VKPostsSearchParameters(VKSearchQuery, BaseModel):
    """
    VK API request parameters class for finding hashtags with the specified hash tag name and search time
    """
    hours_range: int = Field(ge=1, le=24)

    @validator('hours_range', pre=True)
    def check_times_is_int(cls, v):
        assert isinstance(v, int), f'search_hours must be an integer value. Given type: {type(v)}'
        return v


class VKPostsCounterManager:
    # API documentation for newsfeed.search response: https://vk.com/dev/newsfeed.search
    def __init__(self, client: VkClient):
        self.client = client

    def count_request(self, params: VKPostsSearchParameters) -> list:
        time_ranges = get_time_ranges(params.hours_range)
        count = []
        for start_time, end_time in time_ranges:
            request_params = VKNewsFeedSearchRequest(query=params.query,
                                                     start_time=start_time,
                                                     end_time=end_time,
                                                     count=1)
            response = self.client.request(request_params)
            count.append(response['total_count'])
        return count
