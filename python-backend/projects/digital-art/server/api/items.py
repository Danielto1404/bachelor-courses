from fastapi import APIRouter, Depends
from sqlalchemy import orm

import db.managers.items as items_manager
import db.managers.users as users_manager
import schemas.items
from db.services import get_db

router = APIRouter(prefix="/api/items")


@router.get("/")
async def items(
        db: orm.Session = Depends(get_db),
        user: schemas.users.User = Depends(users_manager.get_current_user)
):
    return await items_manager.get_free_items(user_id=user.id, db=db)


@router.get("/laid")
async def laid_items(
        db: orm.Session = Depends(get_db),
        user: schemas.users.User = Depends(users_manager.get_current_user)
):
    return await items_manager.get_laid_items(user_id=user.id, db=db)


@router.post("/create")
async def create_item(
        item: schemas.items.ItemCreate,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await items_manager.create_item(item=item, user_id=user.id, db=db)


@router.delete("/delete/{item_id}")
async def delete_item(
        item_id: int,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await items_manager.delete_item_owner(item_id=item_id, user_id=user.id, db=db)


@router.post("/transfer")
async def transfer_item(
        transfer: schemas.items.ItemTransfer,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await items_manager.transfer_item(
        item_id=transfer.item_id,
        from_user_id=user.id,
        to_user_login=transfer.to_user,
        db=db
    )
