#pragma once

#include <string>
#include <vector>
#include "../Employee/CEO.h"
#include "../FundsBalance/FundsBalance.h"
#include "../Office/Office.h"
#include "Department.h"

class Trust : public Department
{
public:
    Trust(CEO ceo, Office headOffice, std::string trustName);
    FundsBalance getFundsBalance() const;
    void changeCEO(CEO newCeo);
    CEO getCEO() const;
    void changeHeadOffice(Office office);
    std::string printDetails() const override;
    void getMoney(int value);
private:
    std::string mTrustName;
    std::vector <Office> mOffices;
    CEO mCeo;
    Office mHeadOffice;
    FundsBalance mFundsBalance;
};
