#include "GrantDepartment.h"
#include "../Message/Event.h"

bool GrantDepartment::checkBudget() const
{
    return Department::mBudget.printDetails() > 0;
}

void GrantDepartment::payMoney(std::string name, int amountPaid, std::string description)
{
    Event* event = new Event(std::move(name), amountPaid, std::move(description));
    mMessages.emplace_back(event);
    Department::mBudget.payMoney(event);
}

std::string GrantDepartment::printDetails() const
{
    return Department::printDetails();
}

GrantDepartment::GrantDepartment(Employee* employee, Office office) : Department(employee, std::move(office))
{
    Department::mDepartmentName = "Grant department";
}

GrantDepartment::GrantDepartment(std::vector <Employee*> employees, std::vector <Office> offices) : Department(
        std::move(employees), std::move(offices))
{
    Department::mDepartmentName = "Grant department";
}
