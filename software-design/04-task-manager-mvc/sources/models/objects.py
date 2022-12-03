from dataclasses import dataclass


@dataclass
class Task:
    task_id: int
    group_id: int
    name: str
    completed: bool


@dataclass
class TasksGroup:
    group_id: int
    name: str
    tasks: [Task]
