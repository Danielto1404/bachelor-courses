from pathlib import Path

project_path = Path(__file__).parent.resolve()
sources_path = project_path / 'sources'
resources_path = sources_path / 'resources'
templates_path = resources_path / 'templates'
static_path = resources_path / 'static'
models_path = sources_path / 'models'
controllers_path = sources_path / 'controllers'
test = project_path / 'test'
