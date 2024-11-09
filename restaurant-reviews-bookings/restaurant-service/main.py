from fastapi import FastAPI
from SQL.models import Restaurant, Address, Booking
from SQL.engine import engine, SessionLocal
from routers.user_restaurant import user_restaurant_router

Restaurant.__table__.create(bind=engine, checkfirst=True)
Booking.__table__.create(bind=engine, checkfirst=True)
Address.__table__.create(bind=engine, checkfirst=True)


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


app = FastAPI()
app.add_api_route(user_restaurant_router)


@app.get("/")
def home():
    return {"message": "Welcome to the API"}
