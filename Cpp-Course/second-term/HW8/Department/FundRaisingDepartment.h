#pragma once

#include "Department.h"
#include "../Employee/Employee.h"
#include "../Office/Office.h"

class FundRaisingDepartment : public Department
{
public:
    FundRaisingDepartment(Employee* employee, Office office);
    FundRaisingDepartment(std::vector <Employee*> employees, std::vector <Office> offices);
    std::string printDetails() const override;
    void awardMoney(std::string name, int amountAwarded, std::string description);
};


