from sqlalchemy import create_engine, MetaData
from sqlalchemy.orm import DeclarativeMeta


class DatabaseConfig:
    def __init__(self, url, mock: bool = False, echo: bool = False):
        self.url = url
        self.echo = echo
        self.kwargs = {}
        if mock:
            self.kwargs['strategy'] = 'mock'

    @staticmethod
    def mock():
        return DatabaseConfig(url='mock', mock=True)

    @staticmethod
    def real_db_from_url(url):
        return DatabaseConfig(url=url, mock=False)


class DatabaseORM:
    def __init__(self, config: DatabaseConfig):
        self.engine = create_engine(url=config.url, **config.kwargs, echo=config.echo)
        self._metadata = None

    @property
    def metadata(self) -> DeclarativeMeta:
        if self._metadata is None:
            self._metadata = MetaData(bind=self.engine)

        return self._metadata

    def commit_new_tables(self):
        self.metadata.create_all()
