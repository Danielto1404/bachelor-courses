import pydantic
from pydantic import constr


class _ItemBase(pydantic.BaseModel):
    title: constr(min_length=1)
    description: constr(min_length=1)


class ItemCreate(_ItemBase):
    pass


class Item(_ItemBase):
    id: int

    class Config:
        orm_mode = True


class ItemTransfer(pydantic.BaseModel):
    item_id: int
    to_user: str


class ItemOwners(pydantic.BaseModel):
    item_id: int
    user_id: int

    class Config:
        orm_mode = True
