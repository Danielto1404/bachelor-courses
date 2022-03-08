from fastapi import APIRouter

from server.constants.text import Generation
from server.schemas.generation import EvaluationConfig, TrainingConfig, run_evaluator, run_trainer

router = APIRouter(prefix='/generation')


@router.get('/')
def root():
    return Generation.WELCOME


@router.post('/evaluate/{arguments}')
# @exception_handler()
def evaluate(config: EvaluationConfig):
    return run_evaluator(config)


@router.post('/train/{arguments}')
# @exception_handler()
def train(config: TrainingConfig):
    vocab, model, trainer, loader = run_trainer(config)
    return 'Model trained successfully & saved at index 1'


train.__doc__ = run_trainer.__doc__
evaluate.__doc__ = run_evaluator.__doc__
