"""
This module sets up the SQLAlchemy connection to a MySQL database, 
including the engine, session, and base class for models.
"""

from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker


# Database URL for SQLAlchemy connection
SQLALCHEMY_DATABASE_URL = "mysql+pymysql://python_user:testing@db:3306/python_db"
# SQLALCHEMY_DATABASE_URL = "mysql+pymysql://python_user:testing@localhost:3306/python_db"

# Create the SQLAlchemy engine
engine = create_engine(SQLALCHEMY_DATABASE_URL)

# Create a configured "Session" class
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# Create a base class for our classes definitions
Base = declarative_base()
