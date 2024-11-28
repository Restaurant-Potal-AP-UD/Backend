"""
user_restaurant_router Module


Author: Sebastian Avendaño Rodriguez
Date: 2024/11/26
Description: This module provides API endpoints for managing user restaurant in a restaurant management system.
            It allows users to create, retrieve, update, and delete their bookings. The endpoints are secured
            with JWT authentication to ensure that only authenticated users can access their restaurant information.

"""

from typing import Annotated, List
from fastapi.responses import JSONResponse
from fastapi import APIRouter, Depends
from SQL.crud import *
from models.user_management import Restaurant
from models.extra_models import Token
from utilities.JWT_utilities import verify

user_restaurant_router = APIRouter()

# ============================= GET METHODS ==================================== #


@user_restaurant_router.get("/api/get/restaurant/", response_model=Restaurant)
def get_user_restaurant(user: Annotated[Token, Depends(verify)]):
    """
    Retrieve the restaurant information for the authenticated user.

    Args:
        user (Token): The token containing user information, verified via dependency injection.

    Returns:
        Restaurant: The restaurant information including name, owner, addresses, and bookings.
    """

    restaurant_info = read_restaurant(current_user=user.get("sub"))

    if restaurant_info is not None:
        addresses_info = read_addresses(restaurant_info.id)
        bookings_info = read_bookings(restaurant_id=restaurant_info.id)
        restaurant = Restaurant(
            restaurant_name=restaurant_info.restaurant_name,
            restaurant_owner=str(uuid.UUID(bytes=restaurant_info.restaurant_owner)),
            restaurant_addresses=addresses_info,
            restaurant_bookings=bookings_info,
        )

        return restaurant
    else:
        return JSONResponse(
            status_code=404, content={"detail": "User got no restaurant active"}
        )


@user_restaurant_router.get("/api/get/restaurant/all", response_model=List[Restaurant])
def get_restaurants():
    """
    Retrieve a list of all restaurants.

    Returns:
        List[Restaurant]: A list of all restaurants with their names, owners, addresses, and bookings.
    """
    list_restaurants = []

    restaurants_info = read_restaurants()

    for item in restaurants_info:
        restaurant = Restaurant(
            restaurant_name=item.restaurant_name,
            restaurant_owner=str(uuid.UUID(bytes=item.restaurant_owner)),
            restaurant_addresses=read_addresses(item.id),
            restaurant_bookings=read_bookings(restaurant_id=item.id),
        )

        list_restaurants.append(restaurant)

    return list_restaurants


# ============================= POST METHODS =================================== #


@user_restaurant_router.post("/api/create/restaurant/")
def post_user_restaurant(restaurant_name: str, user: Annotated[Token, Depends(verify)]):
    """
    Create a new restaurant for the authenticated user.

    Args:
        restaurant_name (str): The name of the restaurant to be created.
        user (Token): The token containing user information, verified via dependency injection.

    Returns:
        Any: The result of the restaurant creation process.
    """
    data = {"restaurant_name": restaurant_name, "user": user.get("sub")}
    return create_restaurant(data=data)


# ============================= UPDATE METHODS ================================= #


@user_restaurant_router.patch("/api/update/restaurant/user/")
def update_user_restaurant(
    user: Annotated[Token, Depends(verify)], restaurant_name: str
):
    """
    Update the name of the restaurant for the authenticated user.

    Args:
        user (Token): The token containing user information, verified via dependency injection.
        restaurant_name (str): The new name for the restaurant.

    Returns:
        Any: The result of the restaurant update process.
    """
    return update_restaurant(
        current_user=user.get("sub"), restaurant_name=restaurant_name
    )


# ============================= DELETE METHODS ==================================== #


@user_restaurant_router.delete("/api/update/restaurant/user/")
def delete_user_restaurant(user: Annotated[Token, Depends(verify)]):
    """
    Delete the restaurant for the authenticated user.

    Args:
        user (Token): The token containing user information, verified via dependency injection.

    Returns:
        Any: The result of the restaurant deletion process.
    """
    return delete_restaurant(current_user=user.get("sub"))
