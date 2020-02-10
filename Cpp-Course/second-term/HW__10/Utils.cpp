#include "Utils.h"
#include <random>

std::string generateString(size_t maxLen)
{
    std::string s;
    for (size_t i = 0; i < maxLen; i++)
    {
        s += std::string(1, randLetter());
    }
    return s;
}

char randLetter()
{
    return 'a' + randInt(0, 25);
}

int randInt(int lowBnd, int upBnd)
{
    std::random_device rd;
    std::uniform_int_distribution <int> uid(lowBnd, upBnd);
    return uid(rd);
}

MyClass::MyClass(int x, int y) : mX(x), mY(y)
{}

std::ostream& operator<<(std::ostream& out, const MyClass& myClass)
{
    out << "{x=" << myClass.mX << ",y=" << myClass.mY << "}";
    return out;
}
