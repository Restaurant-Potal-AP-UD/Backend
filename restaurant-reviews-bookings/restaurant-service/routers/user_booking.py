"""
user_booking_router Module


Author: Sebastian Avendaño Rodriguez
Date: 2024/11/25
Description: This module provides API endpoints for managing user bookings in a restaurant management system.
            It allows users to create, retrieve, update, and delete their bookings. The endpoints are secured
            with JWT authentication to ensure that only authenticated users can access their booking information.

"""

import datetime
from typing import Annotated, List, Optional
from fastapi import APIRouter, Body, Depends
from fastapi.responses import JSONResponse
from models.user_management import Booking as b
from models.extra_models import Datetime, Token
from SQL.crud import *
from utilities.JWT_utilities import verify
from sqlalchemy.exc import IntegrityError

user_booking_router = APIRouter()

# ============================= GET METHODS ==================================== #


@user_booking_router.get("/api/get/booking/user", response_model=List[b])
def get_user_bookings(user: Annotated[Token, Depends(verify)]):
    """
    Retrieve all bookings made by the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.

    Returns:
        List[Booking]: A list of Booking objects representing the user's bookings.
    """
    user_bookings = read_bookings(current_user=user.get("sub"))

    if user_bookings is not None:
        data_list = []
        for i in user_bookings:
            booking = Booking(
                customer=str(uuid.UUID(bytes=i.customer)),
                booking_date=i.booking_date,
                people_quantity=i.people_quantity,
            )
            data_list.append(booking)
        return data_list
    else:
        return JSONResponse(
            status_code=404,
            content={"detail": "There are no active bookings for this user"},
        )


@user_booking_router.get("/api/get/booking/restaurant/{restaurant_id}")
def get_restaurant_bookings(
    user: Annotated[Token, Depends(verify)], restaurant_id: int
):
    """
    Retrieve all bookings for a specific restaurant.

    Args:
        user (Token): The authenticated user's token, used to identify the user.
        restaurant_id (int): The ID of the restaurant for which to retrieve bookings.

    Returns:
        List[Booking]: A list of Booking objects representing the restaurant's bookings.
    """
    restaurant_bookings = read_bookings(restaurant_id=restaurant_id)
    if restaurant_bookings is not None:
        data_list = []
        for i in restaurant_bookings:
            booking = Booking(
                customer=str(uuid.UUID(bytes=i.customer)),
                booking_date=i.booking_date,
                people_quantity=i.people_quantity,
            )
            data_list.append(booking)

        return data_list
    else:
        return JSONResponse(
            status_code=404,
            content={"detail": "There are no active bookings for this restaurant"},
        )


# ============================= POST METHODS ==================================== #


@user_booking_router.post("/api/post/booking/user")
def post_user_booking(
    user: Annotated[Token, Depends(verify)],
    restaurant_id: Annotated[int, Body(embed=True)],
    booking_date: Datetime,
    people_quantity: Annotated[int, Body(embed=True)],
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
        date = datetime(
            year=booking_date.year,
            month=booking_date.month,
            day=booking_date.day,
            hour=booking_date.hour,
            minute=booking_date.minute,
        )

        data = {
            "user": user.get("username"),
            "restaurant_id": restaurant_id,
            "booking_date": date,
            "people_quantity": people_quantity,
        }

        return create_booking(data=data)

    except IntegrityError:
        return JSONResponse(status_code=400, content={"detail": "Invalid data"})


# ============================= UPDATE METHODS ==================================== #


@user_booking_router.put("/api/update/booking/user")
def update_user_booking(
    user: Annotated[Token, Depends(verify)],
    people_quantity: Annotated[int, Body(embed=True)],
    booking_id: Annotated[int, Body(embed=True)],
    booking_date: Datetime,
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
        return update_booking(
            current_user=user.get("sub"),
            booking_id=booking_id,
            booking_date=datetime(
                year=booking_date.year,
                month=booking_date.month,
                day=booking_date.day,
                hour=booking_date.hour,
                minute=booking_date.minute,
            ),
            people_quantity=people_quantity,
        )
    except IntegrityError:
        return JSONResponse(status_code=400, content={"detail": "Invalid data"})


# ============================= DELETE METHODS ==================================== #


@user_booking_router.post("/api/delete/booking/user")
def delete_user_booking(
    user: Annotated[Token, Depends(verify)],
    booking_id: Annotated[int, Body(embed=True)],
):
    """
    Delete a booking for the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.

    Returns:
        Response: The response from the delete_booking function.
    """
    return delete_booking(current_user=user.get("sub"), booking_id=booking_id)
