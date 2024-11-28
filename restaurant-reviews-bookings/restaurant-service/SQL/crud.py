"""
Module for handling CRUD operations for Address, Restaurant, and Booking entities.

Author: Sebastian Avendaño Rodriguez
Date: 2024/11/07
Description: This module provides functions to create, read, update, and delete records
             related to Address, Restaurant, and Booking tables. It uses SQLAlchemy to
             interact with the database.

"""

from datetime import datetime
from http.client import HTTPException
from typing import List, Optional
import uuid
from SQL.engine import SessionLocal
from SQL.models import Address, Booking, Restaurant

db = SessionLocal()

# ====================================== CREATE ================================== #


def create_address(data: dict):
    """
    Create a new address associated with a specific restaurant.

    Args:
        data (dict): Dictionary containing address details (street, city, zip_code, location, restaurant_id).

    Returns:
        dict: Success message confirming address registration.
    """
    address = Address(
        street=data.get("street"),
        city=data.get("city"),
        zip_code=data.get("zip_code"),
        location=data.get("location"),
        restaurant_id=data.get("restaurant_id"),
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
    restaurant = Restaurant(
        restaurant_name=data.get("restaurant_name"),
        restaurant_owner=uuid.UUID(data.get("user")).bytes,
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
        customer=uuid.UUID(data.get("user")).bytes,
        restaurant_id=data.get("restaurant_id"),
        booking_date=data.get("booking_date"),
        people_quantity=data.get("quantity"),
    )

    db.add(booking)
    db.commit()
    db.refresh(booking)
    return {"success": "The booking has been successfully registered"}


# ====================================== READ ==================================== #


def read_addresses(restaurant_id: int):
    """
    Retrieve all addresses associated with a specific restaurant by its ID.

    Args:
        restaurant_id (int): ID of the restaurant.

    Returns:
        list: List of Address objects for the specified restaurant.
    """
    return db.query(Address).filter(Address.restaurant_id == restaurant_id).all()


def read_bookings(
    user_email: Optional[str] = None, restaurant_id: Optional[int] = None
) -> List[Booking]:
    """
    Retrieve bookings based on a user email or restaurant ID.

    Args:
        user_email (Optional[str]): Email of the user to filter bookings by customer.
        restaurant_id (Optional[int]): ID of the restaurant to filter bookings.

    Returns:
        list: List of Booking objects that match the specified criteria.

    Raises:
        HTTPException: If both user_email and restaurant_id are provided simultaneously.
    """
    if user_email is not None and restaurant_id is not None:
        raise HTTPException(status_code=400, detail="Please, set the correct data")

    if user_email is not None:
        return db.query(Booking).filter(Booking.customer == user_email).all()

    if restaurant_id is not None:
        return db.query(Booking).filter(Booking.restaurant_id == restaurant_id).all()


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


def update_address(data: dict, address_id: int):
    """
    Update address information based on the given address ID.

    Args:
        data (dict): Dictionary containing updated address details (city, location, street, zip_code).
        address_id (int): ID of the address to update.

    Returns:
        dict: Success message confirming the update.
    """
    address = db.query(Address).filter(Address.id == address_id).first()
    if address:
        address.city = data.get("city")
        address.location = data.get("location")
        address.street = data.get("street")
        address.zip_code = data.get("zip_code")

        db.commit()
        db.refresh(address)

        return {"Success": "The information has been changed correctly"}

    raise HTTPException(status_code=404, detail="Address not found")


def update_restaurant(current_user: str, restaurant_name: Optional[str] = None):
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
    if restaurant:
        if restaurant_name is not None:
            restaurant.restaurant_name = restaurant_name
        db.commit()
        db.refresh(restaurant)
        return {"Success": "The information has been changed correctly"}

    raise HTTPException(
        status_code=404, detail="There is no restaurant associated with this account"
    )


def update_booking(
    current_user: str,
    people_quantity: Optional[int] = None,
    booking_date: Optional[datetime] = None,
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
    booking = (
        db.query(Booking)
        .filter(Booking.customer == uuid.UUID(current_user).bytes)
        .first()
    )

    if booking:
        if people_quantity is not None:
            booking.people_quantity = people_quantity
        if booking_date is not None:
            booking.booking_date = booking_date

        db.commit()
        db.refresh(booking)
        return {"Success": "The booking has been updated correctly"}

    raise HTTPException(
        status_code=404, detail="There is no active reservation with your account"
    )


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


def delete_booking(current_user: str):
    """
    Delete a booking associated with the current user (customer).

    Args:
        current_user (str): Email of the customer.

    Returns:
        dict: Success message confirming the deletion.

    Raises:
        HTTPException: If the booking does not exist.
    """
    booking = (
        db.query(Booking)
        .filter(Booking.customer == uuid.UUID(current_user).bytes)
        .first()
    )
    if booking:
        db.delete(booking)
        db.commit()
        return {"Success": "The booking has been deleted correctly"}

    raise HTTPException(status_code=404, detail="There is no booking")


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

    raise HTTPException(status_code=404, detail="There is no restaurant")
