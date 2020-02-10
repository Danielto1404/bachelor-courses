#include <iostream>
#include "Department/Department.h"
#include "Employee/CEO.h"
#include "Employee/Director.h"
#include "Department/GrantDepartment.h"
#include "Department/FundRaisingDepartment.h"
#include <vector>

int main()
{
    Employee* employee1 = new CEO("Daniil Korolev", "Udelka rulit", "239239239", 10000);
    Employee* employee2 = new Director("Oleg Bobrik", "Akademka 2", "30303030", 10000);
    Office office("Itmo", 1234567);
    std::vector <Employee*> employees = {employee1, employee2};
    std::vector <Office> offices = {office};

    FundRaisingDepartment fundRaisingDepartment(employees, offices);
    fundRaisingDepartment.awardMoney("Aaaa", 12000, "Just for chill");
    std::cout << fundRaisingDepartment.printDetails();

    GrantDepartment grantDepartment(employee1, office);
    std::cout << "\n~~~~~~~~~~~~~~~~~~~~~\n";
    grantDepartment.payMoney("Grant", 1000, "for investigation payment");
    std::cout << grantDepartment.printDetails() << "\n\n";

}