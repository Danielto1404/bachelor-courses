#include "Budget.h"

Budget::Budget(int budget)
{
    mBudget = budget;
}

int Budget::printDetails() const
{
    return mBudget;
}

Budget::Budget()
{
    mBudget = 0;
}

std::vector <int> Budget::fundActivity() const
{
    return mChanges;
}

void Budget::awardMoney(const Message* cause)
{
    int value = cause->getResults();
    mBudget += value;
    mChanges.push_back(value);
}

void Budget::payMoney(const Message* event)
{
    int value = event->getResults();
    mBudget -= value;
    mChanges.push_back(-value);
}

