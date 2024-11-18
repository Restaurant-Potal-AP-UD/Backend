-- Crear los schemas
CREATE DATABASE IF NOT EXISTS java_db;
CREATE DATABASE IF NOT EXISTS python_db;

-- Crear usuarios con sus contraseñas
CREATE USER 'java_user'@'%' IDENTIFIED BY 'testing';
CREATE USER 'python_user'@'%' IDENTIFIED BY 'testing';

-- Otorgar permisos a los usuarios para sus schemas respectivos
GRANT ALL PRIVILEGES ON java_db.* TO 'java_user'@'%';
GRANT ALL PRIVILEGES ON python_db.* TO 'python_user'@'%';

-- Aplicar los cambios
FLUSH PRIVILEGES;
