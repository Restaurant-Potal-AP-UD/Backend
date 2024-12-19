import datetime
from pydantic import BaseModel, Field


class Token(BaseModel):
    token: str
    payload: dict


class Datetime(BaseModel):
    year: int = Field(ge=datetime.datetime.now().year)
    month: int = Field(ge=1, le=12)
    day: int = Field(ge=1, le=31)
    hour: int = Field(ge=0, le=24)
    minute: int = Field(ge=0, le=60)
