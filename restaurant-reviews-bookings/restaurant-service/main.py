from fastapi import FastAPI
from SQL import models
from SQL.engine import engine, SessionLocal
from routers.user_restaurant import user_restaurant_router

models.Base.metadata.create_all(bind=engine)

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

app.include_router(user_restaurant_router)


@app.get("/")
def home():
    return {"message": "Welcome to the API"}
