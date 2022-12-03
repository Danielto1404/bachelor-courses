from abc import abstractmethod
from typing import Callable


class Sized:

    @property
    @abstractmethod
    def size(self):
        raise NotImplementedError

    def isEmpty(self):
        """Checks whether size == 0"""
        return self.size == 0

    def __len__(self):
        """
        Analog of the size method to `.size`
        """
        return self.size


class Cache(Sized):

    @abstractmethod
    def get(self, key, retriever: Callable):
        """
        Tries to get data from cache storage, puts data from thre retriever function if given key isn't contains in
        cache storage.

        :param key: query key
        :param retriever: function that somehow retrieves the data by the given key
        :return: result of retriever function
        """
        raise NotImplementedError

    @abstractmethod
    def __contains__(self, query):
        """
        Checks whether query contains in LRUCache storage.
        """
        raise NotImplementedError


class LinkedList(Sized):

    @abstractmethod
    def pop(self):
        """
        Removes and returns last added element from the linked list.
        """
        raise NotImplementedError

    @property
    @abstractmethod
    def front(self):
        """
        Gets most recent added element from list.
        """
        raise NotImplementedError

    @property
    @abstractmethod
    def back(self):
        """
        Gets least recent added element from list.
        """
        raise NotImplementedError

    @abstractmethod
    def push(self, node):
        """
        Appends given node to head of linked list. If a node is contains in linked list then moves node to head.
        :returns node which was discarded when the cardinality exceeds the capacity of the linked list.
        """
        raise NotImplementedError
