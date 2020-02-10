#pragma once

#include "Employee.h"

class Director : public Employee
{
public:
    Director(std::string name, std::string address, std::string id, int salary);
};
