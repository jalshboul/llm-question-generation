#ifndef TITLE_H
#define TITLE_H

#include <string>

class Title {
public:
    std::string id;
    std::string name;
    int baseSalary;

    Title() = default;
    Title(const std::string& id, const std::string& name, int baseSalary)
        : id(id), name(name), baseSalary(baseSalary) {}
};

#endif