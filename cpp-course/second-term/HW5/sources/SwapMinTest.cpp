#include <iostream>
#include "../headers/ArrayLib.h"
#include "../headers/FunctionsLib.h"

void initialize(unsigned& n, unsigned& m, unsigned& minRow, unsigned& minCol, int& minVal, int**& toSwap, int**& ans)
{
    n = randUnsigned(50) + 1;
    m = randUnsigned(50) + 1;
    minRow = randUnsigned(n);
    minCol = randUnsigned(m);
    minVal = randUnsigned(1000) - 500;
    toSwap = create_array(n, m);
    ans = create_array(n, m);
}

void fill(unsigned n, unsigned m, unsigned minValRow, unsigned minValCol, int minVal, int** toSwap, int** ans)
{
    for (unsigned i = 0; i < n; ++i)
    {
        for (unsigned j = 0; j < m; ++j)
        {
            int curEl = (minVal + 1 + randUnsigned(1000));
            toSwap[i][j] = curEl;
            ans[i][j] = curEl;
        }
    }
    toSwap[minValRow][minValCol] = minVal;
    ans[minValRow][minValCol] = minVal;
}

void testSwapMin(unsigned numberOfTest)
{
    std::cout << "\nTesting \"swap_min\" function : \n\n";
    unsigned testPassedCount = 0, n, m, minValRow, minValCol;
    int minValue;
    int** toSwap, ** ans;
    clock_t begin = clock();
    for (unsigned test = 0; test < numberOfTest; ++test)
    {
        initialize(n, m, minValRow, minValCol, minValue, toSwap, ans);
        fill(n, m, minValRow, minValCol, minValue, toSwap, ans);
        std::swap(ans[0], ans[minValRow]);
        swap_min(toSwap, n, m);
        if (!checkArrayEquals(n, m, toSwap, ans))
        {
            fail(testPassedCount);
            std::cout << "\nMin : [" << minValRow << "][" << minValCol << "] = " << minValue << '\n';
            std::cout << "\nexpected :\n";
            printArray(ans, n, m);
            std::cout << "\ngot :\n";
            printArray(toSwap, n, m);
            return;
        } else
        {
            showAndCountTests(testPassedCount, numberOfTest, 5);
        }
        deleteArray(toSwap, n, m);
        deleteArray(ans, n, m);
    }
    showTime(begin);
}