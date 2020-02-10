#include <iostream>
#include "String.h"

String::String(const char* str) : mSize(strlen(str)), mString(strcpy(new char[mSize + 1], str)) {}

String::String(size_t n, char c) : mSize(n), mString(static_cast<char*>(memset(new char[mSize + 1], c, mSize)))
{
    mString[mSize] = '\0';
}

String::String(const String& data) : mSize(data.mSize), mString(strcpy(new char[mSize + 1], data.mString)) {}

String::~String()
{
    delete[] mString;
}

String& String::operator=(const String& data)
{
    if (this != &data)
    {
        delete[] mString;
        mSize = data.mSize;
        mString = strcpy(new char[mSize + 1], data.mString);
    }
    return *this;
}

void String::append(const String& other)
{
    mSize += other.mSize;
    char* append = strcat(strcpy(new char[mSize + 1], mString), other.mString);
    delete[] mString;
    mString = append;
}

void String::show() const
{
    std::cout << "len: " << getSize() << std::endl;
    int i = 0;
    while (mString[i] != '\0')
    {
        std::cout << mString[i];
        i++;
    }
    std::cout << std::endl << "~~~~~" << std::endl;
}

size_t String::getSize() const
{
    return mSize;
}