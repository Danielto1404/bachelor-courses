from dataclasses import dataclass

from drawing.figures.drawable import Drawable
from drawing.figures.points import Point2D


@dataclass
class Circle(Drawable):
    center: Point2D
    radius: float

    def draw(self, drawing_api):
        drawing_api.draw_circle(self)
