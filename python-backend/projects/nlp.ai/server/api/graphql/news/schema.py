from datetime import date, timedelta

import graphene
from fastapi import APIRouter
from graphene import String, ObjectType, ID, Field, Date, Boolean, List, Int
from starlette.graphql import GraphQLApp

from server.api.graphql.news.resolvers import get_news_list_resolver

__all__ = ['router']


class NewsAuthor(ObjectType):
    name = String(required=True)
    author_id = ID(required=True)


class NewsContent(ObjectType):
    title = String(required=True)
    text = String(required=True, amount=Int(required=False))

    @staticmethod
    def resolve_text(parent, info, **kwargs):
        text = parent.text
        amount = kwargs.get('amount', len(text))
        return text[:amount]


class News(ObjectType):
    author = Field(NewsAuthor, required=True)
    content = Field(NewsContent, required=True)
    publish_date = Field(Date, required=True)


class NewsList(ObjectType):
    news = List(News, required=True, filter_by_date=Boolean(default_value=False))

    @staticmethod
    def resolve_news(parent, info, filter_by_date):
        if filter_by_date:
            return sorted(news_source, key=lambda x: x.publish_date)

        resolver = get_news_list_resolver(parent, info, filter_by_date)
        return news_source


news_source = [
    News(author=NewsAuthor(name='Victor',
                           author_id=1),
         content=NewsContent(title='First',
                             text='WOW!'),
         publish_date=date.today(),
         ),
    News(author=NewsAuthor(name='Eugene',
                           author_id=2),
         content=NewsContent(title='Second',
                             text='WOW 222!'),
         publish_date=date.today() - timedelta(days=1)
         ),
]

router = APIRouter()
router.add_route("/graphql", GraphQLApp(schema=graphene.Schema(query=NewsList)))
