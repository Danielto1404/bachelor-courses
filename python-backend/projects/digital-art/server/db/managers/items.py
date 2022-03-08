from typing import Union

from fastapi import HTTPException
from sqlalchemy import orm

import db.managers.users as users_manager
import models
import schemas.items
import schemas.market
from db.managers.owner import get


async def get_all_items(
        user_id: int,
        db: orm.Session
) -> [schemas.items.Item]:
    """
    Retrieves all items which owns specific users.
    """
    items = db.query(models.Item).join(models.Owner) \
        .filter(models.Owner.user_id == user_id) \
        .all()

    return [schemas.items.Item.from_orm(item) for item in items]


async def get_laid_items(
        user_id: int,
        db: orm.Session
) -> [dict]:
    """
    Retrieves all currently laid on market items for specific user.
    """

    items = db.query(models.Item, models.Market).join(models.Owner) \
        .filter(models.Owner.user_id == user_id) \
        .filter(models.Owner.slot_id == models.Market.slot_id) \
        .all()

    return [
        dict(
            item=schemas.items.Item.from_orm(item),
            slot=schemas.market.MarketSlot.from_orm(slot)
        )
        for (item, slot) in items
    ]


async def get_free_items(
        user_id: int,
        db: orm.Session
) -> [schemas.items.Item]:
    items = db.query(models.Item).join(models.Owner) \
        .filter(models.Owner.user_id == user_id) \
        .filter(models.Owner.slot_id == None) \
        .all()

    return [schemas.items.Item.from_orm(item) for item in items]


async def create_item(
        item: schemas.items.ItemCreate,
        user_id: int,
        db: orm.Session
) -> schemas.items.ItemOwners:
    # Add item to item table
    db_item = models.Item(
        title=item.title,
        description=item.description
    )
    db.add(db_item)
    db.commit()
    db.refresh(db_item)

    # Add item owner
    db_owner = models.Owner(
        user_id=user_id,
        item_id=db_item.id
    )

    db.add(db_owner)
    db.commit()
    db.refresh(db_owner)

    return schemas.items.ItemOwners.from_orm(db_owner)


async def delete_item_owner(
        item_id: int,
        user_id: int,
        db: orm.Session
) -> Union[HTTPException, bool]:
    """
    Deletes given item from user.
    returns `True` id item deleted successfully otherwise `HTTPException`
    """
    owner = await get(item_id=item_id, user_id=user_id, db=db)

    if not owner:
        raise HTTPException(status_code=403, detail="Forbidden operation")

    item = db.query(models.Item) \
        .filter(models.Item.id == item_id) \
        .first()

    slot = db.query(models.Market) \
        .filter(models.Market.slot_id == owner.slot_id) \
        .first()

    if slot:
        db.delete(slot)

    db.delete(owner)  # delete from owners
    db.delete(item)  # delete from items
    db.commit()
    return True


async def transfer_item(
        item_id: int,
        from_user_id: int,
        to_user_login: str,
        db: orm.Session
) -> Union[HTTPException, bool]:
    """
    Transfers item from current user to user with given login.
    """
    destination = await users_manager.get_by_login(login=to_user_login, db=db)
    if not destination:
        raise HTTPException(status_code=400, detail="Given user login doesn't exists")

    current_owner = await get(item_id=item_id, user_id=from_user_id, db=db)

    if not current_owner:
        raise HTTPException(status_code=403, detail="Forbidden operation")

    if current_owner.user_id == destination.id:
        raise HTTPException(status_code=403, detail="Can't transfer item to owner account")

    current_owner.user_id = destination.id
    current_owner.slot_id = None
    db.commit()
    db.refresh(current_owner)
    return True
