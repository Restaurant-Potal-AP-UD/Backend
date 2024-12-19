from fastapi import FastAPI
from SQL import models
from SQL.engine import engine, SessionLocal
from routers.user_restaurant import user_restaurant_router
from routers.user_booking import user_booking_router
from routers.address_router import address_router


def get_db():
    """
    Dependency to provide a database session.

    Yields:
        db (SessionLocal): Database session.
    """
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


models.Base.metadata.create_all(bind=engine)

app = FastAPI()

app.include_router(user_restaurant_router)
app.include_router(user_booking_router)
app.include_router(address_router)


@app.get("/")
def home():
    return {"message": "Welcome to the API"}
