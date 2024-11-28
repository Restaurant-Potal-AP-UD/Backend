"""
This package provides utilities for verifying JSON Web Tokens (JWT) in a FastAPI application.

It includes a function to decode and verify tokens, checking for expiration and validity.
"""

from config import SECRET_KEY, ALGORITHM
from fastapi import Depends, HTTPException
from fastapi.security import HTTPBearer
import jwt

# Initialize HTTPBearer security scheme for token verification
security = HTTPBearer()


def verify(token: str = Depends(security)) -> dict:
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
        # Decode the token
        decoded_token = jwt.decode(
            token.credentials, SECRET_KEY, algorithms=[ALGORITHM]
        )
        return decoded_token
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="Token has expired")
    except jwt.InvalidTokenError:
        raise HTTPException(status_code=401, detail="Invalid token")
