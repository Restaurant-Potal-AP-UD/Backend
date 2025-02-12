"""
user_restaurant_router Module


Author: Sebastian Avendaño Rodriguez
Date: 2024/11/26
Description: This module provides API endpoints for managing user restaurant in a restaurant 
            management system. It allows users to create, retrieve, update, and delete 
            their bookings. The endpoints are secured with JWT authentication to ensure that 
            only authenticated users can access their restaurant information.

"""

from typing import Annotated, List
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse
from fastapi import APIRouter, Body, Depends, Path
from DTO.extra_models import Token
from DTO.user_management import (
    RestaurantUser,
    RestaurantAll,
    ShowAddress,
    BookingRestaurant,
)
from utilities.JWT_utilities import decode_token
from repositories import repository, models
from config import PATHS

user_restaurant_router = APIRouter()

# ============================= REPOSITORY ==================================== #

restaurant_repository = repository.BaseRepository(PATHS.get("restaurant"))

# ============================= GET METHODS ==================================== #


@user_restaurant_router.get("/api/get/restaurant/", response_model=RestaurantUser)
def get_user_restaurant(user: Annotated[Token, Depends(decode_token)]):
    """
    Retrieve the restaurant information for the authenticated user.

    Args:
        user (Token): The token containing user information, verified via dependency injection.

    Returns:
        Restaurant: The restaurant information including name, owner, addresses, and bookings.
    """

    restaurant_info = restaurant_repository.get_entities_by_field(
        "restaurant_owner_name", user.get("username")
    )

    if restaurant_info:
        addresses = restaurant_info[0].get("restaurant_addresses")
        bookings = restaurant_info[0].get("restaurant_bookings")
        if addresses is not None:
            addresses_list = [
                ShowAddress(
                    id=address.get("code"),
                    country=address.get("country"),
                    street=address.get("street"),
                    city=address.get("city"),
                    state=address.get("state"),
                    zip_code=address.get("zip_code"),
                    location=address.get("location"),
                )
                for address in addresses
            ]
        addresses_list = []

        if bookings is not None:
            bookings_list = [
                BookingRestaurant(
                    code=booking.get("code"),
                    booking_date=booking.get("booking_date"),
                    people=booking.get("people"),
                    customer=booking.get("customer"),
                )
                for booking in bookings
            ]

        bookings_list = []
        restaurant = RestaurantUser(
            restaurant_name=restaurant_info[0].get("restaurant_name"),
            restaurant_owner=restaurant_info[0].get("restaurant_owner_name"),
            restaurant_addresses=addresses_list,
            restaurant_bookings=bookings_list,
        )
        return restaurant
    else:
        return JSONResponse(
            status_code=404, content={"detail": "User got no restaurant active"}
        )


@user_restaurant_router.get(
    "/api/get/restaurant/all", response_model=List[RestaurantAll]
)
def get_restaurants():
    """
    Retrieve a list of all restaurants.

    Returns:
        List[Restaurant]: List of restaurants with their details, or a JSON response with an error message.
    """
    restaurants_info = restaurant_repository.get_entities()

    if not restaurants_info:
        return JSONResponse(
            status_code=404, content={"detail": "No restaurants found."}
        )

    list_restaurants = []
    for item in restaurants_info:

        addresses_info = item.get("restaurant_addresses") or []

        addresses_list = [
            ShowAddress(
                id=address.get("code"),
                country=address.get("country"),
                street=address.get("street"),
                city=address.get("city"),
                state=address.get("state"),
                zip_code=address.get("zip_code"),
                location=address.get("location"),
            )
            for address in addresses_info
        ]

        restaurant = RestaurantAll(
            restaurant_id=item.get("code"),
            restaurant_name=item.get("restaurant_name"),
            restaurant_owner=item.get("restaurant_owner_name"),
            restaurant_addresses=addresses_list,
        )

        list_restaurants.append(restaurant)

    return list_restaurants


@user_restaurant_router.get("/api/get/restaurant/{restaurant_name}")
def get_restaurant_by_name(restaurant_name: Annotated[str, Path()]):
    """
        This class selects the restaurant by the given name
    Args:
        restaurant_name (Annotated[str, Path): _description_

    Returns:
        JSONResponse
    """
    restaurant = restaurant_repository.get_entity_by_field(
        "restaurant_name", restaurant_name
    )

    if restaurant:
        return JSONResponse(content=jsonable_encoder(restaurant), status_code=200)
    else:
        return JSONResponse(content={"error": "Something Went Wrong"}, status_code=400)


# ============================= POST METHODS =================================== #


@user_restaurant_router.post("/api/create/restaurant/")
def post_user_restaurant(
    restaurant_name: Annotated[str, Body(embed=True)],
    user: Annotated[Token, Depends(decode_token)],
):
    """
    Create a new restaurant for the authenticated user.

    Args:
        restaurant_name (str): The name of the restaurant to be created.
        user (Token): The token containing user information, verified via dependency injection.

    Returns:
        Any: The result of the restaurant creation process.
    """
    restaurant_user = restaurant_repository.get_entities_by_field(
        "restaurant_owner_name", user.get("username")
    )
    if restaurant_user:
        return JSONResponse(
            content={"error": "You have one active restaurant"}, status_code=400
        )
    restaurant_info = models.Restaurant(
        restaurant_name=restaurant_name, restaurant_owner_name=user.get("username")
    )

    restaurant_repository.post_entity(restaurant_info)
    return JSONResponse(content={"success": "Restaurant created"}, status_code=201)


# ============================= DELETE METHODS ==================================== #


@user_restaurant_router.delete("/api/update/restaurant/user/")
def delete_user_restaurant(user: Annotated[Token, Depends(decode_token)]):
    """
    Delete the restaurant for the authenticated user.

    Args:
        user (Token): The token containing user information, verified via dependency injection.

    Returns:
        Any: The result of the restaurant deletion process.
    """
    restaurant_del = restaurant_repository.delete_entity_by_field(
        "restaurant_owner_name", user.get("username")
    )

    if restaurant_del.get("success"):
        return JSONResponse(content={"success": "Restaurant deleted"}, status_code=200)
    return JSONResponse(content={"error": "Restaurant not found"}, status_code=404)
