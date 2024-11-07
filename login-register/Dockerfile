FROM eclipse-temurin:21

LABEL author="Sebastian Avendaño"

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/login-register-0.0.1-SNAPSHOT.jar /app/app.jar

# Comando para ejecutar la clase principal
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
