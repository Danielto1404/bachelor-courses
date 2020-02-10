#pragma once

#include <string>

class FundsBalance
{
public:
    std::string printDetails() const;
    void achieveMoney(int value);
private:
    int mBalance = 0;
};


