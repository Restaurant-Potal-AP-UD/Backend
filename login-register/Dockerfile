# Usa la imagen base de Maven para construir el proyecto
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usa la imagen de JDK para ejecutar el proyecto
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/login-register-0.0.1-SNAPSHOT.jar /app/app.jar

# Expone el puerto donde correrá Spring Boot
EXPOSE 8080

# Comando para ejecutar la clase principal
ENTRYPOINT ["java", "-jar", "app.jar"]
