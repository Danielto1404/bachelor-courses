#pragma once

#include <vector>
#include "../Budget/Budget.h"
#include "../Office/Office.h"
#include "../Employee/Employee.h"
#include "../Message/Message.h"

class Department
{
public:
    virtual ~Department();
    Department(Employee* employee, Office office);
    Department(std::vector <Employee*> employees, std::vector <Office> offices);
    virtual std::string printDetails() const;
    void addOffice(Office office);
    void addEmployee(Employee* employee);
    const Budget getBudget() const;
    std::string track() const;
    std::vector <Office> getOfficesVector() const;
    std::vector <Employee*> getEmployeesVector() const;
protected:
    Budget mBudget;
    std::vector <Message*> mMessages;
    std::string mDepartmentName = "Department";
private:
    std::vector <Employee*> mEmployees;
    std::vector <Office> mOffices;
};

