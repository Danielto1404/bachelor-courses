#pragma once

#include <vector>
#include "../Message/Message.h"

class Budget
{
public:
    Budget();
    explicit Budget(int budget);
    std::vector <int> fundActivity() const;
    int printDetails() const;
    void awardMoney(const Message* cause);
    void payMoney(const Message* event);
private:
    std::vector <int> mChanges;
    int mBudget;
};

