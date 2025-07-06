from model.employee import Employee, Name, Title
from model.department import Department
from datetime import date


class Database:
    _instance = None

    def __new__(cls, *args, **kwargs):
        if cls._instance is None:
            cls._instance = super().__new__(cls, *args, **kwargs)
            cls._instance.__load()
        return cls._instance

    def __read_employees(self):
        f = open("data/employees.csv")
        lines = f.readlines()[1:]
        for line in lines:
            split_line = [cell.strip() for cell in line.split(",")]
            # name, department, birth, title
            dep = self.departments[split_line[1]]
            title = self.__titles[split_line[3]]
            dep.employees.append(
                Employee(
                    split_line[0],
                    title.name,
                    split_line[1],
                    date.fromisoformat(split_line[2]),
                    title.base_salary
                )
            )
        f.close()

    def __read_departments(self):
        f = open("data/departments.csv")
        self.departments = {}
        lines = f.readlines()[1:]
        for line in lines:
            split_line = [cell.strip() for cell in line.split(",")]
            self.departments[split_line[0]] = Department(split_line[1])
        f.close()

    def __read_titles(self):
        f = open("data/titles.csv")
        lines = f.readlines()[1:]
        self.__titles = {}
        for line in lines:
            split_line = [cell.strip() for cell in line.split(",")]
            self.__titles[split_line[0]] = Title(
                split_line[0], split_line[1], int(split_line[2]))
        f.close()

    def __load(self):
        self.__read_titles()
        self.__read_departments()
        self.__read_employees()
