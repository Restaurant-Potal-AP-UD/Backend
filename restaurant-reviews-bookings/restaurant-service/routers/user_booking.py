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
from fastapi import APIRouter, Depends
from models.user_management import Booking
from models.extra_models import Datetime, Token
from SQL.crud import *
from utilities.JWT_utilities import verify

user_booking_router = APIRouter()

# ============================= GET METHODS ==================================== #


@user_booking_router.get("/api/get/booking/user", response_model=List[Booking])
def get_user_bookings(user: Annotated[Token, Depends(verify)]):
    """
    Retrieve all bookings made by the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.

    Returns:
        List[Booking]: A list of Booking objects representing the user's bookings.
    """
    user_bookings = read_bookings(user_email=user.get("sub"))
    data_list = []
    for i in user_bookings:
        booking = Booking(
            customer=i.customer,
            booking_date=i.booking_date,
            number_of_people=i.people_quantity,
        )
        data_list.append(booking)
    return data_list


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
    data_list = []
    for i in restaurant_bookings:
        booking = Booking(
            customer=i.customer,
            booking_date=i.booking_date,
            number_of_people=i.people_quantity,
        )
        data_list.append(booking)

    return data_list


# ============================= POST METHODS ==================================== #


@user_booking_router.post("/api/post/booking/user")
def post_user_booking(
    user: Annotated[Token, Depends(verify)],
    restaurant_id: int,
    booking_date: Datetime,
    people_quantity: int,
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
    date = datetime.datetime(
        year=booking_date.year,
        month=booking_date.month,
        day=booking_date.day,
        hour=booking_date.hour,
        minute=booking_date.minute,
    )

    data = {
        "user": user.get("sub"),
        "restaurant_id": restaurant_id,
        "booking_date": date,
        "people_quantity": people_quantity,
    }

    return create_booking(data=data)


# ============================= UPDATE METHODS ==================================== #


@user_booking_router.patch("/api/update/booking/user")
def update_user_booking(
    user: Annotated[Token, Depends(verify)],
    booking_date: Optional[Datetime] = None,
    people_quantity: Optional[int] = None,
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
    return update_booking(
        current_user=user.get("sub"),
        booking_date=booking_date,
        people_quantity=people_quantity,
    )


# ============================= DELETE METHODS ==================================== #


@user_booking_router.delete("/api/delete/booking/user")
def delete_user_booking(user: Annotated[Token, Depends(verify)]):
    """
    Delete a booking for the authenticated user.

    Args:
        user (Token): The authenticated user's token, used to identify the user.

    Returns:
        Response: The response from the delete_booking function.
    """
    return delete_booking(current_user=user.get("sub"))
