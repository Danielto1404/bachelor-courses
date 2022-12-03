import argparse

from config import config
from src.http.client import VkClient
from src.posts import VKPostsSearchParameters, VKPostsCounterManager

ap = argparse.ArgumentParser()
ap.add_argument('-q', '--query', required=True, help='hash tag name to find')
ap.add_argument('-hr', '--hours_range', required=False, type=int, default=1, help='amount of search hours')

args = vars(ap.parse_args())

client = VkClient(api_token=config['client']['api_token'])
params = VKPostsSearchParameters(query=args['query'], hours_range=args['hours_range'])
counter = VKPostsCounterManager(client=client)
counts = counter.count_request(params=params)
print(counts)
