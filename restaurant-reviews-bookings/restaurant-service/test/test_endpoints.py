"""
Module for testing FastAPI application routes and functionality using TestClient and pytest.

This module contains test cases for various CRUD operations related to restaurant and booking endpoints, 
including JWT token validation, GET, POST, PUT, DELETE methods, and extraordinary scenarios.
"""

from fastapi.testclient import TestClient
import pytest
from main import app
from SQL.engine import SessionLocal
from sqlalchemy.orm import Session

# Initialize TestClient with predefined headers
client = TestClient(app, headers={"Content-Type": "application/json"})

# Predefined headers for testing JWT tokens
headers_good = {
    "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0ZmUxNjAzOC1jODFhLTRlM2EtYTljMi0yMDdmNGExY2M3ZTMiLCJleHAiOjk5OTk5OTk5OTksImlhdCI6MTcwMDAwMDAwMH0.SudHBpuBEuC0fX4POEzV0azF4dNIZOlnJJEYgBYLIUg"
}

headers_bad = {"Authorization": "Bearer 1182736609hgasdvasjv1.asdasbdhj12314.aksjdhag"}
headers_exp = {
    "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0ZmUxNjAzOC1jODFhLTRlM2EtYTljMi0yMDdmNGExY2M3ZTMiLCJleHAiOjE2OTAwMDAwMDAsImlhdCI6MTcwMDAwMDAwMH0.kMAKJ82LFwlRqnowe8yvM7lYYcx4osf7er_Z_pRoAk4"
}


@pytest.fixture(scope="function")
def db_session():
    """
    Fixture for providing a database session for tests.

    Yields:
        Session: SQLAlchemy session object for interacting with the database.
    """
    session: Session = SessionLocal()
    try:
        yield session
    finally:
        session.rollback()
        session.close()


# ==================================== JWT TESTING =======================================#


def test_token_verify():
    """
    Tests verification of JWT tokens, including valid, invalid, and expired tokens.

    Validates:
        - Unauthorized access with no token (403 status).
        - Unauthorized access with invalid token (401 status).
        - Unauthorized access with expired token (401 status).
    """
    response = client.post("/api/create/restaurant/")
    assert response.status_code == 403

    response = client.post(
        "/api/create/restaurant/",
        headers=headers_bad,
    )
    assert response.status_code == 401
    assert response.json() == {"detail": "Invalid token"}

    response = client.post("/api/create/restaurant/", headers=headers_exp)
    assert response.status_code == 401
    assert response.json() == {"detail": "Token has expired"}


# ==================================== GET TESTING =======================================#


def test_get_restaurant_router():
    """
    Tests GET requests for restaurant-related endpoints.

    Validates:
        - Empty restaurant list for a user (404 status).
        - Fetching all restaurants (200 status).
    """
    response = client.get("/api/get/restaurant/", headers=headers_good)
    assert response.status_code == 404
    assert response.json() == {"detail": "User got no restaurant active"}

    response = client.get("/api/get/restaurant/all", headers=headers_good)
    assert response.status_code == 200


def test_get_booking_router():
    """
    Tests GET requests for booking-related endpoints.

    Validates:
        - Empty booking list for a user (404 status).
        - Empty booking list for a restaurant (404 status).
    """
    response = client.get("/api/get/booking/user", headers=headers_good)
    assert response.status_code == 404
    assert response.json() == {"detail": "There are no active bookings for this user"}

    response = client.get("/api/get/booking/restaurant/1", headers=headers_good)
    assert response.status_code == 404
    assert response.json() == {
        "detail": "There are no active bookings for this restaurant"
    }


# ==================================== POST TESTING =======================================#


def test_post_restaurant_router():
    """
    Tests POST requests for restaurant creation.

    Validates:
        - Successful restaurant creation (200 status).
        - Attempting to create a duplicate restaurant (400 status).
        - Fetching the newly created restaurant (200 status).
    """
    response = client.post(
        "/api/create/restaurant/",
        headers=headers_good,
        json={"restaurant_name": "perra"},
    )

    assert response.status_code == 200
    assert response.json() == {
        "success": "The restaurant has been successfully registered"
    }

    response = client.post(
        "/api/create/restaurant/",
        headers=headers_good,
        json={"restaurant_name": "perra"},
    )

    assert response.status_code == 400
    assert response.json() == {"detail": "Restaurant already exists"}

    """
    Testing the existing new restaurant for the user.
    """

    response = client.get("/api/get/restaurant/", headers=headers_good)
    assert response.status_code == 200


def test_get_address():
    response = client.get("/api/restaurant/read/address/1", headers=headers_good)
    assert response.status_code == 200

    response = client.post(
        "/api/restaurant/create/address",
        headers=headers_good,
        json={
            "address": {
                "street": "123 Main St",
                "city": "Springfield",
                "state": "IL",
                "zip_code": "62704",
            },
            "location": "Near the central park",
            "restaurant_id": 1,
        },
    )
    assert response.status_code == 200
    assert response.json() == {
        "success": "The address has been successfully registered"
    }


def test_post_booking_router():
    """
    Tests POST requests for booking creation.

    Validates:
        - Successful booking creation (200 status).
        - Attempting to create a duplicate booking (400 status).
        - Fetching the newly created booking (200 status).
    """
    response = client.post(
        "/api/post/booking/user",
        headers=headers_good,
        json={
            "restaurant_id": 1,
            "booking_date": {
                "year": 2025,
                "month": 1,
                "day": 30,
                "hour": 19,
                "minute": 30,
            },
            "people_quantity": 4,
        },
    )

    response.status_code == 200
    assert response.json() == {
        "success": "The booking has been successfully registered"
    }

    response = client.post(
        "/api/post/booking/user",
        headers=headers_good,
        json={
            "restaurant_id": 1,
            "booking_date": {
                "year": 2025,
                "month": 1,
                "day": 30,
                "hour": 19,
                "minute": 30,
            },
            "people_quantity": 4,
        },
    )
    assert response.status_code == 400
    assert response.json() == {"detail": "Booking already exists"}

    response = client.get("/api/get/booking/restaurant/1", headers=headers_good)
    assert response.status_code == 200


# ==================================== UPDATE TESTING =======================================#


def test_update_restaurant_router():
    """
    Tests PUT requests for updating restaurant information.

    Validates:
        - Successful update of restaurant name (200 status).
        - Attempting to update with the same name (400 status).
        - Attempting to update a non-existent restaurant (404 status).
    """
    response = client.put(
        "/api/update/restaurant/user/",
        headers=headers_good,
        json={"restaurant_name": "Puta"},
    )

    assert response.status_code == 200
    assert response.json() == {"Success": "The information has been changed correctly"}

    response = client.put(
        "/api/update/restaurant/user/",
        headers=headers_good,
        json={"restaurant_name": "Puta"},
    )

    assert response.status_code == 400
    assert response.json() == {"error": "Restaurant name is the same"}

    response = client.put(
        "/api/update/restaurant/user/",
        headers=headers_good,
        json={"restaurant_name": ""},
    )

    assert response.status_code == 404
    assert response.json() == {"error": "Restaurant not found"}


def test_update_booking_router():
    """
    Tests PUT requests for updating booking information.

    Validates:
        - Successful booking update (200 status).
        - Attempting to update a booking with the same date (400 status).
        - Attempting to update a booking with invalid data (400 status).
    """
    response = client.put(
        "/api/update/booking/user",
        headers=headers_good,
        json={
            "people_quantity": 2,
            "booking_id": 1,
            "booking_date": {
                "year": 2027,
                "month": 6,
                "day": 15,
                "hour": 20,
                "minute": 0,
            },
        },
    )

    assert response.status_code == 200
    assert response.json() == {"Success": "The booking has been updated correctly"}

    response = client.put(
        "/api/update/booking/user",
        headers=headers_good,
        json={
            "people_quantity": 2,
            "booking_id": 1,
            "booking_date": {
                "year": 2027,
                "month": 6,
                "day": 15,
                "hour": 20,
                "minute": 0,
            },
        },
    )

    assert response.status_code == 400
    assert response.json() == {"error": "Same date"}

    response = client.put(
        "/api/update/booking/user",
        headers=headers_good,
        json={
            "people_quantity": 3,
            "booking_id": 2,
            "booking_date": {
                "year": 2028,
                "month": 6,
                "day": 15,
                "hour": 20,
                "minute": 0,
            },
        },
    )

    assert response.status_code == 400
    assert response.json() == {"detail": "Invalid data"}


# ==================================== DELETE TESTING =======================================#


def test_delete_restaurant_router():
    """
    Test deleting a restaurant with valid data:
    - Checks if a restaurant can be deleted successfully.
    - Expects a success response with status code 200 and a success message.

    Test deleting a restaurant when no restaurant exists:
    - Attempts to delete a restaurant again after it has already been deleted.
    - Expects a failure response with status code 400 and an error message indicating "No restaurant found".
    """
    response = client.delete("/api/update/restaurant/user/", headers=headers_good)
    assert response.status_code == 200
    assert response.json() == {"Success": "The restaurant has been deleted correctly"}

    response = client.delete("/api/update/restaurant/user/", headers=headers_good)
    assert response.status_code == 400
    assert response.json() == {"error": "No restaurant found"}


def test_delete_booking_router():
    """
    Test deleting a booking when no booking exists:
    - Attempts to delete a booking using an invalid booking ID or when no booking exists.
    - Expects a failure response with status code 400 and an error message indicating "No booking found".
    """
    response = client.post(
        "/api/delete/booking/user", headers=headers_good, json={"booking_id": 1}
    )

    assert response.status_code == 400
    assert response.json() == {"error": "No booking found"}


# ================================ EXTRAORDINARY TESTING ====================================#


def extraordinary_test():
    """
    Test creating a booking with invalid data:
    - Attempts to create a booking with an invalid restaurant ID or incorrect data.
    - Expects a failure response with status code 400 and an error message indicating "Invalid data".
    """
    response = client.post(
        "/api/post/booking/user",
        headers=headers_good,
        json={
            "restaurant_id": 4,
            "booking_date": {
                "year": 2026,
                "month": 1,
                "day": 30,
                "hour": 19,
                "minute": 30,
            },
            "people_quantity": 4,
        },
    )

    assert response.status_code == 400
    assert response.json() == {"detail": "Invalid data"}
