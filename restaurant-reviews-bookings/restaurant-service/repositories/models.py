from pathlib import Path
from repository import BaseEntity, BaseRepository


class Restaurant(BaseEntity):
    def __init__(self, restaurant_name: str, restaurant_owner_name: str):
        super().__init__(
            restaurant_name=restaurant_name, restaurant_owner_name=restaurant_owner_name
        )


class Booking:

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


add = BaseRepository(
    "Backend/restaurant-reviews-bookings/restaurant-service/persistence/address.json"
)
print(add.get_entity_by_code(52962509004))
