from dataclasses import dataclass
from typing import Optional, Any

from lrucache.interfaces import LinkedList


@dataclass
class QueryData:
    """
    Represents simple generic class for key, value storing.

    Attributes:
    ----------

    query : Any
        Represents query object

    response : Any
        Represents response object
    """
    query: Any
    response: Any


class Node:
    """
    Node class represents node in doubly linked list.

    Attributes:
    -----------
    data : Generic data, default=None
        data which stored in Node.

    nref : Optional[Node], default=None
        reference to the next node.
    pref : Optional[Node], default=None
        reference to the previous node.
    """

    def __init__(self, data: Optional[Any] = None, *, nref=None, pref=None):
        self.data = data
        self.nref = nref
        self.pref = pref

    def isEmptyLinks(self):
        return self.nref is None and self.pref is None

    @staticmethod
    def link(pref=None, nref=None):
        """
        Links given nodes as previous and next via `nref`, `pref` attributes

        :param pref: previous node (nref param would be be set)
        :param nref: next node (pref param would be set)
        """
        if pref:
            pref.nref = nref
        if nref:
            nref.pref = pref


class DoublyLinkedList(LinkedList):
    """
    Attributes:
    -----------

    capacity : int, default=128
        Capacity of the list. If amount of elements in the list requests more than capacity, then
        least recently added elements are discarded.

    Methods list:
    --------
    push(value), pop(), back(), front(), size()
    For more information see docs for specific method.
    """

    def __init__(self, capacity: int = 128):
        assert isinstance(capacity, int), 'Capacity should be an integer'
        assert capacity > 0, 'Capacity must be greater than 0'
        self.capacity = capacity
        self._counter = 0
        self._head = None
        self._tail = None

    def push(self, node: Node):
        assert isinstance(node, Node), f'Expected type: Node, but got: {type(node)}'
        deleted = self._push(node)
        assert self.front == node.data, f'New added element should be equal to front element'
        return deleted

    def pop(self):
        size = self.size
        data = self._pop()
        assert size - 1 == self.size, "Sizes after using `.pop` doesn't match"
        return data

    def _push(self, node):
        is_new_node = node.isEmptyLinks()
        deleted = self.pop() if is_new_node and self.size == self.capacity else None

        if node is self._head:
            return deleted

        if self.isEmpty():
            self._tail = node
        else:
            Node.link(node.pref, node.nref)
            Node.link(node, self._head)

        self._head = node
        self._counter += 1 if is_new_node else 0
        return deleted

    def _pop(self):
        data = self.back

        if self.size == 1:
            self._head = None
            self._tail = None
        else:
            pref = self._tail.pref
            pref.nref = None
            self._tail = pref
        self._counter -= 1
        return data

    @property
    def back(self):
        assert self.size > 0, 'Cant get last element, list is empty'
        return self._tail.data

    @property
    def front(self):
        assert self.size > 0, 'Cant get first element, list is empty'
        return self._head.data

    @property
    def size(self):
        assert 0 <= self._counter <= self.capacity, \
            f'Current amount of stored elements must be in range (0, maxsize={self.capacity}, got: {self._counter}'
        return self._counter
