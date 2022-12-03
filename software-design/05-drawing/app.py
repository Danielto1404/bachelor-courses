from argparse import ArgumentParser

from drawing.api import DrawingApiBuilder, AVAILABLE_DRAWING_API
from graph.graphs import AVAILABLE_GRAPH_CONSTRUCTORS, GraphClass


def get_cli_args():
    args_parser = ArgumentParser()
    args_parser.add_argument('--api',
                             help='drawing API',
                             choices=AVAILABLE_DRAWING_API,
                             required=True)
    args_parser.add_argument('--graph',
                             help='graph constructor API',
                             choices=AVAILABLE_GRAPH_CONSTRUCTORS,
                             required=True)
    return args_parser.parse_args()


if __name__ == '__main__':
    args = get_cli_args()
    drawing_api = DrawingApiBuilder.from_name(api_name=args.api)
    graph_class = GraphClass.from_name(graph_constructor=args.graph)
    graph = graph_class(drawing_api)
    edges = graph.prompt()
    for e in edges:
        graph.add_edge(e)
    graph.draw()
