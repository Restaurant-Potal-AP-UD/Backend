"""
This class represents the user management of the Restaurant service.

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
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Source: http://opensource.org/licenses/GPL-3.0
"""

from typing import List


class Address:
    """
    Class representing an address.

    Attributes:
        street (str): The street of the address.
        city (str): The city of the address.
        state (str): The state of the address.
        zip_code (str): The zip code of the address.
    """

    def __init__(self, street: str, city: str, state: str, zip_code: str):
        """
        Constructor for the Address class.

        Args:
            street (str): The street of the address.
            city (str): The city of the address.
            state (str): The state of the address.
            zip_code (str): The zip code of the address.
        """
        self.street = street
        self.city = city
        self.state = state
        self.zip_code = zip_code

    def __str__(self):
        """
        Returns a string representation of the address.

        Returns:
            str: A string representation of the address.
        """
        return f"{self.street}, {self.city}, {self.state} {self.zip_code}"


class Booking:
    """
    Class representing a booking.

    Attributes:
        customer_name (str): The name of the customer making the booking.
        booking_date (str): The date of the booking.
        number_of_people (int): The number of people for the booking.
    """

    def __init__(self, customer_name: str, booking_date: str, number_of_people: int):
        """
        Constructor for the Booking class.

        Args:
            customer_name (str): The name of the customer making the booking.
            booking_date (str): The date of the booking.
            number_of_people (int): The number of people for the booking.
        """
        self.customer_name = customer_name
        self.booking_date = booking_date
        self.number_of_people = number_of_people

    def __str__(self):
        """
        Returns a string representation of the booking.

        Returns:
            str: A string representation of the booking.
        """
        return f"Booking for {self.customer_name} on {self.booking_date} for {self.number_of_people} people."


class Restaurant:
    """
    Class representing a restaurant.

    Attributes:
        restaurant_name (str): The name of the restaurant.
        restaurant_owner (str): The owner of the restaurant.
        restaurant_address (Address): The main address of the restaurant.
        restaurant_addresses (List[Address]): List of additional addresses for the restaurant.
        restaurant_bookings (List[Booking | None]): List of bookings for the restaurant.
    """

    def __init__(
        self,
        restaurant_name: str,
        restaurant_owner: str,
        restaurant_address: Address,
        restaurant_addresses: List[Address] = None,
        restaurant_bookings: List[Booking | None] = None,
    ):
        """
        Constructor for the Restaurant class.

        Args:
            restaurant_name (str): The name of the restaurant.
            restaurant_owner (str): The owner of the restaurant.
            restaurant_address (Address): The main address of the restaurant.
            restaurant_addresses (List[Address], optional): List of additional addresses for the restaurant. Defaults to None.
            restaurant_bookings (List[Booking | None], optional): List of bookings for the restaurant. Defaults to None.

        Raises:
            TypeError: If any of the parameters are not of the expected type.
        """
        if not isinstance(restaurant_name, str):
            raise TypeError("The restaurant name must be a string.")
        if not isinstance(restaurant_owner, str):
            raise TypeError("The restaurant owner must be a string.")
        if not isinstance(restaurant_address, Address):
            raise TypeError(
                "The main address of the restaurant must be an Address object."
            )

        self.restaurant_name = restaurant_name
        self.restaurant_owner = restaurant_owner
        self.restaurant_address = restaurant_address
        self.restaurant_addresses = (
            restaurant_addresses if restaurant_addresses is not None else []
        )
        self.restaurant_bookings = (
            restaurant_bookings if restaurant_bookings is not None else []
        )

    def __str__(self):
        """
        Returns a string representation of the restaurant.

        Returns:
            str: A string representation of the restaurant.
        """
        restaurant_info = f"{self.restaurant_name} - {self.restaurant_owner}\n"
        restaurant_info += f"Main Address: {self.restaurant_address}\n"
        if self.restaurant_addresses:
            restaurant_info += "Additional Addresses:\n"
            for address in self.restaurant_addresses:
                restaurant_info += f"- {address}\n"
        if self.restaurant_bookings:
            restaurant_info += "Bookings:\n"
            for booking in self.restaurant_bookings:
                restaurant_info += f"- {booking}\n"
        return restaurant_info
