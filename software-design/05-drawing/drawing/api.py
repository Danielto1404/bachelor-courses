import math
from abc import ABC, abstractmethod

import cairo
import numpy as np
import pygame

from drawing.colors import WHITE, BLACK, PINK
from drawing.figures.circle import Circle
from drawing.figures.lines import Line2D

AVAILABLE_DRAWING_API = [
    'pygame', 'pycairo'
]


class DrawingApiException(Exception):
    pass


class DrawingApi(ABC):
    def __init__(self, areaWidth: int = 800, areaHeight: int = 600):
        self.areaWidth = areaWidth
        self.areaHeight = areaHeight

    @abstractmethod
    def draw_circle(self, circle: Circle):
        pass

    @abstractmethod
    def draw_line2d(self, line: Line2D):
        pass

    @abstractmethod
    def render_screen(self):
        pass


class PyGameDrawingAPI(DrawingApi):
    def __init__(self, areaWidth: int = 800, areaHeight: int = 600):
        super().__init__(areaWidth, areaHeight)
        self.screen = pygame.display.set_mode((areaWidth, areaHeight))
        self.screen.fill(WHITE)

    def draw_circle(self, circle: Circle):
        pygame.draw.circle(
            surface=self.screen,
            color=PINK,
            center=(circle.center.x, circle.center.y),
            radius=circle.radius
        )
        pygame.display.flip()

    def draw_line2d(self, line: Line2D):
        pygame.draw.line(
            surface=self.screen,
            color=BLACK,
            start_pos=(line.from_point.x, line.from_point.y),
            end_pos=(line.to_point.x, line.to_point.y)
        )
        pygame.display.flip()

    def render_screen(self):
        running = True
        while running:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False


class PyCairoDrawingApi(DrawingApi):

    def __init__(self, areaWidth: int = 800, areaHeight: int = 600):
        super().__init__(areaWidth, areaHeight)
        self.surface = cairo.ImageSurface(cairo.FORMAT_ARGB32, self.areaWidth, self.areaHeight)
        self.context = cairo.Context(self.surface)

        self.__init_context__(self.context)

    def __init_context__(self, context):
        context.rectangle(0, 0, self.areaWidth, self.areaHeight)
        context.fill()

    def draw_circle(self, circle: Circle):
        self.context.move_to(circle.center.x, circle.center.y)
        self.context.arc(circle.center.x, circle.center.y, circle.radius, 0, 2 * math.pi)

    def draw_line2d(self, line: Line2D):
        self.context.move_to(line.from_point.x, line.from_point.y)
        self.context.line_to(line.to_point.x, line.to_point.y)

    def render_screen(self):
        import matplotlib.pyplot as plt

        self.context.set_source_rgb(1, 1, 1)
        self.context.stroke()
        image_bytes = self.surface.get_data()
        image = np.ndarray(shape=(self.areaHeight, self.areaWidth, 4), dtype=np.uint8, buffer=image_bytes)
        plt.imshow(image)
        plt.axis('off')
        plt.show()


class DrawingApiBuilder:
    @staticmethod
    def from_name(api_name: str) -> DrawingApi:
        match api_name:
            case 'pygame':
                return PyGameDrawingAPI()
            case 'pycairo':
                return PyCairoDrawingApi()
            case _:
                raise DrawingApiException(f'DrawingApi: {api_name=} not found')
