import math
from abc import abstractmethod, ABC
from collections import defaultdict
from typing import Callable

import numpy as np

from drawing.api import DrawingApi
from drawing.figures.circle import Circle
from drawing.figures.lines import Line2D
from drawing.figures.points import Point2D
from graph.components import Edge, Node
from graph.inputs import EdgesListInput, AdjacencyMatrixInput

AVAILABLE_GRAPH_CONSTRUCTORS = [
    'edges', 'adjacency'
]


class GraphException(Exception):
    pass


class Graph(ABC):
    def __init__(self, drawing_api: DrawingApi):
        self.drawing_api = drawing_api

    @abstractmethod
    def add_edge(self, edge: Edge):
        pass

    @abstractmethod
    def schema(self) -> (list, list):
        """
        :return: (list of nodes, list of edges)
        """
        pass

    def draw(self):
        nodes, edges = self.schema()
        api = self.drawing_api
        center = Point2D(x=api.areaWidth // 2, y=api.areaHeight // 2)
        min_dim = min(api.areaWidth, api.areaHeight)
        radius = min_dim * 0.4

        coordinates = {
            node: Point2D.from_polar(angle=i * 2 * math.pi / len(nodes),
                                     radius=radius,
                                     center=center)
            for i, node in enumerate(nodes)
        }

        for e in edges:
            Line2D(from_point=coordinates[e.from_node], to_point=coordinates[e.to_node]).draw(api)

        for node_point in coordinates.values():
            Circle(center=node_point, radius=min_dim * 0.01).draw(api)

        api.render_screen()


class EdgeListGraph(Graph, EdgesListInput):
    def __init__(self, drawing_api: DrawingApi):
        super().__init__(drawing_api)
        self.edges_list = defaultdict(list)

    def add_edge(self, edge: Edge):
        self.edges_list[edge.from_node].append(edge.to_node)
        self.edges_list[edge.to_node].append(edge.from_node)

    def schema(self) -> (list, list):
        nodes = list(self.edges_list.keys())
        edges = list({
            Edge(node, neighbour) for node, neighbors in self.edges_list.items() for neighbour in neighbors
        })
        return nodes, edges


class AdjacencyMatrixGraph(Graph, AdjacencyMatrixInput):
    def __init__(self, drawing_api: DrawingApi):
        super().__init__(drawing_api)
        self.matrix = np.array([])

    @property
    def nodes_cnt(self):
        return len(self.matrix)

    def add_edge(self, edge: Edge):
        a, b = edge.from_node.name, edge.to_node.name
        max_dimension = max(a, b) + 1
        if len(self.matrix) <= max_dimension:
            matrix = np.zeros((max_dimension, max_dimension))
            matrix[:len(self.matrix), :len(self.matrix)] = self.matrix
            self.matrix = matrix

        self.matrix[a][b] += 1

    def schema(self) -> (list, list):
        nodes = list(map(Node, range(self.nodes_cnt)))
        edges = list({
            Edge(Node(i), Node(j)) for i in range(self.nodes_cnt) for j in range(self.nodes_cnt)
            if self.matrix[i][j] != 0
        })
        return nodes, edges


class GraphClass:
    @staticmethod
    def from_name(graph_constructor: str) -> Callable[[DrawingApi], Graph]:
        match graph_constructor:
            case 'edges':
                return EdgeListGraph
            case 'adjacency':
                return AdjacencyMatrixGraph
            case _:
                raise GraphException(f'Graph class for name: {graph_constructor=} not found')
