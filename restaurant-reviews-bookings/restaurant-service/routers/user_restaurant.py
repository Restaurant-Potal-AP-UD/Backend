from typing import Annotated, List
from fastapi import APIRouter, Depends
from SQL.crud import (
    create_restaurant,
    read_restaurant,
    read_restaurants,
    read_addresses,
    read_booking,
)
from models.user_management import Restaurant
from models.extra_models import Token
from utilities.JWT_utilities import verify

user_restaurant_router = APIRouter()

# ============================= GET METHODS ==================================== #


@user_restaurant_router.get("/api/get/restaurant/", response_model=Restaurant)
def get_user_restaurant(user: Annotated[Token, Depends(verify)]):
    restaurant_info = read_restaurant(current_user=user.get("sub"))
    addresses_info = read_addresses(restaurant_info.id)
    bookings_info = read_booking(restaurant_id=restaurant_info.id)

    restaurant = Restaurant(
        restaurant_name=restaurant_info.restaurant_name,
        restaurant_owner=restaurant_info.restaurant_owner,
        restaurant_addresses=addresses_info,
        restaurant_bookings=bookings_info,
    )

    return restaurant


@user_restaurant_router.get("/api/get/restaurant/all", response_model=List[Restaurant])
def get_restaurants():

    list_restaurants = []

    restaurants_info = read_restaurants()

    for item in restaurants_info:
        restaurant = Restaurant(
            restaurant_name=item.restaurant_name,
            restaurant_owner=item.restaurant_owner,
            restaurant_addresses=read_addresses(item.id),
            restaurant_bookings=read_booking(restaurant_id=item.id),
        )

        list_restaurants.append(restaurant)

    return list_restaurants


# ============================= POST METHODS =================================== #


@user_restaurant_router.post("/api/create/restaurant/")
def post_user_restaurant(restaurant_name: str, user: Annotated[Token, Depends(verify)]):

    # Añadir validacion de header, debido a que el frontend envia el "user_id" (debe de estar encriptado)
    # La validacion verifica que el user_id enviado y el user_id en el "Authorization bearer (header)"
    # debe de ser igual

    data = {"restaurant_name": restaurant_name, "user": user.get("sub")}
    return create_restaurant(data=data)


# ============================= UPDATE METHODS ================================= #


def update_user_restauran():
    pass
