import passlib.hash as hashes
import sqlalchemy as sql

from db import database


class User(database.Base):
    __tablename__ = "users"
    id = sql.Column(sql.Integer, primary_key=True, index=True, autoincrement=True)
    login = sql.Column(sql.String(50), index=True, unique=True, nullable=False)
    hashed_password = sql.Column(sql.String, nullable=False)
    balance = sql.Column(sql.Integer, default=0, nullable=False)

    def verify_password(self, password: str):
        return hashes.bcrypt.verify(password, self.hashed_password)


class Item(database.Base):
    __tablename__ = "items"
    id = sql.Column(sql.Integer, primary_key=True, index=True, autoincrement=True)
    title = sql.Column(sql.String, nullable=False)
    description = sql.Column(sql.String, nullable=False)
    image_base64 = sql.Column(sql.BLOB)


class Owner(database.Base):
    __tablename__ = "owners"
    item_id = sql.Column(sql.Integer, sql.ForeignKey("items.id"), index=True, primary_key=True)
    user_id = sql.Column(sql.Integer, sql.ForeignKey("users.id"), index=True, primary_key=True)
    slot_id = sql.Column(sql.Integer)


class Market(database.Base):
    __tablename__ = "market"
    slot_id = sql.Column(sql.Integer, primary_key=True, index=True, autoincrement=True)
    price = sql.Column(sql.Float, nullable=False)
