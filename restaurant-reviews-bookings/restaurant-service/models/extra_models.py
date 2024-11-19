from pydantic import BaseModel


class Token(BaseModel):
    token: str
    payload: dict
