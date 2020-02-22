#include "Trust.h"

void Trust::getMoney(int value)
{
    mFundsBalance.achieveMoney(value);
}

std::string Trust::printDetails() const
{
    std::string result =
            mTrustName + "\nCEO: " + mCeo.printDetails() + "\nHead office info: " + mHeadOffice.printDetails() +
            "Other offices:\n";
    for (const auto& office: mOffices)
    {
        result += office.printDetails();
        result += "----------------------\n";
    }
    result += "Fund balance: " + mFundsBalance.printDetails() + "\n";
    return result;
}

FundsBalance Trust::getFundsBalance() const
{
    return mFundsBalance;
}

void Trust::changeCEO(CEO newCeo)
{
    mCeo = std::move(newCeo);
}

Trust::Trust(CEO ceo, Office headOffice, std::string trustName) : Department(&ceo, headOffice)
{
    Department::mDepartmentName = "Trust";
    mCeo = std::move(ceo);
    mHeadOffice = std::move(headOffice);
    mTrustName = std::move(trustName);
}

CEO Trust::getCEO() const
{
    return mCeo;
}

void Trust::changeHeadOffice(Office office)
{
    mHeadOffice = std::move(office);
}
