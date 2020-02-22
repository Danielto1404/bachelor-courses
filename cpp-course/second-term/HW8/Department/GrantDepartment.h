#pragma once

#include "Department.h"
#include "../Employee/Employee.h"
#include "../Office/Office.h"
#include <vector>

class GrantDepartment : public Department
{
public:
    GrantDepartment(std::vector <Employee*> employees, std::vector <Office> offices);
    GrantDepartment(Employee* employee, Office office);
    std::string printDetails() const override;
    bool checkBudget() const;
    void payMoney(std::string name, int amountPaid, std::string description);
};

