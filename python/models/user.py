"""
This class will be a copy and paste of the same class User in java file, this will help to make an
abstract data type for the correct instation of Restaurant class, also will help to analize and 
authorize information from the same class.
"""

import datetime
from pydantic import BaseModel, EmailStr
from Backend.python.models.restaurant import Restaurant


class User:
    __name: str
    __surname: str
    __username: str
    __phone_number: str
    __email: EmailStr
    __password: str
    __creation_date: datetime
    __is_active: bool = False
    __active_reservation: bool = False
    __restaurant: Restaurant

    def __init__(self, name: str, surname: str, username: str):
        self.__name = name
