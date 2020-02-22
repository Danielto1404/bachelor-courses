#pragma once

#include "Employee.h"

class CEO : public Employee
{
public:
    CEO() = default;
    CEO(std::string name, std::string address, std::string id, int salary);
};