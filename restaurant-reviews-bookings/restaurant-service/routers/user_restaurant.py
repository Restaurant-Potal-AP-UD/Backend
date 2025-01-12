"""
user_restaurant_router Module


Author: Sebastian Avendaño Rodriguez
Date: 2024/11/26
Description: This module provides API endpoints for managing user restaurant in a restaurant management system.
            It allows users to create, retrieve, update, and delete their bookings. The endpoints are secured
            with JWT authentication to ensure that only authenticated users can access their restaurant information.

"""

from fastapi.responses import JSONResponse
from typing import Annotated, List
from fastapi.responses import JSONResponse
from fastapi import APIRouter, Body, Depends
from pymysql import IntegrityError
from SQL.crud import *
from models.user_management import (
    Restaurant_all as rta,
    Restaurant_user as rts,
    ShowAddress,
    Booking as bk,
)
from models.extra_models import Token
from utilities.JWT_utilities import verify

user_restaurant_router = APIRouter()

# ============================= GET METHODS ==================================== #


@user_restaurant_router.get("/api/get/restaurant/", response_model=rts)
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

        addresses_info = addresses_info or []
        bookings_info = bookings_info or []

        addresses_list = [
            ShowAddress(
                id=address.id,
                street=address.street,
                city=address.city,
                state=address.state,
                zip_code=str(
                    address.zip_code,
                ),
            )
            for address in addresses_info
        ]

        restaurant = rts(
            restaurant_name=restaurant_info.restaurant_name,
            restaurant_owner=restaurant_info.restaurant_owner_name,
            restaurant_addresses=addresses_list,
            restaurant_bookings=[
                bk(
                    customer=booking.customer,
                    booking_date=booking.booking_date.strftime("%d/%m/%Y %H:%M"),
                    people_quantity=booking.people_quantity,
                )
                for booking in bookings_info
            ],
        )
        return restaurant
    else:
        return JSONResponse(
            status_code=404, content={"detail": "User got no restaurant active"}
        )


@user_restaurant_router.get("/api/get/restaurant/all", response_model=List[rta])
def get_restaurants():
    """
    Retrieve a list of all restaurants.

    Returns:
        List[Restaurant]: List of restaurants with their details, or a JSON response with an error message.
    """
    restaurants_info = read_restaurants()

    if not restaurants_info:
        return JSONResponse(
            status_code=404, content={"detail": "No restaurants found."}
        )

    list_restaurants = []
    for item in restaurants_info:
        # Leer direcciones y reservas
        addresses_info = read_addresses(item.id) or []

        addresses_list = [
            ShowAddress(
                id=address.id,
                street=address.street,
                city=address.city,
                state=address.state,
                zip_code=str(address.zip_code),
            )
            for address in addresses_info
        ]

        restaurant = rta(
            restaurant_id=item.id,
            restaurant_name=item.restaurant_name,
            restaurant_owner=item.restaurant_owner_name,
            restaurant_addresses=addresses_list,
        )

        list_restaurants.append(restaurant)

    return list_restaurants


# ============================= POST METHODS =================================== #


@user_restaurant_router.post("/api/create/restaurant/")
def post_user_restaurant(
    restaurant_name: Annotated[str, Body(embed=True)],
    user: Annotated[Token, Depends(verify)],
):
    """
    Create a new restaurant for the authenticated user.

    Args:
        restaurant_name (str): The name of the restaurant to be created.
        user (Token): The token containing user information, verified via dependency injection.

    Returns:
        Any: The result of the restaurant creation process.
    """
    data = {
        "restaurant_name": restaurant_name,
        "user": user.get("sub"),
        "username": user.get("username"),
    }
    return create_restaurant(data=data)


# ============================= UPDATE METHODS ================================= #


@user_restaurant_router.put("/api/update/restaurant/user/")
def update_user_restaurant(
    user: Annotated[Token, Depends(verify)],
    restaurant_name: Annotated[str, Body(embed=True)],
):
    """
    Update the name of the restaurant for the authenticated user.

    Args:
        user (Token): The token containing user information, verified via dependency injection.
        restaurant_name (str): The new name for the restaurant.

    Returns:
        Any: The result of the restaurant update process.
    """
    try:
        if restaurant_name is None or restaurant_name == "":
            return JSONResponse(
                status_code=404, content={"error": "Restaurant not found"}
            )

        return update_restaurant(
            current_user=user.get("sub"), restaurant_name=restaurant_name
        )
    except IntegrityError:
        return JSONResponse(status_code=400, content={"error": "Invalid ID"})


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
