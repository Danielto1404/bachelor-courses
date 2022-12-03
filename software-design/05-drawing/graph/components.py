from dataclasses import dataclass


@dataclass
class Node:
    name: str | int

    def __hash__(self):
        return hash(self.name)


@dataclass
class Edge:
    from_node: Node
    to_node: Node

    def __hash__(self):
        return hash(self.from_node) - hash(self.to_node)
