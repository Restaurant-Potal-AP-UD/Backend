from config import SECRET_KEY, ALGORITHM
from fastapi import Depends, HTTPException
from fastapi.security import HTTPBearer
from models.extra_models import Token
import http.client
import jwt


security = HTTPBearer()


def verify(token: str = Depends(security)) -> dict:
    try:
        # Decodifica el token usando la clave secreta y el algoritmo configurados
        decoded_token = jwt.decode(
            token.credentials, SECRET_KEY, algorithms=[ALGORITHM]
        )
        return decoded_token
    except jwt.ExpiredSignatureError:
        # Excepción si el token ha expirado
        raise HTTPException(status_code=401, detail="Token has expired")
    except jwt.InvalidTokenError:
        # Excepción si el token es inválido
        raise HTTPException(status_code=401, detail="Invalid token")
