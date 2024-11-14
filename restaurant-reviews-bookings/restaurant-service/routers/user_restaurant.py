from typing import Annotated, List
from fastapi import APIRouter, Header
from SQL.crud import create_restaurant, read_restaurant, read_restaurants
from models.user_management import Restaurant
from models.extra_models import CommonHeaders

user_restaurant_router = APIRouter()

# ============================= GET METHODS ==================================== #


@user_restaurant_router.get("/api/get/restaurant/{user_id}", response_model=Restaurant)
def get_user_restaurant(user_id: int, headers: Annotated[CommonHeaders, Header()]):

    # Añadir validacion de header, debido a que el frontend envia el "user_id" (debe de estar encriptado)
    # La validacion verifica que el user_id enviado y el user_id en el "Authorization bearer (header)"
    # debe de ser igual

    return read_restaurant(current_user=user_id)


@user_restaurant_router.get("/api/get/restaurant/all", response_model=List[Restaurant])
def get_restaurants():
    return read_restaurants()


# ============================= POST METHODS =================================== #


@user_restaurant_router.post("/api/create/restaurant/{user_id}")
def post_user_restaurant(
    restaurant_name: str, user_id: int, #headers: Annotated[CommonHeaders, Header()]
):

    # Añadir validacion de header, debido a que el frontend envia el "user_id" (debe de estar encriptado)
    # La validacion verifica que el user_id enviado y el user_id en el "Authorization bearer (header)"
    # debe de ser igual

    data = {
        "restaurant_name": restaurant_name,
    }
    return create_restaurant(data=data, user_data=user_id)


# ============================= UPDATE METHODS ================================= #


def update_user_restauran():
    pass
