#ifndef DEPARTMENT_H
#define DEPARTMENT_H

#include "Employee.h"
#include <string>
#include <vector>

class Department {
public:
    std::string name;
    std::vector<Employee> employees;

    Department() = default;
    Department(const std::string& name) : name(name) {}

    void addEmployee(const Employee& emp) {
        employees.push_back(emp);
    }
};

#endif