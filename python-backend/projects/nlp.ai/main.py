import uvicorn
from fastapi import FastAPI

from server.api.routers import api_router
from server.utils import read_yaml

app = FastAPI(
    title="nlp.ai web application",
    description="Author - https://github.com/Danielto1404",
    version="0.1",
)

config = read_yaml('config.yml')
app.include_router(api_router)

if __name__ == '__main__':
    uvicorn.run(
        app='main:app',
        host=config['server']['host'],
        port=config['server']['port'],
        log_level=config['server']['log_level'],
        reload=config['server']['reload']
    )
