#include "FundRaisingDepartment.h"
#include "../Message/Cause.h"

std::string FundRaisingDepartment::printDetails() const
{
    std::string result;
    for (auto message: mMessages)
    {
        result += message->printDetails() + "\n--------------------";
    }
    return result;
}

void FundRaisingDepartment::awardMoney(std::string name, int amountAwarded, std::string description)
{
    Message* cause = new Cause(std::move(name), amountAwarded, std::move(description));
    Department::mBudget.awardMoney(cause);
    mMessages.emplace_back(cause);
}

FundRaisingDepartment::FundRaisingDepartment(Employee* employee, Office office) : Department(employee,
                                                                                             std::move(office))
{
    Department::mDepartmentName = "Fund raising department";
}

FundRaisingDepartment::FundRaisingDepartment(std::vector <Employee*> employees, std::vector <Office> offices)
        : Department(std::move(employees), std::move(offices))
{
    Department::mDepartmentName = "Fund raising department";
}
