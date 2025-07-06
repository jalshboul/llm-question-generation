package model;

import java.time.LocalDate;

public class Employee {
    private Name name;
    private String title;
    private String department;
    private LocalDate birthdate;
    private int salary;

    public Employee(Name name, String title, String department, LocalDate birthdate, int salary) {
        this.name = name;
        this.title = title;
        this.department = department;
        this.birthdate = birthdate;
        this.salary = salary;
    }

    public Name getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public int getSalary() {
        return salary;
    }
}