""" 
This class is the Top interface to access FastAPI application
"""

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from routers.user_restaurant_router import user_restaurant_router
from routers.user_booking_router import user_booking_router
from routers.address_router import address_router
from config import ORIGINS

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=ORIGINS,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(user_restaurant_router)
app.include_router(user_booking_router)
app.include_router(address_router)


@app.get("/")
def home():
    return {"message": "Welcome to the API"}
