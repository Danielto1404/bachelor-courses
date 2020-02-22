#include <iostream>
#include <random>

unsigned randUnsigned(unsigned bound)
{
    std::random_device rd;
    std::uniform_int_distribution <unsigned int> uid(0, bound - 1);
    return uid(rd);
}

char getRandomChar()
{
    char symbol = static_cast<char>(randUnsigned(100));
    if (symbol == '\0' || symbol == '\n')
    {
        return getRandomChar();
    }
    return symbol;
}

bool isEnd(char c)
{
    return c == '\n' || c == '\0';
}

void fail(unsigned testNumber)
{
    std::cout << "Passed : " << testNumber << " tests\n";
    std::cout << "Test №" << testNumber + 1 << " failed\n";
}

void showTime(clock_t time)
{
    std::cout << "===Testing finished in : " << (clock() - time) / 1000 << " ms\n";
}

void showAndCountTests(unsigned& testPassedCount, unsigned numberOfTest, unsigned freq)
{
    testPassedCount++;
    if (testPassedCount % (numberOfTest / freq) == 0)
    {
        std::cout << ">> Tests passed : " << testPassedCount << "\n";
    }
}

void border()
{
    std::cout << "\n~~~~~~~~~~~~~\n\n";
}
