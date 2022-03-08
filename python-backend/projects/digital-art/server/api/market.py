from fastapi import APIRouter, Depends
from sqlalchemy import orm

import db.managers.market as market_manager
import db.managers.users as users_manager
import schemas.users
from db.services import get_db
from schemas.market import SellItem, UpdatePrice

router = APIRouter(prefix="/api/market")


@router.get("/")
async def on_sale(
        _: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await market_manager.get_all_items(db=db)


@router.post("/sell")
async def sell(
        sell_item: SellItem,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await market_manager.sell(
        item_id=sell_item.item_id,
        user_id=user.id,
        price=sell_item.price,
        db=db
    )


@router.post("/buy/{item_id}")
async def buy(
        item_id: int,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await market_manager.buy(item_id=item_id, buyer_id=user.id, db=db)


@router.delete("/delete/{item_id}")
async def delete_from_market(
        item_id: int,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await market_manager.delete_from_market(item_id=item_id, user_id=user.id, db=db)


@router.post("/update")
async def change_price(
        payment: UpdatePrice,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await market_manager.update_price(
        item_id=payment.item_id,
        new_price=payment.price,
        user_id=user.id,
        db=db
    )
