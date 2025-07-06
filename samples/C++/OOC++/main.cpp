#include "Database.h"
#include <iostream>

int main() {
    Database& db = Database::getInstance();

    for (const auto& [id, dep] : db.departments) {
        std::cout << id << " => " << dep.name
                  << " with " << dep.employees.size() << " employees\n";
    }

    return 0;
}