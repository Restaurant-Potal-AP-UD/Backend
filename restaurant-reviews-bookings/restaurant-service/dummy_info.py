"""

This class it's only for dummy information to test the GET status in every endpoint in this service

"""

from typing import List
from models.user_management import Restaurant
from faker import Faker


faker = Faker()


def generate_fake_booking() -> dict:
    return {
        "customer_name": faker.name(),
        "booking_date": faker.date(),
        "number_of_people": faker.random_int(min=1, max=15),
    }


def generate_fake_address() -> dict:
    return {
        "street": faker.street_name(),
        "city": faker.city(),
        "state": faker.state(),
        "zip_code": faker.zipcode(),
    }


def generate_fake_restaurant(
    addresses: List[dict | None], bookings: List[dict | None]
) -> dict:
    return {
        "restaurant_name": faker.place_name(),
        "restaurant_owner": faker.name(),
        "restaurant_address": faker.address(),
        "restaurant_adresses": addresses,
        "restaurant_bookings": bookings,
    }
