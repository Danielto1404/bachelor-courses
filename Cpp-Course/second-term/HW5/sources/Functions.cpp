#include <iostream>
#include "../headers/FunctionsLib.h"

void swap_min(int** m, unsigned rows, unsigned cols)
{
    if (rows * cols == 0)
    {
        return;
    }
    int minVal = m[0][0];
    int minValRow = 0;
    for (unsigned int i = 0; i < rows; ++i)
    {
        for (unsigned int j = 0; j < cols; ++j)
        {
            if (minVal > m[i][j])
            {
                minValRow = i;
                minVal = m[i][j];
            }
        }
    }
    std::swap(m[minValRow], m[0]);
}

char* resize(const char* str, unsigned size, unsigned new_size)
{
    char* res = nullptr;
    if (new_size != 0)
    {
        res = new char[new_size];
        size_t validSize = std::min(size, new_size);
        for (int i = 0; i < validSize; ++i)
        {
            res[i] = str[i];
        }

    }
    delete[] str;
    return res;
}

char* getline()
{
    unsigned allocSize = 16;
    unsigned pos;
    char curSymbol;
    char* str = static_cast<char*> (malloc(allocSize));
    for (pos = 0; std::cin.get(curSymbol) && !isEnd(curSymbol); ++pos)
    {
        str[pos] = curSymbol;
        if (pos == allocSize - 1)
        {
            str = static_cast<char*> (realloc(str, allocSize * 2));
            allocSize *= 2;
        }
    }
    str[pos] = '\0';
    return static_cast<char*>(realloc(str, pos + 1));
}
