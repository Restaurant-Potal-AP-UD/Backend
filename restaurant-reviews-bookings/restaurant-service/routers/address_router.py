from typing import Annotated
from fastapi import APIRouter, Body, Depends, Path
from fastapi.responses import JSONResponse
from utilities.JWT_utilities import verify, decode_token
from DTO.user_management import BaseAddress, ShowAddress
from DTO.extra_models import Token
from repositories import repository, models
from config import PATHS

address_router = APIRouter()

# ================================= REPOSITORY ============================================== #

address_repository = repository.BaseRepository(PATHS.get("address"))
restaurant_repository = repository.BaseRepository(PATHS.get("restaurant"))

# ================================== GET METHODS ============================================ #


@address_router.get("/api/restaurant/read/address/{restaurant_name}")
def read_address(
    restaurant_name: Annotated[str, Path()], _: Annotated[None, Depends(verify)]
):
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

    return JSONResponse(content={"succes": "Address created"}, status_code=200)


# ================================== DELETE METHODS =========================================== #


@address_router.delete("/api/restaurant/delete/address/{restaurant_name}")
def delete_restaurant_address(
    code: Annotated[int, Body()],
    restaurant_name: Annotated[str, Path()],
    user: Annotated[str, Depends(decode_token)],
):

    if restaurant_repository.get_entity_by_field(
        "restaurant_name", restaurant_name
    ).get("restaurant_owner_name") != user.get("username"):
        return JSONResponse(
            content={"message": "You are not the owner of this restaurant"},
            status_code=404,
        )

    address_val = address_repository.delete_entity_by_code(code=code)
    if address_val.get("success"):
        return JSONResponse(content={"success": "Address deleted succesfully "})
    return JSONResponse(
        content={"error": "something went wrong, please check credentials"}
    )
