#ifndef NAME_H
#define NAME_H

#include <string>

class Name {
public:
    std::string first;
    std::string middle;
    std::string last;

    Name() = default;
    Name(const std::string& first, const std::string& middle, const std::string& last)
        : first(first), middle(middle), last(last) {}

    std::string fullName() const {
        return middle.empty() ? first + " " + last : first + " " + middle + " " + last;
    }
};

#endif