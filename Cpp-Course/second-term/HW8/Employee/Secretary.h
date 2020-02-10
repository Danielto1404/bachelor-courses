#pragma once

#include "Employee.h"

class Secretary : public Employee
{
public:
    Secretary(std::string name, std::string address, std::string id, int salary);
};

