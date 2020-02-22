#include <utility>
#include "CEO.h"

CEO::CEO(std::string name, std::string address, std::string id, int salary) :
        Employee::Employee(std::move(name), std::move(address), std::move(id), salary, "CEO") {}
