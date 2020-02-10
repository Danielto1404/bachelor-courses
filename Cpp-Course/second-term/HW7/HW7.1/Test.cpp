#include <iostream>
#include "Rect.hpp"

class Driver
{
public:
    void test(Rect r) const
    {
        std::cout << "Area = " << r.getArea() << std::endl;
        std::cout << "Perimeter = " << r.getPerimeter() << std::endl;
    }
};

int main()
{
    Driver driver;
    Rect pf = Rect(2, 3);
    driver.test(pf);
    PlaneFigure* a = new Rect(2, 3);
    delete a;
    return 0;
}