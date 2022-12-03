from typing import Dict, Callable, Hashable

from lrucache.interfaces import Cache, LinkedList
from lrucache.linked_list import DoublyLinkedList, QueryData, Node


class LRUCache(Cache):
    """
    LRUCache represents a **Least Recently Used Cache** data structure.
    """

    def __init__(self, capacity: int = 128):
        """
        :param capacity: capacity of LRU Cache storage.
        """
        assert isinstance(capacity, int), 'Capacity must be an integer'
        assert capacity > 0, 'Capacity must be greater than 0'

        self.capacity: int = capacity
        self._queries: Dict = {}
        self._storage: LinkedList = DoublyLinkedList(capacity=self.capacity)

    @property
    def size(self):
        return self._storage.size

    def get(self, query, retriever: Callable):
        assert isinstance(query, Hashable), f'Query type: {type(query)} must implement __hash__ method'
        assert isinstance(retriever, Callable), \
            f'Retriever must be a callable function with one argument: unexpected type {type(retriever)} '
        node = self._queries.get(
            query,
            Node(QueryData(query=query, response=retriever(query)))
        )
        removed_data = self._storage.push(node)
        if removed_data:
            self._queries.pop(removed_data.query)
        self._queries[query] = node
        return node.data.response

    def __contains__(self, query):
        return query in self._queries

    def __repr__(self):
        return f'{self.__class__.__name__}(capacity={self.capacity})'
