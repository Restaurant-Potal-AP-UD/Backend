"""
Module for handling CRUD operations for Address, Restaurant, and Booking entities.

Author: Sebastian Avendaño Rodriguez
Date: 2024/11/07
Description: This module provides functions to create, read, update, and delete records
             related to Address, Restaurant, and Booking tables. It uses SQLAlchemy to
             interact with the database.

"""

import uuid
from datetime import datetime
from typing import List, Optional
from http.client import HTTPException
from fastapi.responses import JSONResponse
from SQL.engine import SessionLocal
from SQL.models import Address, Booking, Restaurant
from models.user_management import ShowAddress as ad

db = SessionLocal()

# ====================================== CREATE ================================== #


def create_address(address: Address, location: str, user: str):
    """
    Create a new address associated with a specific restaurant.

    Args:
        data (dict): Dictionary containing address details (street, city, zip_code, location, restaurant_id).

    Returns:
        dict: Success message confirming address registration.
    """

    restaurant = read_restaurant(current_user=user)
    addresses = db.query(Booking).filter(Booking.restaurant_id == restaurant.id).all()

    if address in addresses:
        JSONResponse(status_code=500, content={"detail": "Address already exists"})

    address = Address(
        street=address.street,
        city=address.city,
        state=address.state,
        zip_code=address.zip_code,
        location=location,
        restaurant_id=restaurant.id,
    )
    db.add(address)
    db.commit()
    db.refresh(address)
    return {"success": "The address has been successfully registered"}


def create_restaurant(data: dict):
    """
    Create a new restaurant associated with a specific user (owner).

    Args:
        data (dict): Dictionary containing restaurant details (restaurant_name, user).

    Returns:
        dict: Success message confirming restaurant registration.
    """
    if read_restaurant(data.get("user")) is not None:
        return JSONResponse(
            status_code=400, content={"detail": "Restaurant already exists"}
        )

    else:
        restaurant = Restaurant(
            restaurant_name=data.get("restaurant_name"),
            restaurant_owner=uuid.UUID(data.get("user")).bytes,
            restaurant_owner_name=data.get("username"),
        )
        db.add(restaurant)
        db.commit()
        db.refresh(restaurant)
        return {"success": "The restaurant has been successfully registered"}


def create_booking(data: dict):
    """
    Create a new booking for a specific user and restaurant.

    Args:
        data (dict): Dictionary containing booking details (user, restaurant_id, booking_date, quantity).

    Returns:
        dict: Success message confirming booking registration.
    """

    booking = Booking(
        customer=data.get("user"),
        restaurant_id=data.get("restaurant_id"),
        booking_date=data.get("booking_date"),
        people_quantity=data.get("people_quantity"),
    )

    db.add(booking)
    db.commit()
    db.refresh(booking)
    return {"success": "The booking has been successfully registered"}


# ====================================== READ ==================================== #


def read_addresses(restaurant_id: int) -> List[ad | None]:
    """
    Retrieve all addresses associated with a specific restaurant by its ID.

    Args:
        restaurant_id (int): ID of the restaurant.

    Returns:
        list: List of Address objects for the specified restaurant.
    """
    address = db.query(Address).filter(Address.restaurant_id == restaurant_id).all()
    return address


def read_bookings(
    current_user: Optional[str] = None, restaurant_id: Optional[int] = None
) -> List[Booking] | None:
    """
    Retrieve bookings based on a user email or restaurant ID.

    Args:
        current_user (Optional[str]): Email or user ID to filter bookings by customer.
        restaurant_id (Optional[int]): ID of the restaurant to filter bookings.

    Returns:
        list: List of Booking objects that match the specified criteria.

    Raises:
        HTTPException: If both current_user and restaurant_id are provided simultaneously or neither is provided.
    """
    if current_user is not None and restaurant_id is not None:
        return JSONResponse(
            status_code=400,
            content={"detail": "Error"},
        )

    elif current_user is not None:
        user_bookings = (
            db.query(Booking)
            .filter(Booking.customer == uuid.UUID(current_user).bytes)
            .all()
        )
        return user_bookings if user_bookings else None

    elif restaurant_id is not None:
        restaurant_bookings = (
            db.query(Booking).filter(Booking.restaurant_id == restaurant_id).all()
        )
        return restaurant_bookings if restaurant_bookings else None


def read_restaurant(current_user: str):
    """
    Retrieve the restaurant associated with the current user (owner).

    Args:
        current_user (str): Email of the restaurant owner.

    Returns:
        Restaurant: The Restaurant object for the specified user, or None if not found.
    """
    return (
        db.query(Restaurant)
        .filter(Restaurant.restaurant_owner == uuid.UUID(current_user).bytes)
        .first()
    )


def read_restaurants() -> List[Restaurant]:
    """
    Retrieve all restaurants from the database.

    Returns:
        list: List of all Restaurant objects.
    """
    return list(db.query(Restaurant).all())


# ====================================== UPDATE ================================== #


def update_address(address: Address):
    """
    Update address information based on the given address ID.

    Args:
        data (dict): Dictionary containing updated address details (city, location, street, zip_code).
        address_id (int): ID of the address to update.

    Returns:
        dict: Success message confirming the update.
    """
    address = db.query(Address).filter(Address.id == address.id).first()
    if address:
        address.city = address.city
        address.location = address.location
        address.street = address.street
        address.zip_code = address.zip_code

        db.commit()
        db.refresh(address)

        return {"Success": "The information has been changed correctly"}

    raise HTTPException(status_code=404, detail="Address not found")


def update_restaurant(current_user: str, restaurant_name: str):
    """
    Update restaurant information for the current user (owner).

    Args:
        current_user (str): Email of the restaurant owner.
        restaurant_name (Optional[str]): New name for the restaurant.

    Returns:
        dict: Success message confirming the update.

    Raises:
        HTTPException: If there is no restaurant associated with the account.
    """
    restaurant = (
        db.query(Restaurant)
        .filter(Restaurant.restaurant_owner == uuid.UUID(current_user).bytes)
        .first()
    )

    if restaurant is not None:
        if restaurant.restaurant_name == restaurant_name:
            return JSONResponse(
                status_code=400, content={"error": "Restaurant name is the same"}
            )
        else:
            restaurant.restaurant_name = restaurant_name
            db.commit()
            db.refresh(restaurant)
            return {"Success": "The information has been changed correctly"}

    return JSONResponse(status_code=404, content={"error": "Restaurant not found"})


def update_booking(
    current_user: str,
    booking_id: int,
    people_quantity: int,
    booking_date: datetime,
):
    """
    Update booking information for the current user.

    Args:
        current_user (str): Email of the customer.
        people_quantity (Optional[int]): New quantity of people for the booking.
        booking_date (Optional[datetime]): New date for the booking.

    Returns:
        dict: Success message confirming the update.

    Raises:
        HTTPException: If there is no active reservation with the account.
    """

    booking = db.query(Booking).filter(Booking.id == booking_id).first()

    if booking and booking.customer == uuid.UUID(current_user).bytes:
        if booking.booking_date == booking_date:
            return JSONResponse(status_code=400, content={"error": "Same date"})

        booking.people_quantity = people_quantity
        booking.booking_date = booking_date

        db.commit()
        db.refresh(booking)
        return {"Success": "The booking has been updated correctly"}

    return JSONResponse(status_code=400, content={"detail": "Invalid data"})


# ====================================== DELETE ================================== #


def delete_address(address_id: int):
    """
    Delete an address by its ID.

    Args:
        address_id (int): ID of the address to delete.

    Returns:
        dict: Success message confirming the deletion.

    Raises:
        HTTPException: If the address does not exist.
    """
    address = db.query(Address).filter(Address.id == address_id).first()
    if address:
        db.delete(address)
        db.commit()

        return {
            "Success": "The address has been deleted correctly from your restaurant"
        }

    raise HTTPException(status_code=404, detail="There is no address")


def delete_booking(current_user: str, booking_id: int):
    """
    Delete a booking associated with the current user (customer).

    Args:
        current_user (str): Email of the customer.

    Returns:
        dict: Success message confirming the deletion.

    Raises:
        HTTPException: If the booking does not exist.
    """
    booking = db.query(Booking).filter(Booking.id == booking_id).first()
    if booking and booking.customer == uuid.UUID(current_user).bytes:
        db.delete(booking)
        db.commit()
        return {"Success": "The booking has been deleted correctly"}
    else:
        return JSONResponse(status_code=400, content={"error": "No booking found"})


def delete_restaurant(current_user: str):
    """
    Delete the restaurant associated with the current user (owner).

    Args:
        current_user (str): Email of the restaurant owner.

    Returns:
        dict: Success message confirming the deletion.

    Raises:
        HTTPException: If the restaurant does not exist.
    """
    restaurant = (
        db.query(Restaurant)
        .filter(Restaurant.restaurant_owner == uuid.UUID(current_user).bytes)
        .first()
    )

    if restaurant:
        db.delete(restaurant)
        db.commit()
        return {"Success": "The restaurant has been deleted correctly"}

    return JSONResponse(status_code=400, content={"error": "No restaurant found"})
