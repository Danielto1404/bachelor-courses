### LRU Cache python implementation.

#### Build on DoublyLinkedList and HashMap

#### Example of usage.

```python
from lrucache.lru_cache import LRUCache

database = Database()


def find_age_by_username(username: str):
    """
    Simple database finder
    """
    database.find_all(filter_by=username).age


# You can now use LRUCache for most common queries to store responses.
lru_cache = LRUCache(capacity=1024)

responses = []

queries = [
    "Daniil Korolev",
    "Oleg Adamov",
    "Pavel Pelikhov",
    "Peter the Great"
]

for query in queries:
    response = lru_cache.get(query=query,
                             retriever=find_age_by_username)

    responses.append(response)
```
