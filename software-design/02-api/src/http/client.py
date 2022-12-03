import vk_api

from src.http.requests import VKRequest


class VkClient:
    def __init__(self, api_token):
        """
        Instantiate vk api session.
        """
        self.session = vk_api.VkApi(token=api_token)

    def request(self, request: VKRequest):
        """
        Performs network requests with given parameters
        """
        body = request.body()
        endpoint = request.endpoint()
        return self.session.method(endpoint, body)
