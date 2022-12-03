from abc import abstractmethod
from typing import Optional

from pydantic import Field
from pydantic.main import BaseModel


class VKRequest:
    @abstractmethod
    def body(self) -> dict:
        """
        Returns dictionary of request parameters.
        """
        raise NotImplementedError()

    @abstractmethod
    def endpoint(self) -> str:
        """
        Returns string that is gonna be used as url-address for method in request.
        """
        raise NotImplementedError()


class VKNewsFeedSearchRequest(VKRequest, BaseModel):
    # q is VkApi query parameter
    q: str = Field(alias='query')
    start_time: Optional[int] = None
    end_time: Optional[int] = None
    count: Optional[int] = None

    def body(self) -> dict:
        return self.dict()

    def endpoint(self) -> str:
        return 'newsfeed.search'
