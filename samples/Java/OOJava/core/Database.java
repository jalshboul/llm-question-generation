package core;

import model.Department;
import model.Employee;
import model.Name;
import model.Title;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database instance;

    public Map<String, Department> departments = new HashMap<>();
    private Map<String, Title> titles = new HashMap<>();

    private Database() {
        load();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void load() {
        readTitles();
        readDepartments();
        readEmployees();
    }

    private void readDepartments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/departments.csv"))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String id = split[0].trim();
                String name = split[1].trim();
                departments.put(id, new Department(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTitles() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/titles.csv"))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String id = split[0].trim();
                String name = split[1].trim();
                int salary = Integer.parseInt(split[2].trim());
                titles.put(id, new Title(id, name, salary));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readEmployees() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/employees.csv"))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String nameStr = split[0].trim();
                String departmentId = split[1].trim();
                LocalDate birthdate = LocalDate.parse(split[2].trim());
                String titleId = split[3].trim();

                Department dep = departments.get(departmentId);
                Title title = titles.get(titleId);

                String[] nameParts = nameStr.split(" ");
                String first = nameParts[0];
                String last = nameParts[nameParts.length - 1];
                String middle = (nameParts.length == 3) ? nameParts[1] : "";

                Name name = new Name(first, middle, last);
                Employee emp = new Employee(name, title.getName(), departmentId, birthdate, title.getBaseSalary());
                dep.addEmployee(emp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}