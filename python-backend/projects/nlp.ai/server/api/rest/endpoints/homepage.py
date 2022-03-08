from fastapi import APIRouter

from server.constants.text import Homepage

router = APIRouter()


@router.get('/')
def root():
    return Homepage.WELCOME
