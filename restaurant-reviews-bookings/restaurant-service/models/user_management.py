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
from pydantic import BaseModel, EmailStr


class Address(BaseModel):
    """
    Class representing an address.

    Attributes:
        street (str): Street of the address.
        city (str): City of the address.
        state (str): State of the address.
        zip_code (str): Zip code of the address.
        location (str): Additional location information of the address.
    """

    street: str
    city: str
    state: str
    zip_code: str
    location: str


class Booking(BaseModel):
    """
    Class representing a booking at the restaurant.

    Attributes:
        customer(str): Email of the customer making the booking.
        booking_date (str): Date of the booking.
        number_of_people (int): Number of people for the booking.
    """

    customer: str
    booking_date: str
    people_quantity: int


class Restaurant(BaseModel):
    """
    Class representing a restaurant.

    Attributes:
        restaurant_name (str): Name of the restaurant.
        restaurant_owner (str): Owner of the restaurant.
        restaurant_address (Address): Main address of the restaurant.
        restaurant_addresses (List[Address | None]): List of additional addresses for the restaurant.
        restaurant_bookings (List[Booking | None]): List of bookings made at the restaurant.
    """

    restaurant_name: str
    restaurant_owner: str
    restaurant_addresses: List[Address | None]
    restaurant_bookings: List[Booking | None]
