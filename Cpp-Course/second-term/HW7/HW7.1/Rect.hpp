#pragma once

#include "PlaneFigure.h"

class Rect : public PlaneFigure
{
public:
    Rect(double w, double h);

    double getArea() override;
    double getPerimeter() override;
private:
    double mWidth;
    double mHeight;
};

