#include <iostream>

int** create_array(unsigned n, unsigned m)
{
    int** arr = new int* [n];
    for (unsigned int i = 0; i != n; ++i)
    {
        arr[i] = new int[m];
    }
    return arr;
}

void deleteArray(int** arr, unsigned n, unsigned m)
{
    for (unsigned i = 0; i < n; ++i)
    {
        delete[] arr[i];
    }
    delete[]arr;
}

void printArray(int** arr, unsigned n, unsigned m)
{
    for (unsigned i = 0; i < n; i++)
    {
        std::cout << "[" << i << "]";
        for (unsigned j = 0; j < m; j++)
        {
            std::cout << arr[i][j] << " ";
        }
        std::cout << "\n";
    }
}

bool checkArrayEquals(unsigned n, unsigned m, int** firstArr, int** secondArr)
{
    for (unsigned i = 0; i < n; ++i)
    {
        for (unsigned j = 0; j < m; ++j)
        {
            if (firstArr[i][j] != secondArr[i][j])
                return false;
        }
    }
    return true;
}

