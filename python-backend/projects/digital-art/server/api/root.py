from fastapi import APIRouter

router = APIRouter(prefix="/api")


@router.get("/")
def get_info():
    return "Welcome to Digital Art market place"
