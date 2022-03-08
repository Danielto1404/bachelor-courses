import pydantic
from pydantic import Field


class _PayableItem(pydantic.BaseModel):
    item_id: int
    price: float = Field(ge=0)


class SellItem(_PayableItem):
    pass


class UpdatePrice(_PayableItem):
    pass


class BuyItem(_PayableItem):
    pass


class MarketSlot(pydantic.BaseModel):
    slot_id: int
    price: float = Field(ge=0)

    class Config:
        orm_mode = True
