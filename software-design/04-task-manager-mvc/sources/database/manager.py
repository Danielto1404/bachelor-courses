from sqlalchemy.orm import Session

from sources.database.engine import DatabaseORM


class TaskSQLManager:
    def __init__(self, tasks, groups, database: DatabaseORM):
        self.database = database
        self.groups = groups
        self.tasks = tasks
        self.session = None

    def open_session(self):
        self.session = Session(self.database.engine, autocommit=True)

    def insert_group(self, name: str):
        insertion = self.groups \
            .insert() \
            .values(name=name)
        self.session.execute(insertion)

    def get_groups(self):
        return self.session \
            .query(self.groups) \
            .order_by('group_id') \
            .all()

    def delete_group(self, group_id: int):
        deletion = self.tasks \
            .delete() \
            .filter_by(group_id=group_id)
        self.session.execute(deletion)
        deletion = self.groups \
            .delete() \
            .filter_by(group_id=group_id)
        self.session.execute(deletion)

    def delete_task(self, task_id: int):
        deletion = self.tasks \
            .delete() \
            .filter_by(task_id=task_id)
        self.session.execute(deletion)

    def insert_task(self, name, group_id):
        insertion = self.tasks \
            .insert() \
            .values(name=name, group_id=group_id)
        self.session.execute(insertion)

    def change_completion(self, task_id: int, completed: bool):
        self.session.query(self.tasks).filter_by(task_id=task_id).update({
            'completed': completed
        })

    def get_tasks_within_group(self, group_id) -> list:
        return self.session \
            .query(self.tasks) \
            .filter_by(group_id=group_id) \
            .order_by('task_id') \
            .all()
