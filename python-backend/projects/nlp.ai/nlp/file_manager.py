from pathlib import Path

project_path = Path(__file__).parent.parent.resolve()
nlp_path = project_path / 'nlp'

generation_path = nlp_path / 'generation'
generation_pretrained_path = generation_path / 'pretrained'

dialog_path = nlp_path / 'dialog'


def resolve(path: str):
    if path.startswith(str(project_path)):
        return path
    else:
        return project_path / path
