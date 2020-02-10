#include <iostream>
#include "headers/FunctionsLib.h"

int main()
{
    testSwapMin(100);
    border();
    testResize(100);
    border();
    std::cout << "Input your line : ";
    auto s = getline();
    std::cout << "\nYour line is : \n" << s << "\n\n";
    delete[] s;
}
