from pathlib import Path


def open_json(path: Path, mode: str):
    try:
        with open(path, mode, encoding="utf-8") as file:
            data = json.load(file)
    except json.JSONDecodeError:
        return "something went wrong"
