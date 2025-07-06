from dataclasses import dataclass, field
from model.employee import Employee


@dataclass
class Department:
    name: str
    employees: list[Employee] = field(default_factory=list)
