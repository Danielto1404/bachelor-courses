from abc import ABC, abstractmethod

from graph.components import Edge, Node


class GraphCLIInput(ABC):
    @abstractmethod
    def prompt(self) -> list[Edge]:
        pass


class EdgesListInput(GraphCLIInput):
    def prompt(self) -> list[Edge]:
        edges = []
        number_of_edges = int(input('provide number of edges: '))
        for i in range(number_of_edges):
            a, b = input().split()
            edges.append(Edge(Node(a), Node(b)))
        print('Prompt finished successfully ✅!')

        return edges


class AdjacencyMatrixInput(GraphCLIInput):
    def prompt(self) -> list[Edge]:
        edges = []
        number_of_nodes = int(input('provide number of initial nodes: '))
        for root_node in range(number_of_nodes):
            nodes = list(map(int, input().split()))
            assert len(nodes) == number_of_nodes, f'Number of nodes should be equal to {number_of_nodes}'
            edges += [
                Edge(Node(root_node), Node(node_index))
                for node_index, amount_of_edges in enumerate(nodes)
                for _ in range(amount_of_edges)
            ]
        print('Prompt finished successfully ✅!')
        return edges
