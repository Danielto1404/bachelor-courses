from fastapi import APIRouter

from server.constants.text import Dialog

router = APIRouter(prefix='/dialog')


@router.get('/')
def root():
    return Dialog.WELCOME
