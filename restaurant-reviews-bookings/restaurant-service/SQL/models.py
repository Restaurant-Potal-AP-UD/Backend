from sqlalchemy import Boolean, Column, DateTime, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from SQL.engine import Base


class User(Base):
    __tablename__ = "User"
    id = Column(Integer, primary_key=True)
    name = Column(String)
    surname = Column(String)
    email = Column(String)
    password = Column(String)
    creation_date = Column(DateTime)
    reservation = Column(Boolean)
    active = Column(Boolean)

    # Relaciones
    bookings = relationship("Booking", back_populates="user", cascade="all, delete")
    restaurants = relationship(
        "Restaurant", back_populates="owner", cascade="all, delete"
    )


class Address(Base):
    __tablename__ = "Address"
    id = Column(Integer, primary_key=True)
    street = Column(String)
    city = Column(String)
    zip_code = Column(Integer)
    location = Column(String)
    restaurant_id = Column(Integer, ForeignKey("Restaurant.id"))

    # Relación inversa con Restaurant
    restaurant = relationship("Restaurant", back_populates="addresses")


class Booking(Base):
    __tablename__ = "Booking"
    id = Column(Integer, primary_key=True)
    customer_id = Column(Integer, ForeignKey("User.id"))
    restaurant_id = Column(Integer, ForeignKey("Restaurant.id"))
    booking_date = Column(DateTime)
    people_quantity = Column(Integer, default=0)

    # Relaciones inversas
    user = relationship("User", back_populates="bookings")
    restaurant = relationship("Restaurant", back_populates="bookings")


class Restaurant(Base):
    __tablename__ = "Restaurant"
    id = Column(Integer, primary_key=True)
    restaurant_name = Column(String(80))
    restaurant_owner_id = Column(Integer, ForeignKey("User.id"))

    # Relaciones
    owner = relationship("User", back_populates="restaurants")
    addresses = relationship(
        "Address", back_populates="restaurant", cascade="all, delete"
    )
    bookings = relationship(
        "Booking", back_populates="restaurant", cascade="all, delete"
    )
