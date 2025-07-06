from dataclasses import dataclass
from datetime import date


@dataclass(order=True)
class Title:
    id: str
    name: str
    base_salary: int


@dataclass(order=True)
class Name:
    first: str
    middle: str
    last: str

    def full_name(self):
        if len(self.middle) > 0:
            return f"{self.first} {self.middle} {self.last}"
        return f"{self.first} {self.last}"


@dataclass(order=True)
class Employee:
    name: Name
    title: str
    department: str
    birthdate: date
    salary: int
