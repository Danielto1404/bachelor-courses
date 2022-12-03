from sources.database.engine import DatabaseConfig, DatabaseORM
from sources.database.manager import TaskSQLManager
from sources.models.factory import TasksFactory, GroupsFactory

__all__ = ['task_sql_manager']

db = DatabaseORM(
    DatabaseConfig.real_db_from_url('postgresql://a19378208@localhost/task-manager')
)

Tasks = TasksFactory.build(db.metadata)
Groups = GroupsFactory.build(db.metadata)
db.commit_new_tables()

task_sql_manager = TaskSQLManager(Tasks, Groups, db)
task_sql_manager.open_session()
