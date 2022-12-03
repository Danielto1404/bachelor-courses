from fastapi import APIRouter
from fastapi.templating import Jinja2Templates
from starlette.requests import Request
from starlette.responses import HTMLResponse, RedirectResponse

from paths import templates_path
from sources.database.sql import task_sql_manager
from sources.models.mapper import retrieve_tasks

api_router = APIRouter()
templates = Jinja2Templates(templates_path)


def post_back_page():
    return RedirectResponse('/', status_code=302)


@api_router.get('/', response_class=HTMLResponse)
async def index(request: Request):
    data = retrieve_tasks(task_sql_manager)
    return templates.TemplateResponse('index.html', {'request': request, 'groups': data})


@api_router.post('/add-group', response_class=RedirectResponse)
async def add_group(request: Request):
    form = await request.form()
    task_sql_manager.insert_group(name=form['group_name'])
    return post_back_page()


@api_router.post('/remove-group/{group_id}')
async def delete_group(group_id: int):
    task_sql_manager.delete_group(group_id)
    return post_back_page()


@api_router.post('/add-task/{group_id}', response_class=RedirectResponse)
async def add_task(request: Request, group_id: int):
    form = await request.form()
    task_sql_manager.insert_task(name=form['task_name'], group_id=group_id)
    return post_back_page()


@api_router.post('/remove-task/{task_id}', response_class=RedirectResponse)
async def remove_task(task_id: int):
    task_sql_manager.delete_task(task_id=task_id)
    return post_back_page()


@api_router.post('/mark', response_class=RedirectResponse)
async def mark_task(completed: bool, task_id: int):
    task_sql_manager.change_completion(task_id=task_id, completed=completed)
    return post_back_page()
