from sources.database.manager import TaskSQLManager
from sources.models.objects import TasksGroup, Task


def retrieve_tasks(sql_manager: TaskSQLManager):
    groups = []
    for group in sql_manager.get_groups():
        group_id = group['group_id']
        tasks = sql_manager.get_tasks_within_group(group_id)
        tasks = [
            Task(name=task['name'], task_id=task['task_id'], group_id=task['group_id'], completed=task['completed'])
            for task in tasks]

        groups.append(
            TasksGroup(group_id=group['group_id'], name=group['name'], tasks=tasks)
        )

    return groups
