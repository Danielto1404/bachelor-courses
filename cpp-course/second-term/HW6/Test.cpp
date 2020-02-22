#include <iostream>
#include "String.h"

int main()
{
    String s1 = String("Hello");
    String s2 = String("world");
    String s3 = String(10, 'a');
    s1.show();
    s2.show();
    s3.show();
    std::cout << "\nString s4(s2)  |  s2 = s1\n\n\n";
    String s4(s2);
    s2 = s1;
    s1.show();
    s2.show();
    s3.show();
    s4.show();
    std::cout << "\ns1.append(s3) | s2.append(s2)\n\n\n";
    s1.append(s3);
    s2.append(s2);
    s1.show();
    s2.show();
    return 0;
}
