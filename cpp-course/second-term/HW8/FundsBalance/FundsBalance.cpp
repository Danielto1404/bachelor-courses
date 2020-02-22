#include "FundsBalance.h"

std::string FundsBalance::printDetails() const
{
    return "Funds balance: " + std::to_string(mBalance);
}

void FundsBalance::achieveMoney(int value)
{
    mBalance += value;
}
