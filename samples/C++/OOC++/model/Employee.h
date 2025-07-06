#ifndef EMPLOYEE_H
#define EMPLOYEE_H

#include "Name.h"
#include <string>
#include <ctime>

class Employee {
public:
    Name name;
    std::string title;
    std::string department;
    std::tm birthdate{};
    int salary;

    Employee() = default;
    Employee(Name name, std::string title, std::string department, std::tm birthdate, int salary)
        : name(name), title(std::move(title)), department(std::move(department)), birthdate(birthdate), salary(salary) {}
};

#endif