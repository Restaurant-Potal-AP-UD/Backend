"""
This module represents the user management of the Restaurant service.

Dinneconnect 
Copyright (C) 21/10/2024  Sebastian Avendaño Rodriguez - savendanor@udistrital.edu.co

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

Source: http://opensource.org/licenses/GPL-3.0
"""

from typing import List
from pydantic import BaseModel


class BaseAddress(BaseModel):
    """
    Class representing an address.

    Attributes:
        country (str): Country of the address.
        street (str): Street of the address.
        city (str): City of the address.
        state (str): State of the address.
        zip_code (str): Zip code of the address.
        location (str): Additional location information of the address.
    """

    country: str
    street: str
    city: str
    state: str
    zip_code: int
    location: str


class ShowAddress(BaseAddress):
    """
    Class extending BaseAddress to include an identifier.

    Attributes:
        id (int): Unique identifier for the address.
        All attributes from BaseAddress are inherited.
    """

    id: int


class Booking(BaseModel):
    """
    Class representing a booking at the restaurant.

    Attributes:
        booking_date (str): Date of the booking.
        people (int): Number of people for the booking.
    """

    booking_date: str
    people: int


class BookingUser(Booking):
    """
    Class extending Booking with additional user-specific information.

    Attributes:
        code (int): Unique booking code for reference.
        restaurant_name (str): Name of the restaurant where the booking is made.
        customer (str): Email of the customer making the booking.
        All attributes from Booking are inherited.
    """

    code: int
    restaurant_name: str
    customer: str


class BookingRestaurant(Booking):
    """
    Class extending Booking with restaurant-specific information.

    Attributes:
        code (int): Unique booking code for reference.
        customer (str): Email of the customer who made the booking.
        All attributes from Booking are inherited.
    """

    code: int
    customer: str


class Restaurant(BaseModel):
    """
    Base class representing a restaurant's core information.

    Attributes:
        restaurant_name (str): Name of the restaurant.
        restaurant_owner (str): Owner of the restaurant.
        restaurant_addresses (List[ShowAddress | None]): List of addresses associated with the restaurant.
    """

    restaurant_name: str
    restaurant_owner: str
    restaurant_addresses: List[ShowAddress | None]


class RestaurantAll(Restaurant):
    """
    Class representing complete restaurant information including identifier.

    Attributes:
        restaurant_id (int): Unique identifier for the restaurant.
        All attributes from Restaurant are inherited:
            restaurant_name (str): Name of the restaurant.
            restaurant_owner (str): Owner of the restaurant.
            restaurant_addresses (List[ShowAddress | None]): List of addresses associated with the restaurant.
    """

    restaurant_id: int


class RestaurantUser(Restaurant):
    """
    Class representing restaurant information from a user's perspective, including bookings.

    Attributes:
        restaurant_bookings (List[Booking | None]): List of bookings associated with the restaurant.
        All attributes from Restaurant are inherited:
            restaurant_name (str): Name of the restaurant.
            restaurant_owner (str): Owner of the restaurant.
            restaurant_addresses (List[ShowAddress | None]): List of addresses associated with the restaurant.
    """

    restaurant_bookings: List[Booking | None]
