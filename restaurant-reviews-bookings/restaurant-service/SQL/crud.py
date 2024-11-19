"""
Module for handling CRUD operations for Address, Restaurant, and Booking entities.

Author: Sebastian Avendaño Rodriguez
Date: 2024/11/07
Description: This module provides functions to create, read, update, and delete records
             related to Address, Restaurant, and Booking tables. It uses SQLAlchemy to
             interact with the database.

"""

from http.client import HTTPException
from typing import Optional
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
        restaurant_name=data.get("restaurant_name"), restaurant_owner=data.get("user")
    )
    db.add(restaurant)
    db.commit()
    db.refresh(restaurant)
    return {"success": "The restaurant has been successfully registered"}


def create_booking(data: dict):
    """
    Create a new booking for a specific user and restaurant.

    Args:
        data (dict): Dictionary containing booking details (booking_date, quantity).
        user_data (User): The User object representing the customer making the booking.
        restaurant_id (int): ID of the restaurant associated with the booking.

    Returns:
        dict: Success message confirming booking registration.
    """
    booking = Booking(
        customer_id=data.get("user"),
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


def read_booking(
    user_id: Optional[int] = None, restaurant_id: Optional[Booking] = None
):
    """
    Retrieve bookings based on a user ID or restaurant ID.

    Args:
        user_id (Optional[User]): User object to filter bookings by customer ID.
        restaurant_id (Optional[Booking]): Booking object to filter bookings by restaurant ID.

    Returns:
        list: List of Booking objects that match the specified criteria.

    Raises:
        HTTPException: If both user_id and restaurant_id are provided simultaneously.
    """
    if user_id is not None and restaurant_id is not None:
        raise HTTPException(status_code=400, detail="Please, set the correct data")

    if user_id is not None:
        return db.query(Booking).filter(Booking.customer_id == user_id.id).all()

    if restaurant_id is not None:
        return db.query(Booking).filter(Booking.restaurant_id == restaurant_id).all()


def read_restaurant(current_user: str):
    """
    Retrieve the restaurant associated with the current user (owner).

    Args:
        current_user (str): Email object representing the restaurant owner email.

    Returns:
        Restaurant: The Restaurant object for the specified user, or None if not found.
    """
    return (
        db.query(Restaurant).filter(Restaurant.restaurant_owner == current_user).first()
    )


def read_restaurants():
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
    address.city = data.get("city")
    address.location = data.get("location")
    address.street = data.get("street")
    address.zip_code = data.get("zip_code")

    db.commit()
    db.refresh(address)

    return {"Success": "The information has been changed correctly"}


def update_restaurant(current_user: int, restaurant_name: Optional[str] = None):
    """
    Update restaurant information for the current user (owner).

    Args:
        current_user (User): User object representing the restaurant owner.
        restaurant_name (Optional[str]): New name for the restaurant.

    Returns:
        dict: Success message confirming the update.
    """
    restaurant = (
        db.query(Restaurant)
        .filter(Restaurant.restaurant_owner_id == current_user)
        .first()
    )
    restaurant.restaurant_name = restaurant_name
    db.commit()
    db.refresh(restaurant)
    return {"Success": "The information has been changed correctly"}


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


def delete_booking(current_user: int):
    """
    Delete a booking associated with the current user (customer).

    Args:
        current_user (User): User object representing the customer.

    Returns:
        dict: Success message confirming the deletion.

    Raises:
        HTTPException: If the booking does not exist.
    """
    booking = db.query(Booking).filter(Booking.customer_id == current_user).first()
    if booking:
        db.delete(booking)
        db.commit()
        return {"Success": "The booking has been deleted correctly"}

    raise HTTPException(status_code=404, detail="There is no booking")


def delete_restaurant(current_user: int):
    """
    Delete the restaurant associated with the current user (owner).

    Args:
        current_user (User): User object representing the restaurant owner.

    Returns:
        dict: Success message confirming the deletion.

    Raises:
        HTTPException: If the restaurant does not exist.
    """
    restaurant = (
        db.query(Restaurant)
        .filter(Restaurant.restaurant_owner_id == current_user)
        .first()
    )

    if restaurant:
        db.delete(restaurant)
        db.commit()
        return {"Success": "The restaurant has been deleted correctly"}

    raise HTTPException(status_code=404, detail="There is no restaurant")
