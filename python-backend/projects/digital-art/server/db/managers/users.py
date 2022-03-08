from typing import Optional, Union

import jwt
import passlib.hash as hashes
from fastapi import Depends, security, HTTPException
from sqlalchemy import orm

import models
import schemas.users
from constants import JWT_SECRET
from db.services import get_db

oauth2schema = security.OAuth2PasswordBearer(tokenUrl="/api/users/auth")


async def get_by_id(
        user_id: int,
        db: orm.Session
) -> Optional[models.User]:
    """
    Retrieves user object from database by given id.
    """
    return db.query(models.User).filter(models.User.id == user_id).first()


async def get_by_login(
        login: str,
        db: orm.Session
) -> Optional[models.User]:
    """
    Retrieves user object from database by given login.
    """
    return db.query(models.User).filter(models.User.login == login).first()


async def authenticate(
        login: str,
        password: str,
        db: orm.Session
) -> Union[bool, models.User]:
    """
    Tries to authenticate user.

    If authentication fails returns `False` otherwise returns `User` object.
    """
    user = await get_by_login(login=login, db=db)
    if user and user.verify_password(password=password):
        return user

    return False


async def create(
        user: schemas.users.UserCreate,
        db: orm.Session
) -> models.User:
    """
    Inserts user into database.
    """
    db_user = models.User(
        login=user.login,
        hashed_password=hashes.bcrypt.hash(user.password)
    )
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user


async def create_token(
        user: models.User
) -> dict:
    """
    Generates token for given user.
    """

    user_schema = schemas.users.User.from_orm(user)

    token = jwt.encode(
        user_schema.dict(),
        JWT_SECRET
    )

    return dict(access_token=token, token_type="bearer")


async def get_current_user(
        db: orm.Session = Depends(get_db),
        token=Depends(oauth2schema)
) -> schemas.users.User:
    """
    Encodes given token to user.
    """
    try:
        payload = jwt.decode(jwt=token, key=JWT_SECRET, algorithms=["HS256"])
        user = db.query(models.User).get(payload["id"])
    except:
        raise HTTPException(status_code=401, detail="Invalid login or password")

    return schemas.users.User.from_orm(user)


async def deposit(
        amount: float,
        login: str,
        db: orm.Session
) -> Union[float, HTTPException]:
    if amount < 0:
        raise HTTPException(status_code=400, detail="Amount cannot be negative")

    user = await get_by_login(login=login, db=db)
    if not user:
        raise HTTPException(status_code=400, detail="User login doesn't exists")

    user.balance += amount
    db.commit()
    return user.balance


async def withdraw(
        amount: float,
        login: str,
        db: orm.Session
) -> Union[float, HTTPException]:
    if amount < 0:
        raise HTTPException(status_code=400, detail="Amount cannot be negative")

    user = await get_by_login(login=login, db=db)
    if not user:
        raise HTTPException(status_code=400, detail="User login doesn't exists")

    if amount > user.balance:
        raise HTTPException(status_code=403, detail="Don't have enough balance")

    user.balance -= amount
    db.commit()
    return user.balance
