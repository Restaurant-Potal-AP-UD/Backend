# BACKEND INFORMATION
This service receives and dispatches information.
It's built as a RESTful API.

## CONTENTS
- [Project Description](#project-description)
- [Technologies](#technologies)
  - [Java](#java)
  - [Python](#python)
  - [MySQL](#mysql)
  - [H2](#h2)
  - [Docker](#docker)
- [Installation](#installation)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
- [Testing](#testing)
  - [Unit Testing](#unit-testing)
- [Project Structure](#project-structure)
- [License](#license)
- [Dev](#dev)

## Project Description
This project is a backend service designed to handle user management, restaurant creation, restaurant listings, booking systems, and payment processing (coming soon). It leverages various technologies to ensure robust and efficient operations.

## TECHNOLOGIES

### Java
- **Spring Boot**: Used to build the core user management system.
- **Pom Dependencies**: Located in the `pom.xml` file.

### Python
- **FastAPI**: Used to build the whole source code behind restaurant, reviews and booking information

### MySQL
- The main database used for persistent storage.

### H2
- Used for testing purposes.

### Docker
- Used for containerization to simplify installation on other machines.

## Installation

### Prerequisites
- **Docker**: Ensure you have Docker installed.

### Setup
1. **Create or log in to your Docker account**.
2. **Open Docker Hub**.
3. **Execute**:

## Testing

### Unit Testing
This project includes essential unit testing implemented in each language.
- [Python Testing](https://github.com/Restaurant-Potal-AP-UD/Backend/tree/main/test)<br>
  This directory contains Python unit tests managed by Docker Compose.
  - [Python Testing Code](https://github.com/Restaurant-Potal-AP-UD/Backend/tree/main/restaurant-reviews-bookings/restaurant-service/test)<br>
    This directory houses all the code for integration testing of every endpoint in the project.

- [Java Testing](https://github.com/Restaurant-Potal-AP-UD/Backend/tree/main/login-register/src/test)<br>
  This directory contains Java testing code. Every test it's managed by Junit in combination with Spring Boot
  1. [Java Utilities](https://github.com/Restaurant-Potal-AP-UD/Backend/tree/main/login-register/src/test/java/com/dinneconnect/auth/login_register/utilitiesTest)
  2. [Java Services](https://github.com/Restaurant-Potal-AP-UD/Backend/tree/main/login-register/src/test/java/com/dinneconnect/auth/login_register/userServiceTest)
  3. [Java Controllers](https://github.com/Restaurant-Potal-AP-UD/Backend/tree/main/login-register/src/test/java/com/dinneconnect/auth/login_register/controllerTest)


## Project Structure

The project follows a modular structure for an easy comprenhension, the development and maintenance. 

- **`login-register/`**
  Contains user management code
  - **`src/`**
    - **`main/`**
      - **`DTO/`**: Data transfer objects  
      - **`controller/`**: Configuration of every endpoint that will retrieve the requested information
      - **`models/`**: Used to define models that will work with the DB  
      - **`repository/`**: Contains CRUD raw methods  
      - **`services/`**: Define CRUD methods
      - **`utilities/`**: Functions and tools that are frequently used in other packages
    - **`test/`**
      - **`controllerTest/`**: Source code of the integration testing
      - **`userServiceTest/`**: This file contains the userService methods unittesting
      - **`utilitiesTest/`**: This file contains utilities methods unittesting
- **`mysql/`**
  Contains mysql init code
- **`restaurant-reviews-booking`**
  Contains restaurant, reviews and booking code
  - **`SQL/`**: Main connection with the DB system
  - **`env-r/`**: Dependencies configuration of this service
  - **`models/`**: Used to define models that will work with the DB  
  - **`routers/`**: Contains .py files that work as subsystems, these subsystems defines all the endpoints of this service
  - **`test/`**: Define Integration Testing
  - **`utilities/`**: Functions and tools that are frequently used in other packages

- **`docker-compose.yml`**
  Configuration for serice deployment through Docker Compose.

### DEV
- **Sebastian Avendaño Rodriguez**
  - savendanor@udistrital.edu.co
  - Student at: **Universidad Distrital Francisco Jose de Caldas**
  - Student ID: **20232020101**
