#include <iostream>
#include "../headers/FunctionsLib.h"

void testResize(unsigned numberOfTest)
{
    std::cout << "Testing \"resize\" function :\n\n";
    clock_t begin = clock();
    unsigned testPassedCount = 0;
    for (unsigned test = 0; test < numberOfTest; ++test)
    {
        unsigned size = randUnsigned(1000);
        unsigned newSize = randUnsigned(1000);
        char* str = new char[size];
        char testArr[size];
        for (unsigned i = 0; i < size; ++i)
        {
            str[i] = static_cast<char>(randUnsigned(100));
            testArr[i] = str[i];
        }
        char* resizedStr = resize(str, size, newSize);
        unsigned sz = std::min(size, newSize);
        for (unsigned j = 0; j < sz; ++j)
        {

            if (resizedStr[j] != testArr[j])
            {
                fail(test);
                std::cout << "\nexpected : " << testArr << '\n';
                std::cout << "\ngot : " << resizedStr << '\n';
                return;
            }
        }
        delete[] resizedStr;
        showAndCountTests(testPassedCount, numberOfTest, 5);
    }
    showTime(begin);
}
