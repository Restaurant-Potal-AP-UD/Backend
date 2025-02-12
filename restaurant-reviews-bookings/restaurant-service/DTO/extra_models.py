"""This module works as Utils but in models process

"""

from pydantic import BaseModel


class Token(BaseModel):
    """A Pydantic model for managing JWT (JSON Web Token) tokens and their associated payload.

    This model provides a structured way to handle JWT tokens along with their decoded payload
    information. It ensures type safety and validation for token-related operations.

    Attributes:
        token (str): The raw JWT token string. This is typically a base64-encoded string
            containing three parts (header, payload, and signature) separated by dots.
        payload (dict): The decoded payload portion of the JWT token. This dictionary
            contains the claims and other information stored in the token.

    Example:
        >>> token_instance = Token(
        ...     token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        ...     payload={"user_id": 123, "exp": 1634567890}
        ... )
    """

    token: str
    payload: dict


class Datetime(BaseModel):
    """A Pydantic model for representing date and time components.

    This model provides a structured way to handle datetime information by breaking it down
    into its individual components. It ensures type validation for each temporal field.

    Attributes:
        year (int): The year component of the datetime (e.g., 2024).
        month (int): The month component of the datetime (1-12).
        day (int): The day component of the datetime (1-31, depending on month).
        hour (int): The hour component of the datetime in 24-hour format (0-23).
        minute (int): The minute component of the datetime (0-59).

    Example:
        >>> datetime_instance = Datetime(
        ...     year=2024,
        ...     month=2,
        ...     day=11,
        ...     hour=14,
        ...     minute=30
        ... )
    """

    year: int
    month: int
    day: int
    hour: int
    minute: int
