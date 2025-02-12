"""
This class will manage all the persistence entities
"""

from .repository import BaseEntity


class Booking(BaseEntity):
    """
    Represents a booking entity in the restaurant service.

    This class manages booking information including customer details,
    date, number of people, and associated restaurant.

    Args:
        customer (str): Email or identifier of the customer making the booking
        booking_date (str): Date and time of the booking
        people (int): Number of people for the booking
        restaurant_name (str): Name of the restaurant where booking is made

    Inherits:
        BaseEntity: Provides basic entity functionality including unique code generation
    """

    def __init__(
        self,
        customer: str,
        booking_date: str,
        people: int,
        restaurant_name: str,
    ):
        super().__init__(
            customer=customer,
            booking_date=booking_date,
            people=people,
            restaurant_name=restaurant_name,
        )


class Address(BaseEntity):
    """
    Represents an address entity for restaurants in the service.

    This class manages detailed address information for restaurant locations.

    Args:
        street (str): Street address
        city (str): City name
        state (str): State or province
        zip_code (int): Postal code
        country (str): Country name
        location (str): Additional location details or reference points
        restaurant_name (str): Name of the associated restaurant

    Inherits:
        BaseEntity: Provides basic entity functionality including unique code generation
    """

    def __init__(
        self,
        street: str,
        city: str,
        state: str,
        zip_code: int,
        country: str,
        location: str,
        restaurant_name: str,
    ):
        super().__init__(
            country=country,
            city=city,
            street=street,
            state=state,
            zip_code=zip_code,
            location=location,
            restaurant_name=restaurant_name,
        )


class Review(BaseEntity):
    """
    Represents a customer review entity for restaurants.

    This class manages customer feedback including ratings and comments.

    Args:
        customer (str): Email or identifier of the reviewing customer
        review_date (str): Date when the review was submitted
        comment (str): Detailed review text
        rating (int): Numerical rating given by the customer
        restaurant_name (str): Name of the reviewed restaurant

    Inherits:
        BaseEntity: Provides basic entity functionality including unique code generation
    """

    def __init__(
        self,
        customer: str,
        review_date: str,
        comment: str,
        rating: int,
        restaurant_name: str,
    ):
        super().__init__(
            customer=customer,
            review_date=review_date,
            comment=comment,
            rating=rating,
            restaurant_name=restaurant_name,
        )


class Information(BaseEntity):
    """
    Represents additional information entity for restaurants.

    This class manages contact and descriptive information for restaurants.

    Args:
        phone_number1 (str): Primary contact number
        phone_number2 (str): Secondary contact number
        email (str): Contact email address
        url_menu (str): URL to the restaurant's menu
        description (str): Detailed description of the restaurant
        restaurant_name (str): Name of the restaurant

    Inherits:
        BaseEntity: Provides basic entity functionality including unique code generation
    """

    def __init__(
        self,
        phone_number1: str,
        phone_number2: str,
        email: str,
        url_menu: str,
        description: str,
        restaurant_name: str,
    ):
        super().__init__(
            phone_number1=phone_number1,
            phone_number2=phone_number2,
            email=email,
            url_menu=url_menu,
            description=description,
            restaurant_name=restaurant_name,
        )


class Restaurant(BaseEntity):
    """
    Represents the main restaurant entity.

    This class manages core restaurant information.

    Args:
        restaurant_name (str): Unique name of the restaurant
        restaurant_owner_name (str): Name of the restaurant owner

    Inherits:
        BaseEntity: Provides basic entity functionality including unique code generation
    """

    def __init__(self, restaurant_name: str, restaurant_owner_name: str):
        super().__init__(
            restaurant_name=restaurant_name,
            restaurant_owner_name=restaurant_owner_name,
        )
