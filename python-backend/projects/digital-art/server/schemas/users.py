import pydantic
from pydantic import constr


class _UserBase(pydantic.BaseModel):
    login: constr(min_length=1)


class UserCreate(_UserBase):
    password: constr(min_length=1)

    class Config:
        orm_mode = True


class User(_UserBase):
    id: int
    balance: float

    class Config:
        orm_mode = True
