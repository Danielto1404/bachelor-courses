#pragma once

#include <iostream>

class Employee
{
public:
    std::string printDetails() const;
    Employee();
    Employee(std::string name, std::string address, std::string id, int salary, std::string jobType);
    void changeSalary(int newSalary);
    void changeAddress(std::string newAddress);
    void changeId(std::string newId);
private:
    std::string mName;
    std::string mAddress;
    std::string mId;
    int mSalary = 0;
    std::string mJobType;
};
