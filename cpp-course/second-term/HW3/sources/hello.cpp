#include <iostream>

void hello()
{
#ifdef NAME
    std::cout << "\nHello, " << NAME << "!\n\n";
#endif

#ifndef NAME
    std::cout << "\nHello!\n\n";
#endif
}
