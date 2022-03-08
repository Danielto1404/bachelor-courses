from fastapi import APIRouter, HTTPException, Depends, security
from sqlalchemy import orm

import db.managers.users as users_manager
import schemas.users
from db.services import get_db
from schemas.users import UserCreate

router = APIRouter(prefix="/api/users")


@router.post("/")
async def create_user(
        user: UserCreate,
        db: orm.Session = Depends(get_db)
):
    db_user = await users_manager.get_by_login(login=user.login, db=db)
    if db_user:
        raise HTTPException(status_code=400, detail="User already exists")

    user = await users_manager.create(user=user, db=db)
    return await users_manager.create_token(user=user)


@router.post("/deposit/")
async def deposit(
        amount: float,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await users_manager.deposit(amount=amount, login=user.login, db=db)


@router.post("/withdraw")
async def withdraw(
        amount: float,
        user: schemas.users.User = Depends(users_manager.get_current_user),
        db: orm.Session = Depends(get_db)
):
    return await users_manager.withdraw(amount=amount, login=user.login, db=db)


@router.post("/auth")
async def authenticate(
        form_data: security.OAuth2PasswordRequestForm = Depends(),
        db: orm.Session = Depends(get_db)
):
    user = await users_manager.authenticate(
        login=form_data.username,
        password=form_data.password,
        db=db
    )

    if not user:
        raise HTTPException(status_code=401, detail="Invalid credentials")

    return await users_manager.create_token(user=user)


@router.get('/me')
async def get_user(
        user: schemas.users.User = Depends(users_manager.get_current_user),
):
    return user
