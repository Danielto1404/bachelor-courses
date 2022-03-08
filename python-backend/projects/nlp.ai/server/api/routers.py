from fastapi import APIRouter

from server.api.graphql.news import schema as news_schema
from server.api.rest.endpoints import models, homepage, dialog, generation

api_router = APIRouter()
api_router.include_router(homepage.router, tags=['homepage'])
api_router.include_router(models.router, tags=['models'])
api_router.include_router(generation.router, tags=['generation'])
api_router.include_router(dialog.router, tags=['train'])
api_router.include_router(news_schema.router, tags=['news'])
