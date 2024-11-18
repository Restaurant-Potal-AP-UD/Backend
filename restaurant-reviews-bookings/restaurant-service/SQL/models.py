from sqlalchemy import Boolean, Column, DateTime, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from SQL.engine import Base


class Address(Base):
    __tablename__ = "Address"
    id = Column(Integer, primary_key=True)
    street = Column(String(100))
    city = Column(String(100))
    zip_code = Column(Integer)
    location = Column(String(100))
    restaurant_id = Column(Integer, ForeignKey("Restaurant.id"))

    # Relación inversa con Restaurant
    restaurant = relationship("Restaurant", back_populates="addresses")


class Booking(Base):
    __tablename__ = "Booking"
    id = Column(Integer, primary_key=True)
    customer_id = Column(Integer)
    restaurant_id = Column(Integer, ForeignKey("Restaurant.id"))
    booking_date = Column(DateTime)
    people_quantity = Column(Integer, default=0)

    restaurant = relationship("Restaurant", back_populates="bookings")


class Restaurant(Base):
    __tablename__ = "Restaurant"
    id = Column(Integer, primary_key=True)
    restaurant_name = Column(String(80))
    restaurant_owner_id = Column(Integer)
    
    addresses = relationship(
        "Address", back_populates="restaurant", cascade="all, delete"
    )
    bookings = relationship(
        "Booking", back_populates="restaurant", cascade="all, delete"
    )
