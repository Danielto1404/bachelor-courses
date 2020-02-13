#include <iostream>
#include <iomanip>
#include <fstream>


typedef long double LD;
typedef unsigned U;

int main ()
{
    U n;
    std::ifstream in("markchain.in");
    std::ofstream out("markchain.out");
    in >> n;
    LD curEl;
    LD matrix[n][n];
    LD multipliedMatrix[n][n];
    LD delta = 0;
    LD EPS = 1e-5;

    for (U line = 0; line < n; ++line)
    {
        for (U col = 0; col < n; ++col)
        {
            in >> matrix[line][col];
        }
    }
    while (true)
    {
        for (U line = 0; line < n; ++line)
        {
            for (U col = 0; col < n; ++col)
            {
                curEl = 0;
                for (U p = 0; p < n; p++)
                {
                    curEl += matrix[line][p] * matrix[p][col];
                }
                multipliedMatrix[line][col] = curEl;

                if (multipliedMatrix[line][col] - matrix[line][col] > delta || (!line && !col))
                {
                    delta = multipliedMatrix[line][col] - matrix[line][col];
                }
            }
        }
        for (U row = 0; row < n; ++row)
        {
            for (U col = 0; col < n; ++col)
            {
                matrix[row][col] = multipliedMatrix[row][col];
            }
        }
        if (delta < EPS)
        {
            break;
        }
    }
    for (U i = 0; i < n; ++i)
    {
        out << std::setprecision(5) << matrix[0][i] << "\n";
    }
    return 0;
}