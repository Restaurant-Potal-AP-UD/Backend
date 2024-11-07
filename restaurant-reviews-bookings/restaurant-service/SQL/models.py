from sqlalchemy import Boolean, Column, DateTime, Integer, String
from SQL.engine import Base


class User(Base):
    id = Column(Integer, primary_key=True)
    name = Column(String)
    surname = Column(String)
    email = Column(String)
    password = Column(String)
    creation_date = Column(DateTime)
    reservation = Column(Boolean)
    active = Column(Boolean)
    restaurant_ids = Column(Integer)


class Address(Base):
    __tablename__ = "address"
    id = Column(Integer, primary_key=True)
    street = Column()
    city = Column()
    staticmethodzip_code = Column()
    location = Column()


class Booking(Base):
    __tablename__ = "Booking"
    id = Column(Integer, primary_key=True)
    customer_id = Column(Integer)
    booking_date = Column(DateTime)
    people_quantity = Column(Integer, default=0)


class Restaurant(Base):
    __tablename__ = "Restaurant"
    id = Column(Integer, primary_key=True)
    restaurant_name = Column(String(80))
    restaurant_owner_id = Column(Integer)
    restaurant_addresses = Column(Integer, default=0)
    restaurant_bookings = Column(Integer, default=0)
