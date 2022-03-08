import uvicorn
from fastapi import FastAPI

from api import users, root, items, market
from db.services import create_database

app = FastAPI()

app.include_router(root.router)
app.include_router(users.router)
app.include_router(items.router)
app.include_router(market.router)

if __name__ == '__main__':
    create_database()
    uvicorn.run('server:app', port=8001, reload=True)
