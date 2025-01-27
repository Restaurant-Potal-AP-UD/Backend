import datetime
from pydantic import BaseModel, Field


class Token(BaseModel):
    token: str
    payload: dict


class Datetime(BaseModel):
    year: int
    month: int
    day: int
    hour: int
    minute: int
