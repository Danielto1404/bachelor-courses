import unittest

from lrucache.linked_list import DoublyLinkedList, Node


class LinkedListTest(unittest.TestCase):

    @staticmethod
    def filled_list(capacity, amount_to_fill):
        """
        Fills list with sequentially int numbers
        """
        linked_list = DoublyLinkedList(capacity=capacity)
        for i in range(1, amount_to_fill + 1):
            node = Node(i)
            linked_list.push(node)
        return linked_list

    @unittest.expectedFailure
    def test_zero_capacity(self):
        DoublyLinkedList(capacity=0)

    @unittest.expectedFailure
    def test_negative_capacity(self):
        DoublyLinkedList(capacity=-10)

    @unittest.expectedFailure
    def test_non_integer_capacity(self):
        DoublyLinkedList(capacity='10')

    def test_size_small_capacity(self):
        linked_list = self.filled_list(capacity=4, amount_to_fill=5)
        self.assertEqual(linked_list.size, 4)

    def test_size_big_capacity(self):
        linked_list = self.filled_list(capacity=128, amount_to_fill=256)
        self.assertEqual(len(linked_list), 128)

    def test_push_pop_size(self):
        linked_list = self.filled_list(capacity=3, amount_to_fill=3)
        linked_list.pop()
        self.assertEqual(linked_list.size, 2)
        linked_list.pop()
        self.assertEqual(linked_list.size, 1)
        linked_list.pop()
        self.assertEqual(linked_list.size, 0)

    def test_push_pop_data_equality(self):
        linked_list = self.filled_list(capacity=10, amount_to_fill=8)
        for i in range(1, linked_list.size + 1):
            x = linked_list.pop()
            self.assertEqual(x, i)

    def test_last(self):
        linked_list = self.filled_list(capacity=4, amount_to_fill=5)
        self.assertEqual(linked_list.back, 2)

    def test_first(self):
        linked_list = self.filled_list(capacity=2, amount_to_fill=10)
        self.assertEqual(linked_list.front, 10)

    def test_push_existing_node(self):
        linked_list = DoublyLinkedList(capacity=5)
        node = Node(1)
        for _ in range(5):
            linked_list.push(node)

        node = Node(2)
        for _ in range(5):
            linked_list.push(node)

        self.assertEqual(linked_list.size, 2)

    @unittest.expectedFailure
    def test_pop_from_empty(self):
        DoublyLinkedList().pop()

    @unittest.expectedFailure
    def test_last_from_empty(self):
        return DoublyLinkedList().back

    @unittest.expectedFailure
    def test_first_from_empty(self):
        return DoublyLinkedList().front

    def test_non_int_filling(self):
        linked_list = DoublyLinkedList(capacity=5)
        for i in range(5):
            linked_list.push(Node(str(i)))
        self.assertEqual(linked_list.front, '4')
        self.assertEqual(linked_list.back, '0')

    def test_multiple_pop(self):
        linked_list = self.filled_list(capacity=5, amount_to_fill=10)
        for _ in range(2):
            linked_list.pop()
        self.assertEqual(linked_list.front, 10)
        self.assertEqual(linked_list.back, 8)


if __name__ == '__main__':
    unittest.main()
