from dataclasses import dataclass

from drawing.figures.drawable import Drawable
from drawing.figures.points import Point2D


@dataclass
class Line2D(Drawable):
    from_point: Point2D
    to_point: Point2D

    def draw(self, drawing_api):
        drawing_api.draw_line2d(self)
