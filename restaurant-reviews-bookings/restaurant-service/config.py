""" 
Class configuration to set global variables
"""

ALGORITHM = "HS256"
SECRET_KEY = "25d3c96542daa0310afb85d88c2d380d4458e19af73ed208f96947c706adee06"

ORIGINS = [
    "http://localhost:5500",  # Si usas Live Server de VS Code
    "http://127.0.0.1:5500",  # Alternativa para Live Server
    "http://localhost:3000",  # Si usas un servidor de desarrollo como Node
    "http://127.0.0.1:3000",
    # Añade cualquier otro origen que necesites
]

PATHS = {
    "address": r"Backend\restaurant-reviews-bookings\restaurant-service\persistence\address.json",
    "booking": r"Backend\restaurant-reviews-bookings\restaurant-service\persistence\booking.json",
    "information": r"Backend\restaurant-reviews-bookings\restaurant-service\persistence\information.json",
    "restaurant": r"Backend\restaurant-reviews-bookings\restaurant-service\persistence\restaurant.json",
    "review": r"Backend\restaurant-reviews-bookings\restaurant-service\persistence\review.json",
}
