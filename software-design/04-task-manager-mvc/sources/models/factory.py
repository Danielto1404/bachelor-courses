from dataclasses import dataclass

from sqlalchemy import String, Column, Integer, ForeignKey, Boolean, Table


@dataclass
class TableFactory:
    name: str
    columns: list

    def build(self, metadata):
        return Table(self.name, metadata, *self.columns)


TasksFactory = TableFactory(
    name='Tasks',
    columns=[
        Column('task_id', Integer, primary_key=True),
        Column('group_id', Integer, ForeignKey('Groups.group_id'), nullable=False),
        Column('name', String(255), nullable=False),
        Column('completed', Boolean, default=False)
    ])

GroupsFactory = TableFactory(
    name='Groups',
    columns=[
        Column('group_id', Integer, primary_key=True),
        Column('name', String(255), nullable=False),
    ]
)
