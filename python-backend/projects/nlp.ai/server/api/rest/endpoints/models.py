from fastapi import APIRouter

import server.schemas.models as scheme_models

router = APIRouter(prefix='/models')


@router.get('/')
async def root():
    return scheme_models.get_root()


@router.get('/all')
async def get_all_models():
    return scheme_models.models()


@router.get("/{model_id}", response_model=scheme_models.Model)
async def get_model(model_id: int):
    return scheme_models.get_model(model_id)


@router.get('/{model_id}/name')
async def get_model_name(model_id: int):
    return scheme_models.get_model_name(model_id)


@router.get('/{model_id}/descriptions')
async def get_model_descriptions(model_id: int):
    return scheme_models.get_model_descriptions(model_id)


@router.post("/add", response_description='Model has been added successfully!')
async def add_model(model: scheme_models.Model):
    scheme_models.add_model(model)


@router.post('/{model_id}/add/description', response_description='Model description has been added successfully!')
async def add_description(model_id: int, description: scheme_models.ModelDescription):
    scheme_models.add_description(model_id, description)
