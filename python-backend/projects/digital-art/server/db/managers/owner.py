from typing import Optional

from sqlalchemy import orm

import models


async def get(
        item_id: int,
        user_id: int,
        db: orm.Session
) -> Optional[models.Owner]:
    return db.query(models.Owner) \
        .filter(models.Owner.user_id == user_id) \
        .filter(models.Owner.item_id == item_id) \
        .first()


async def get_by_item(
        item_id: int,
        db: orm.Session
) -> Optional[models.Owner]:
    return db.query(models.Owner) \
        .filter(models.Owner.item_id == item_id) \
        .first()
