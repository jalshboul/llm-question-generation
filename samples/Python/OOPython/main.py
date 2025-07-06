"""
Singleton

One and one instance only

__new__ <- constructor
"""
from database import Database

db = Database()
print(db.departments)
