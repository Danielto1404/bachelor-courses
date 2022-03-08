from db import database


def create_database():
    """
    :return: Creates database if needed
    """
    return database.Base.metadata.create_all(bind=database.engine)


def get_db():
    """
    Retrieves the database
    """
    db = database.SessionLocal()
    try:
        yield db
    finally:
        db.close()
