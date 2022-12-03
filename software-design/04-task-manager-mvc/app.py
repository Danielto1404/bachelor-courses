import uvicorn
from fastapi import FastAPI
from starlette.staticfiles import StaticFiles

from paths import static_path
from sources.controllers.api_controller import api_router

application = FastAPI(
    title="Simple group manager",
    description="Author - https://github.com/Danielto1404",
    version="1.0.0",
)

application.include_router(api_router)
application.mount('/static', StaticFiles(directory=static_path), name="static")

if __name__ == '__main__':
    uvicorn.run(app='app:application', port=2390)
