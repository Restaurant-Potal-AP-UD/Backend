"""
This package provides utilities for verifying JSON Web Tokens (JWT) in a FastAPI application.

It includes a function to decode and verify tokens, checking for expiration and validity.
"""

from fastapi import Depends, HTTPException
from fastapi.security import HTTPBearer
import jwt
from config import SECRET_KEY, ALGORITHM

# Initialize HTTPBearer security scheme for token verification
security = HTTPBearer()


def verify(token: str = Depends(security)) -> dict:
    """

    This method verifies the existence of a correct JWT Token

    Args:
        token (str, optional): _description_. Defaults to Depends(security).

    Raises:
        HTTPException:

    Returns:
        None
    """
    try:
        jwt.decode(token.credentials, SECRET_KEY, algorithms=[ALGORITHM])
        return None
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="Token has expired")
    except jwt.InvalidTokenError:
        raise HTTPException(status_code=401, detail="Invalid token")


def decode_token(token: str = Depends(security)) -> dict:
    """
    Verify the provided JWT token.

    This function decodes the token using the secret key and algorithm specified in the configuration.
    It checks if the token is expired and raises an HTTPException if the token is invalid or expired.

    Args:
        token (str): The JWT token to be verified, obtained from the HTTPBearer dependency.

    Returns:
        dict: The decoded token payload if the token is valid.

    Raises:
        HTTPException: If the token has expired or is invalid, a 401 Unauthorized exception is raised.
    """
    try:
        decoded_token = jwt.decode(
            token.credentials, SECRET_KEY, algorithms=[ALGORITHM]
        )
        return decoded_token
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="Token has expired")
    except jwt.InvalidTokenError:
        raise HTTPException(status_code=401, detail="Invalid token")
