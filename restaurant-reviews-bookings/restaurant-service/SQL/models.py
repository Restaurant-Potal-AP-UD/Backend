import uuid
from sqlalchemy import Column, DateTime, Integer, String, ForeignKey, BINARY
from sqlalchemy.orm import relationship
from SQL.engine import Base


class Address(Base):
    __tablename__ = "Address"
    id = Column(Integer, primary_key=True, autoincrement=True)
    street = Column(String(100))
    city = Column(String(100))
    state = Column(String(100))
    zip_code = Column(Integer)
    location = Column(String(100))
    restaurant_id = Column(
        Integer, ForeignKey("Restaurant.id")
    )  # Referencia a Restaurant.id

    # Relación inversa con Restaurant
    restaurant = relationship("Restaurant", back_populates="addresses")


class Booking(Base):
    __tablename__ = "Booking"
    id = Column(Integer, primary_key=True, autoincrement=True)
    customer = Column(String(80))
    restaurant_id = Column(Integer, ForeignKey("Restaurant.id"))  # Nueva llave foránea
    booking_date = Column(DateTime)
    people_quantity = Column(Integer)

    # Relación con Restaurant usando la nueva llave foránea
    restaurant = relationship("Restaurant", back_populates="bookings")


class Restaurant(Base):
    __tablename__ = "Restaurant"
    id = Column(Integer, primary_key=True, autoincrement=True)
    restaurant_name = Column(String(80))
    restaurant_owner = Column(BINARY(16))
    restaurant_owner_name = Column(String(80), unique=True)

    addresses = relationship(
        "Address", back_populates="restaurant", cascade="all, delete"
    )

    bookings = relationship(
        "Booking", back_populates="restaurant", cascade="all, delete"
    )
