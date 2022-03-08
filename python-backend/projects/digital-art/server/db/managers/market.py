from typing import Union

from fastapi import HTTPException
from sqlalchemy import orm

import db.managers.owner as owners_manager
import db.managers.users as users_manager
import models
import schemas.items
import schemas.market


async def get_slot(
        slot_id: int,
        db: orm.Session
) -> models.Market:
    return db.query(models.Market) \
        .filter(models.Market.slot_id == slot_id) \
        .first()


async def get_owner_slot_by_item(
        item_id: int,
        db: orm.Session
) -> Union[HTTPException, tuple[models.Owner, models.Market]]:
    owner = await owners_manager.get_by_item(item_id=item_id, db=db)
    if not owner:
        raise HTTPException(status_code=403, detail="Forbidden operation")

    slot = await get_slot(slot_id=owner.slot_id, db=db)
    if not slot:
        raise HTTPException(status_code=403, detail="Item not laid on market")

    return owner, slot


async def get_owner_slot(
        item_id: int,
        user_id: int,
        db: orm.Session
) -> Union[HTTPException, tuple[models.Owner, models.Market]]:
    owner = await owners_manager.get(item_id=item_id, user_id=user_id, db=db)
    if not owner:
        raise HTTPException(status_code=403, detail="Forbidden operation")

    slot = await get_slot(slot_id=owner.slot_id, db=db)
    if not slot:
        raise HTTPException(status_code=403, detail="Item not laid on market")

    return owner, slot


async def get_all_items(
        db: orm.Session
) -> [dict]:
    market_items = db.query(models.Item, models.Market).join(models.Owner) \
        .filter(models.Owner.slot_id == models.Market.slot_id) \
        .all()

    return [
        dict(
            item=schemas.items.Item.from_orm(item),
            slot=schemas.market.MarketSlot.from_orm(slot)
        )
        for (item, slot) in market_items
    ]


async def sell(
        item_id: int,
        user_id: int,
        price: float,
        db: orm.Session
) -> Union[HTTPException, dict]:
    owner = await owners_manager.get(item_id=item_id, user_id=user_id, db=db)
    if not owner or owner.slot_id:
        raise HTTPException(status_code=403, detail="Invalid operation")

    market_slot = models.Market(price=price)
    db.add(market_slot)
    db.commit()
    db.refresh(market_slot)

    owner.slot_id = market_slot.slot_id
    db.commit()
    return dict(slot_id=owner.slot_id)


async def delete_from_market(
        item_id: int,
        user_id: int,
        db: orm.Session
) -> Union[HTTPException, bool]:
    owner, slot = await get_owner_slot(item_id=item_id, user_id=user_id, db=db)
    owner.slot_id = None
    db.delete(slot)
    db.commit()
    return True


async def buy(
        item_id: int,
        buyer_id: int,
        db: orm.Session
) -> Union[HTTPException, bool]:
    owner, slot = await get_owner_slot_by_item(item_id=item_id, db=db)
    buyer = await users_manager.get_by_id(user_id=buyer_id, db=db)
    seller = await users_manager.get_by_id(user_id=owner.user_id, db=db)

    if not buyer or not seller:
        raise HTTPException(status_code=401, detail="Check authentication")

    if buyer_id == seller.id:
        raise HTTPException(status_code=403, detail="Can't buy item from same user")

    price = slot.price

    await users_manager.withdraw(amount=price, login=buyer.login, db=db)
    await users_manager.deposit(amount=price, login=seller.login, db=db)

    owner.user_id = buyer_id
    owner.slot_id = None
    db.delete(slot)
    db.commit()

    return True


async def update_price(
        item_id: int,
        user_id: int,
        new_price: float,
        db: orm.Session
) -> float:
    _, slot = await get_owner_slot(item_id=item_id, user_id=user_id, db=db)
    slot.price = new_price
    db.commit()
    db.refresh(slot)
    return slot.price
