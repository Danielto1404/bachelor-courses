from __future__ import annotations

from dataclasses import dataclass
from math import cos, sin


@dataclass
class Point2D:
    x: float
    y: float

    def __add__(self, other):
        return Point2D(self.x + other.x, self.y + other.y)

    @staticmethod
    def from_polar(angle: float, radius: float, center: Point2D | None = None):
        """
        :param angle: float – angle in radians
        :param radius: float – radius as real number
        :param center: Point2D | None – center of coordinates, default=Point2D(x=0,y=0)
        """
        if center is None:
            center = Point2D(x=0.0, y=0.0)

        x = cos(angle) * radius
        y = sin(angle) * radius
        return Point2D(x, y) + center
