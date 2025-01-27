"""
user_booking_router Module


Author: Sebastian Avendaño Rodriguez
Date: 2024/11/25
Description: This module provides API endpoints for managing user bookings in a restaurant management system.
            It allows users to create, retrieve, update, and delete their bookings. The endpoints are secured
            with JWT authentication to ensure that only authenticated users can access their booking information.

"""

import datetime
from typing import Annotated, List
from fastapi import APIRouter, Body, Depends, Path
from fastapi.responses import JSONResponse
from DTO.extra_models import Token
from DTO.user_management import BookingRestaurant, BookingUser, Booking
from utilities.JWT_utilities import verify, decode_token
from utilities.utilities import verify_datetime_format
from repositories import repository, models
from config import PATHS

user_booking_router = APIRouter()
# ================================= REPOSITORY ============================================== #

booking_repository = repository.BaseRepository(PATHS.get("booking"))
restaurant_repository = repository.BaseRepository(PATHS.get("restaurant"))

# ============================= GET METHODS ==================================== #


@user_booking_router.get("/api/get/booking/user", response_model=List[BookingUser])
def get_user_bookings(user: Annotated[Token, Depends(decode_token)]):
    """
    Retrieve all bookings made by the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.

    Returns:
        List[Booking]: A list of Booking objects representing the user's bookings.
    """
    user_bookings = booking_repository.get_entities_by_field(
        "customer", user.get("username")
    )

    if user_bookings is not None:
        data_list = []
        for i in user_bookings:
            booking = BookingUser(
                code=i.get("code"),
                customer=i.get("customer"),
                booking_date=i.get("booking_date"),
                people=i.get("people"),
                restaurant_name=i.get("restaurant_name"),
            )
            data_list.append(booking)
        return data_list
    else:
        return JSONResponse(
            status_code=404,
            content={"detail": "There are no active bookings for this user"},
        )


@user_booking_router.get(
    "/api/get/booking/restaurant/{restaurant_name}",
    response_model=List[BookingRestaurant],
)
def get_restaurant_bookings(
    restaurant_name: Annotated[str, Path()],
    user: Annotated[str, Depends(decode_token)],
):
    """
    Retrieve all bookings for a specific restaurant.

    Args:
        user (Token): The authenticated user's token, used to identify the user.
        restaurant_id (int): The ID of the restaurant for which to retrieve bookings.

    Returns:
        List[Booking]: A list of Booking objects representing the restaurant's bookings.
    """

    restaurant_bookings = booking_repository.get_entities_by_field(
        "restaurant_name", restaurant_name
    )

    restaurant_owner = restaurant_repository.get_entity_by_field(
        "restaurant_owner_name", user.get("username")
    )

    print(restaurant_owner)

    if (
        restaurant_bookings is not None
        and restaurant_owner is not None
        and restaurant_owner.get("restaurant_name") == restaurant_name
    ):
        data_list = []
        for i in restaurant_bookings:
            booking = BookingRestaurant(
                code=i.get("code"),
                customer=i.get("customer"),
                booking_date=i.get("booking_date"),
                people=i.get("people"),
            )
            data_list.append(booking)

        return data_list

    else:
        return JSONResponse(
            status_code=404,
            content={"detail": "Something went wrong"},
        )


# ============================= POST METHODS ==================================== #


@user_booking_router.post("/api/post/booking/user/{restaurant_name}")
def post_user_booking(
    user: Annotated[Token, Depends(decode_token)],
    booking: Booking,
    restaurant_name: Annotated[str, Path()],
):
    """
    Create a new booking for the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.
        restaurant_id (int): The ID of the restaurant for the booking.
        booking_date (Datetime): The date and time of the booking.
        people_quantity (int): The number of people for the booking.

    Returns:
        Response: The response from the create_booking function.
    """
    try:
        if booking.people < 0:
            return JSONResponse(
                status_code=400,
                content={"detail": "People quantity must be greater than 0"},
            )
        if verify_datetime_format(booking.booking_date) is False:
            return JSONResponse(
                status_code=400,
                content={"detail": "please check the booking date information"},
            )

        restaurant = restaurant_repository.get_entity_by_field(
            "restaurant_name", restaurant_name
        )

        if restaurant:
            booking_data = models.Booking(
                user.get("username"),
                booking.booking_date,
                booking.people,
                restaurant_name,
            )
            booking_repository.post_entity(booking_data)
            return JSONResponse(
                status_code=201, content={"detail": "Booking created successfully"}
            )

        return JSONResponse(content={"error": "Restaurant not found"}, status_code=404)

    except Exception as e:
        return JSONResponse(status_code=400, content={"detail": e})


# ============================= UPDATE METHODS ==================================== #


@user_booking_router.put("/api/update/booking/user/{code}")
def update_user_booking(
    _: Annotated[None, Depends(verify)],
    booking: Booking,
    code: Annotated[int, Path()],
):
    """
    Update an existing booking for the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.
        booking_date (Optional[Datetime]): The new date and time for the booking (if any).
        people_quantity (Optional[int]): The new number of people for the booking (if any).

    Returns:
        Response: The response from the update_booking function.
    """
    try:
        if booking.people < 0:
            return JSONResponse(
                status_code=400,
                content={"detail": "People quantity must be greater than 0"},
            )

        if verify_datetime_format(booking.booking_date) is False:
            booking_date = booking_repository.get_entity_by_code(code).get(
                "booking_date"
            )
            booking.booking_date = booking_date

        booking_repository.update_entity(
            code=code,
            **{"people": booking.people, "booking_date": booking.booking_date}
        )

        return JSONResponse(
            content={
                "success": "The booking information have been changed succesfully"
            },
            status_code=200,
        )
    except Exception as e:
        return JSONResponse(status_code=400, content={"detail": e})


# ============================= DELETE METHODS ==================================== #


@user_booking_router.delete("/api/delete/booking/user/{code}")
def delete_user_booking(
    _: Annotated[None, Depends(verify)],
    code: Annotated[int, Path()],
):
    """
    Delete a booking for the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.

    Returns:
        Response: The response from the delete_booking function.
    """
    if booking_repository.delete_entity_by_code(code).get("success"):
        return JSONResponse(
            status_code=200, content={"detail": "Booking deleted successfully"}
        )
    return JSONResponse(status_code=400, content={"detail": "Booking not found"})
