#pragma once

class PlaneFigure
{
public:
    virtual ~PlaneFigure() = default;
    virtual double getArea() = 0;
    virtual double getPerimeter() = 0;
};
