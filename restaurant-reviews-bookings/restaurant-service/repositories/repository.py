import json
from pathlib import Path
import random
from typing import List, Type
from datetime import datetime


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
            with open(self.__relative_path, "w", encoding="UTF8") as file:
                json.dump(self.__data, file, ensure_ascii=False, indent=4)
        except Exception as e:
            print(f"Unexpected error saving data: {e}")

    def post_entity(self, entity: BaseEntity) -> None:
        self.__data.append(entity.to_dict())
        self.save()

    def get_entities_by_field(self, field: str, value: str) -> List[dict]:
        matches = [i for i in self.__data if i.get(field, "").lower() == value.lower()]
        return (
            matches
            if matches
            else {"error": f"No entities found with {field} = {value}"}
        )

    def get_entity_by_code(self, code: int) -> dict:
        entity = next((i for i in self.__data if i["code"] == code), None)
        return entity if entity else {"error": "Entity not found"}

    def update_entity(self, code: int, **kwargs) -> dict:
        entity = next((i for i in self.__data if i["code"] == code), None)

        if entity:
            for key, value in kwargs.items():
                if value is not None:
                    entity[key] = value
            self.save()
            return {"success": "Entity updated successfully"}
        else:
            return {"error": "Entity not found"}

    def delete_entity_by_code(self, code: int) -> dict:
        entity = next((i for i in self.__data if i["code"] == code), None)

        if entity:
            self.__data.remove(entity)
            self.save()
            return {"success": "Entity deleted successfully"}
        else:
            return {"error": "Entity not found"}
