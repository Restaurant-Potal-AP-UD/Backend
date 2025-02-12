"""
Module: address_router

This module provides API endpoints for managing restaurant addresses.  
It allows retrieving, creating, and deleting restaurant addresses while  
ensuring proper authentication and authorization.

Dependencies:
    - FastAPI: APIRouter, Body, Depends, Path
    - JSONResponse for structured HTTP responses
    - JWT utilities: verify, decode_token
    - DTO models: BaseAddress, ShowAddress
    - Repository Pattern: BaseRepository for CRUD operations
    - Configuration: PATHS for managing database paths

Repositories:
    - address_repository: Handles CRUD operations for addresses.
    - restaurant_repository: Handles restaurant-related queries.

API Routes:
    - GET `/api/restaurant/read/address/{restaurant_name}`
    - POST `/api/restaurant/create/address/{restaurant_name}`
    - DELETE `/api/restaurant/delete/address/{restaurant_name}`
"""

from typing import Annotated
from fastapi import APIRouter, Body, Depends, Path
from fastapi.responses import JSONResponse
from ..utilities.JWT_utilities import verify, decode_token
from ..DTO.user_management import BaseAddress, ShowAddress
from ..repositories import repository, models
from ..config import PATHS

address_router = APIRouter()

# ================================= REPOSITORY ============================================== #

address_repository = repository.BaseRepository(PATHS.get("address"))
restaurant_repository = repository.BaseRepository(PATHS.get("restaurant"))

# ================================== GET METHODS ============================================ #


@address_router.get("/api/restaurant/read/address/{restaurant_name}")
def read_address(
    restaurant_name: Annotated[str, Path()], _: Annotated[None, Depends(verify)]
):
    """
    Retrieve all addresses associated with a restaurant.

    Args:
        restaurant_name (str): The name of the restaurant.
        _ (None): Dependency injection for authentication verification.

    Returns:
        JSONResponse: A list of ShowAddress objects if addresses are found,
        otherwise a 404 error message.
    """
    restaurants = address_repository.get_entities_by_field(
        "restaurant_name", restaurant_name
    )

    if restaurants is None:
        return JSONResponse(content={"message": "No addresses found"}, status_code=404)

    list_a = []
    for item in restaurants:
        address = ShowAddress(
            id=item.get("code"),
            street=item.get("street"),
            city=item.get("city"),
            state=item.get("state"),
            zip_code=item.get("zip_code"),
            country=item.get("country"),
            location=item.get("location"),
        )
        list_a.append(address)
    return list_a


# ================================== POST METHODS =========================================== #


@address_router.post("/api/restaurant/create/address/{restaurant_name}")
def create_restaurant_address(
    address: BaseAddress,
    user: Annotated[str, Depends(decode_token)],
    restaurant_name: Annotated[str, Path()],
):
    """
    Create a new address for a restaurant.

    Args:
        address (BaseAddress): The address details provided in the request body.
        user (str): The authenticated user's data extracted from the JWT token.
        restaurant_name (str): The name of the restaurant.

    Returns:
        JSONResponse: Success message if the address is created,
        otherwise an error message with appropriate HTTP status.
    """
    if address.zip_code < 0:
        return JSONResponse(
            content={"message": "Zip code must be a positive number"}, status_code=400
        )

    if (
        restaurant_repository.get_entity_by_field("restaurant_name", restaurant_name)
        is None
    ):
        return JSONResponse(
            content={"message": "Restaurant name is required"}, status_code=400
        )

    if restaurant_repository.get_entity_by_field(
        "restaurant_name", restaurant_name
    ).get("restaurant_owner_name") != user.get("username"):
        return JSONResponse(
            content={"message": "You are not the owner of this restaurant"},
            status_code=401,
        )

    address_repository.post_entity(
        models.Address(
            street=address.street,
            city=address.city,
            state=address.state,
            zip_code=int(address.zip_code),
            location=address.location,
            country=address.country,
            restaurant_name=restaurant_name,
        )
    )

    return JSONResponse(content={"success": "Address created"}, status_code=200)


# ================================== DELETE METHODS =========================================== #


@address_router.delete("/api/restaurant/delete/address/{restaurant_name}")
def delete_restaurant_address(
    code: Annotated[str, Body()],
    restaurant_name: Annotated[str, Path()],
    user: Annotated[str, Depends(decode_token)],
):
    """
    Delete an address associated with a restaurant.

    Args:
        code (str): The unique code of the address to be deleted.
        restaurant_name (str): The name of the restaurant.
        user (str): The authenticated user's data extracted from the JWT token.

    Returns:
        JSONResponse: Success message if the address is deleted,
        otherwise an error message with appropriate HTTP status.
    """
    if restaurant_repository.get_entity_by_field(
        "restaurant_name", restaurant_name
    ).get("restaurant_owner_name") != user.get("username"):
        return JSONResponse(
            content={"message": "You are not the owner of this restaurant"},
            status_code=401,
        )

    address_val = address_repository.delete_entity_by_code(code=int(code))
    if address_val.get("success"):
        return JSONResponse(content={"success": "Address deleted successfully"})
    return JSONResponse(
        content={"error": "Something went wrong, please check credentials"}
    )
