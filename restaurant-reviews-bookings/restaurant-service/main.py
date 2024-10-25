from fastapi import FastAPI
from routers.user_management_router import user_management_router  # Ajusta el import

app = FastAPI()

app.include_router(user_management_router, tags=["users"])


@app.get("/")
def home():
    return {"message": "Welcome to the API"}
