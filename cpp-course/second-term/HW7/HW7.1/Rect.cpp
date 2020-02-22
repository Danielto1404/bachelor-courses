#include "Rect.hpp"

Rect::Rect(double w, double h) : mWidth(w), mHeight(h) {}

double Rect::getArea()
{
    return mWidth * mHeight;
}

double Rect::getPerimeter()
{
    return 2 * mWidth + 2 * mHeight;
}