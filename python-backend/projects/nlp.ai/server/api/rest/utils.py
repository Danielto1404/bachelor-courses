import functools

from fastapi import HTTPException


def exception_handler(exception=Exception, status_code=500):
    def wrapper(func):
        @functools.wraps(wrapped=func)
        async def evaluator(*args, **kwargs):
            try:
                return await func(*args, **kwargs)
            except exception as e:
                return HTTPException(status_code=status_code, detail=str(e))

        return evaluator

    return wrapper
