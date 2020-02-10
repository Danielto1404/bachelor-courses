#include "Department.h"
#include "../Employee/Employee.h"
#include "../Office/Office.h"

Department::Department(Employee* employee, Office office)
{
    mEmployees.push_back(employee);
    mOffices.push_back(std::move(office));
}

std::string Department::printDetails() const
{
    std::string result = mDepartmentName + "\n--------------------\n";
    for (const auto& office: mOffices)
    {
        result += office.printDetails() + +"--------------------\n";
    }
    for (auto employee: mEmployees)
    {
        result += employee->printDetails() + +"--------------------\n";
    }
    result += "Budget: " + std::to_string(mBudget.printDetails()) + "\n";
    return result;
}

Department::Department(std::vector <Employee*> employees, std::vector <Office> offices)
{
    mEmployees = std::move(employees);
    mOffices = std::move(offices);
}

void Department::addOffice(Office office)
{
    mOffices.emplace_back(std::move(office));
}

void Department::addEmployee(Employee* employee)
{
    mEmployees.emplace_back(employee);
}

const Budget Department::getBudget() const
{
    return mBudget;
}

Department::~Department() = default;

std::string Department::track() const
{
    std::string result;
    for (auto message: mMessages)
    {
        result += message->printDetails() + "--------------------\n";
    }
    return result;
}

std::vector <Office> Department::getOfficesVector() const
{
    return mOffices;
}

std::vector <Employee*> Department::getEmployeesVector() const
{
    return mEmployees;
}
