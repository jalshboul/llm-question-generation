#ifndef DATABASE_H
#define DATABASE_H

#include "model/Department.h"
#include "model/Title.h"
#include <map>
#include <fstream>
#include <sstream>
#include <iostream>
#include <iomanip>

class Database {
public:
    std::map<std::string, Department> departments;
    std::map<std::string, Title> titles;

    static Database& getInstance() {
        static Database instance;
        return instance;
    }

    Database(const Database&) = delete;
    void operator=(const Database&) = delete;

private:
    Database() {
        load();
    }

    void load() {
        readTitles();
        readDepartments();
        readEmployees();
    }

    void readDepartments() {
        std::ifstream file("data/departments.csv");
        std::string line;
        std::getline(file, line); // Skip header

        while (std::getline(file, line)) {
            std::stringstream ss(line);
            std::string id, name;
            std::getline(ss, id, ',');
            std::getline(ss, name, ',');
            departments[id] = Department(name);
        }
    }

    void readTitles() {
        std::ifstream file("data/titles.csv");
        std::string line;
        std::getline(file, line); // Skip header

        while (std::getline(file, line)) {
            std::stringstream ss(line);
            std::string id, name, salaryStr;
            std::getline(ss, id, ',');
            std::getline(ss, name, ',');
            std::getline(ss, salaryStr, ',');
            titles[id] = Title(id, name, std::stoi(salaryStr));
        }
    }

    void readEmployees() {
        std::ifstream file("data/employees.csv");
        std::string line;
        std::getline(file, line); // Skip header

        while (std::getline(file, line)) {
            std::stringstream ss(line);
            std::string fullName, departmentId, birthStr, titleId;
            std::getline(ss, fullName, ',');
            std::getline(ss, departmentId, ',');
            std::getline(ss, birthStr, ',');
            std::getline(ss, titleId, ',');

            std::tm birthdate{};
            std::istringstream(birthStr) >> std::get_time(&birthdate, "%Y-%m-%d");

            Title title = titles[titleId];

            std::istringstream nameStream(fullName);
            std::string first, middle, last;
            nameStream >> first;
            if (!(nameStream >> middle)) {
                last = middle;
                middle = "";
            } else {
                nameStream >> last;
            }

            Name name(first, middle, last);
            Employee emp(name, title.name, departmentId, birthdate, title.baseSalary);
            departments[departmentId].addEmployee(emp);
        }
    }
};

#endif