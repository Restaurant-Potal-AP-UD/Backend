from fastapi import APIRouter

user_management_router = APIRouter()


# =========================RESTAURANTS=============================================#


@user_management_router.get("/restaurants/")
async def read_users():
    return {"message": "List of restaurants"}


@user_management_router.post("/users/")
async def create_user(user: dict):
    return {"message": "User created", "user": user}
