"""
This class manage a simple verification
"""

from datetime import datetime


def verify_datetime_format(date_time_str: str) -> bool:
    """
    Verify if a given date and time string matches the format 'YYYY/MM/DD/HH:MM'.

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
