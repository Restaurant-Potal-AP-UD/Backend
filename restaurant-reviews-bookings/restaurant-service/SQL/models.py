import uuid
from sqlalchemy import Column, DateTime, Integer, String, ForeignKey, BINARY
from sqlalchemy.orm import relationship
from SQL.engine import Base


class Address(Base):
    __tablename__ = "Address"
    id = Column(BINARY(16), primary_key=True, default=uuid.uuid4())
    street = Column(String(100))
    city = Column(String(100))
    zip_code = Column(Integer)
    location = Column(String(100))
    restaurant_id = Column(BINARY(16), ForeignKey("Restaurant.id"))

    # Relación inversa con Restaurant
    restaurant = relationship("Restaurant", back_populates="addresses")


class Booking(Base):
    __tablename__ = "Booking"
    id = Column(BINARY(16), primary_key=True, default=uuid.uuid4())
    customer = Column(BINARY(16))
    restaurant_id = Column(
        BINARY(16), ForeignKey("Restaurant.id")
    )  # Cambiado a BINARY(16)
    booking_date = Column(DateTime)
    people_quantity = Column(Integer, default=0)

    restaurant = relationship("Restaurant", back_populates="bookings")


class Restaurant(Base):
    __tablename__ = "Restaurant"
    id = Column(BINARY(16), primary_key=True, default=uuid.uuid4())
    restaurant_name = Column(String(80))
    restaurant_owner = Column(BINARY(16))

    addresses = relationship(
        "Address", back_populates="restaurant", cascade="all, delete"
    )
    bookings = relationship(
        "Booking", back_populates="restaurant", cascade="all, delete"
    )
