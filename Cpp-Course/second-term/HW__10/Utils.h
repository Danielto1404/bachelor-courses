#pragma once

#include <string>

std::string generateString(size_t maxLen);

char randLetter();

int randInt(int lowBnd, int upBnd);

struct MyClass
{
    MyClass() = delete;
    MyClass(int x, int y);
    MyClass(const MyClass& other) = default;
    ~MyClass() = default;

    MyClass& operator=(const MyClass& other) = delete;
    friend std::ostream& operator<<(std::ostream& out, const MyClass& myClass);
private:
    int mX = 0;
    int mY = 0;
};
