from fastapi import APIRouter
import dummy_info as di

user_management_router = APIRouter()


# =========================RESTAURANTS=============================================#


# This method it's open to changes due to the lack of DB connection
@user_management_router.get("/restaurants/")
async def read_restaurants() -> list[dict]:
    fake_info = list(di.generate_fake_restaurant for _ in range(10))
    return {"message": "List of restaurants"}


@user_management_router.post("/restaurant-creation/")
async def create_restaurant():
    return {"message": "User created", "user": user}
