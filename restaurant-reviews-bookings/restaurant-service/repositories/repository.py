"""
    this class works as a service CRUD module, due to data access generalization, and the 
    open hand to make methods 
    to work with other type of entities without harming the whole function
    Returns:
        _type_: _description_
"""

import json
from pathlib import Path
import random
from typing import List

import sys

sys.path.append(
    "C:/Users/user/Desktop/Proyecto U/Backend/restaurant-reviews-bookings/restaurant-service/repositories"
)


class BaseEntity:
    """
    Base class for all entities in the restaurant service.

    This class provides common functionality for all entities including
    unique code generation and dictionary conversion.

    Args:
        **kwargs: Arbitrary keyword arguments that will become entity attributes

    Attributes:
        code (int): Unique identifier generated randomly
        Additional attributes are dynamically created from kwargs
    """

    def __init__(self, **kwargs):
        self.code: int = random.randint(0, 1000000000000)
        for key, value in kwargs.items():
            setattr(self, key, value)

    def to_dict(self):
        """
        Converts the entity to a dictionary representation.

        Returns:
            dict: Dictionary containing all entity attributes
        """
        return self.__dict__


class BaseRepository:
    """
    Base repository class providing data persistence operations.

    This class handles JSON file-based storage and retrieval of entities.

    Args:
        relative_path (str): Path to the JSON file for data storage

    Attributes:
        __relative_path (Path): Pathlib Path object for the data file
        __data (List): In-memory storage of loaded data
    """

    def __init__(self, relative_path: str):
        self.__relative_path = Path(relative_path)
        self.__data = []
        self.__relative_path.parent.mkdir(parents=True, exist_ok=True)
        self.__load_data()

    def __load_data(self):
        """
        Loads data from the JSON file into memory.
        Handles various potential file operations errors.
        """
        try:
            with open(self.__relative_path, "r", encoding="UTF8") as file:
                self.__data = json.load(file)
        except FileNotFoundError:
            self.__data = []
        except json.JSONDecodeError:
            self.__data = []
        except Exception as e:
            print(f"Unexpected error loading data: {e}")

    def save(self):
        """
        Saves the current state of data to the JSON file.
        Creates necessary directories if they don't exist.
        """
        try:
            self.__relative_path.parent.mkdir(parents=True, exist_ok=True)
            with open(self.__relative_path, "w", encoding="UTF8") as file:
                json.dump(self.__data, file, ensure_ascii=False, indent=4)
        except Exception as e:
            print(f"Unexpected error saving data: {e}")

    def post_entity(self, entity: BaseEntity) -> None:
        """
        Adds a new entity to the repository.

        Args:
            entity (BaseEntity): Entity to be added
        """
        self.__data.append(entity.to_dict())
        self.save()

    def get_entities_by_field(self, field: str, value: str) -> List[dict]:
        """
        Retrieves all entities matching a specific field value.

        Args:
            field (str): Field name to match
            value (str): Value to match against

        Returns:
            List[dict]: List of matching entities
        """
        matches = [i for i in self.__data if i.get(field, "") == value]
        return matches if matches else []

    def get_entities(self) -> List[dict]:
        """
        Retrieves all entities in the repository.

        Returns:
            List[dict]: List of all entities
        """
        return self.__data

    def get_entity_by_code(self, code: int) -> dict:
        """
        Retrieves an entity by its unique code.

        Args:
            code (int): Unique identifier of the entity

        Returns:
            dict: Entity if found, error message if not
        """
        entity = next((i for i in self.__data if i["code"] == code), None)
        return entity if entity else {"error": "Entity not found"}

    def get_entity_by_field(self, field: str, value: str) -> dict:
        """
        Retrieves first entity matching a specific field value.

        Args:
            field (str): Field name to match
            value (str): Value to match against

        Returns:
            dict: First matching entity or None
        """
        entity = next((i for i in self.__data if i.get(field, "") == value), None)
        return entity

    def update_entity(self, code: int, **kwargs) -> dict:
        """
        Updates an entity's attributes by its code.

        Args:
            code (int): Unique identifier of the entity
            **kwargs: New field values to update

        Returns:
            dict: Success status of the operation
        """
        entity = next((i for i in self.__data if i["code"] == code), None)
        if entity:
            for key, value in kwargs.items():
                entity[key] = value
            self.save()
            return {"success": True}
        return {"success": False}

    def delete_entity_by_code(self, code: int) -> dict:
        """
        Deletes an entity by its unique code.

        Args:
            code (int): Unique identifier of the entity

        Returns:
            dict: Success status of the operation
        """
        entity = next((i for i in self.__data if i["code"] == code), None)
        if entity:
            self.__data.remove(entity)
            self.save()
            return {"success": True}
        return {"success": False}

    def delete_entity_by_field(self, field: str, value: str) -> dict:
        """
        Deletes first entity matching a specific field value.

        Args:
            field (str): Field name to match
            value (str): Value to match against

        Returns:
            dict: Success status of the operation
        """
        matches = next(
            (i for i in self.__data if i.get(field, "").lower() == value.lower()), None
        )
        if matches:
            self.__data.remove(matches)
            self.save()
            return {"success": True}
        return {"success": False}
