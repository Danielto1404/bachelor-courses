#include "Employee.h"

std::string Employee::printDetails() const
{
    return "Name: " + mName + "\nAddress: " + mAddress + "\nID: " + mId + "\nSalary: " + std::to_string(mSalary) +
           "\nJobType: " + mJobType + "\n";
}

Employee::Employee(std::string name, std::string address, std::string id, int salary, std::string jobType)
{
    mName = std::move(name);
    mAddress = std::move(address);
    mId = std::move(id);
    mSalary = salary;
    mJobType = std::move(jobType);
}

void Employee::changeSalary(int newSalary)
{
    mSalary = newSalary;
}

void Employee::changeAddress(std::string newAddress)
{
    mAddress = std::move(newAddress);
}

void Employee::changeId(std::string newId)
{
    mId = std::move(newId);
}

Employee::Employee() = default;

