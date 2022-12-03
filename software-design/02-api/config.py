from pathlib import Path

from utils import read_yaml

project_path = Path(__file__).parent.resolve()

config = read_yaml(project_path / 'config.yaml')
