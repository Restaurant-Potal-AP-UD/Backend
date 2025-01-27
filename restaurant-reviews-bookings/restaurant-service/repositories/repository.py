import json
from pathlib import Path
import random
from typing import List

import sys

sys.path.append(
    "C:/Users/user/Desktop/Proyecto U/Backend/restaurant-reviews-bookings/restaurant-service/repositories"
)


class BaseEntity:
    def __init__(self, **kwargs):
        self.code: int = random.randint(0, 1000000000000)
        for key, value in kwargs.items():
            setattr(self, key, value)

    def to_dict(self):
        return self.__dict__


class BaseRepository:
    def __init__(self, relative_path: str):
        self.__relative_path = Path(relative_path)
        self.__data = []
        # Create directory if it doesn't exist
        self.__relative_path.parent.mkdir(parents=True, exist_ok=True)
        self.__load_data()

    def __load_data(self):
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
        try:
            self.__relative_path.parent.mkdir(parents=True, exist_ok=True)
            with open(self.__relative_path, "w", encoding="UTF8") as file:
                json.dump(self.__data, file, ensure_ascii=False, indent=4)
        except Exception as e:
            print(f"Unexpected error saving data: {e}")

    def post_entity(self, entity: BaseEntity) -> None:
        self.__data.append(entity.to_dict())
        self.save()
        print(self.__data)

    def get_entities_by_field(self, field: str, value: str) -> List[dict]:
        matches = [i for i in self.__data if i.get(field, "").lower() == value.lower()]
        if matches:
            return matches
        else:
            return []

    def get_entities(self) -> List[dict]:
        return self.__data

    def get_entity_by_code(self, code: int) -> dict:
        entity = next((i for i in self.__data if i["code"] == code), None)
        return entity if entity else {"error": "Entity not found"}

    def get_entity_by_field(self, field: str, value: str):
        entity = next(
            (i for i in self.__data if i.get(field, "").lower() == value.lower()), None
        )
        return entity

    def update_entity(self, code: int, **kwargs) -> dict:
        entity = next((i for i in self.__data if i["code"] == code), None)

        if entity:
            for key, value in kwargs.items():
                entity[key] = value
            self.save()
            return {"success": True}
        else:
            return {"success": False}

    def delete_entity_by_code(self, code: int) -> dict:
        entity = next((i for i in self.__data if i["code"] == code), None)

        if entity:
            self.__data.remove(entity)
            self.save()
            return {"success": True}
        else:
            return {"success": False}

    def delete_entity_by_field(self, field: str, value: str) -> dict:
        matches = next(
            (i for i in self.__data if i.get(field, "").lower() == value.lower()), None
        )
        if matches:
            self.__data.remove(matches)
            self.save()
            return {"success": True}
        return {"success": False}
