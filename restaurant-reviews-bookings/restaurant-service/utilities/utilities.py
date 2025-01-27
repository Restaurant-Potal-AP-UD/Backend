import json
from datetime import datetime
from pathlib import Path


def open_json(path: Path, mode: str):
    try:
        with open(path, mode, encoding="utf-8") as file:
            data = json.load(file)
    except json.JSONDecodeError:
        return "something went wrong"


def verify_datetime_format(date_time_str: str) -> bool:
    """
    Verify if a given date and time string matches the format 'YYYY/MM/DD HH:MM'.

    Args:
        date_time_str (str): The date and time as a string.

    Returns:
        bool: True if the date and time are valid and match the expected format,
              False otherwise.
    """
    try:

        datetime.strptime(date_time_str, "%Y/%m/%d/%H:%M")
        return True
    except ValueError:

        return False
