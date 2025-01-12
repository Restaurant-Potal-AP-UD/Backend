FROM eclipse-temurin:21

LABEL author="Sebastian Avendaño"

EXPOSE 8080:8080
# Establece el directorio de trabajo en el contenedor
WORKDIR /service1

# Copia el archivo JAR de tu aplicación al contenedor
COPY login-register/target/login-register-0.0.1-SNAPSHOT.jar /service1/java/app.jar

# Comando para ejecutar la clase principal
ENTRYPOINT ["java", "-jar", "/service1/java/app.jar"]

