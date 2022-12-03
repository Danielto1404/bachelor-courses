import yaml


def read_yaml(path, encoding='utf-8'):
    """
    Reads YAML file in a safe way.
    Implemented via `yaml.safe_load` method

    ------ Parameters ------

    path: Path | str
        yaml file path
    encoding: str
        file encoding

    ------ Returns ------
        dict
    """
    with open(path, 'r', encoding=encoding) as stream:
        return yaml.safe_load(stream)
