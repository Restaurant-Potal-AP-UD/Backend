from typing import Annotated
from fastapi import APIRouter, Body, Depends, Path
from fastapi.responses import JSONResponse
from utilities.JWT_utilities import verify
from models.user_management import BaseAddress, ShowAddress
from models.extra_models import Token
from SQL.crud import create_address, read_addresses, update_address, delete_address
from sqlalchemy.exc import IntegrityError

address_router = APIRouter()

# ================================== GET METHODS ============================================ #


@address_router.get("/api/restaurant/read/address/{restaurant_id}")
def read_address(
    user: Annotated[Token, Depends(verify)],
    restaurant_id: Annotated[int, Path()],
):
    restaurants = read_addresses(restaurant_id=restaurant_id)
    list_a = []
    for item in restaurants:
        address = ShowAddress(
            id=item.id,
            street=item.street,
            city=item.city,
            state=item.state,
            zip_code=item.state,
        )
        list_a.append(address)

    return list_a


# ================================== POST METHODS =========================================== #


@address_router.post("/api/restaurant/create/address")
def create_restaurant_address(
    address: BaseAddress,
    location: Annotated[str, Body(embed=True)],
    user: Annotated[Token, Depends(verify)],
):
    try:
        int(address.zip_code)
    except ValueError:
        return JSONResponse(
            status_code=400, content={"error": "zip code should be an integer"}
        )
    return create_address(
        address=address,
        location=location,
        user=user.get("sub"),
    )


# ================================== PUT METHODS =========================================== #


@address_router.put("/api/restaurant/update/address")
def update_restaurant_address(
    address: BaseAddress,
    user: Annotated[Token, Depends(verify)],
):
    try:
        return update_address(address=address)
    except IntegrityError:
        return JSONResponse(status_code=400, content={"message": "Incorrect ID"})


# ================================== DELETE METHODS =========================================== #


@address_router.post("/api/restaurant/delete/address")
def delete_restaurant_address(id: int):
    try:
        return delete_address(address_id=id)
    except IntegrityError:
        return JSONResponse(status_code=400, content={"message": "Incorrect ID"})
