# Usar una imagen base de Maven para construir el proyecto
FROM maven:3.8.5-openjdk-17-slim AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /usr/src/app

# Copiar los archivos del proyecto al contenedor
COPY . .

# Construir el proyecto usando Maven
RUN mvn clean package -DskipTests

# Usar una imagen base de OpenJDK 17 para correr la aplicación
FROM openjdk:17-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /usr/src/app

# Copiar el archivo JAR desde la fase de construcción
COPY --from=build /usr/src/app/target/escuelas_project-v1.jar escuelas_project-v1.jar
COPY wait-for-service.jar wait-for-service.jar

# Exponer el puerto
EXPOSE 3000

# Ejecutar el JAR
CMD ["java", "-cp", "wait-for-service.jar:escuelas_project-v1.jar", "WaitForService", "escuelas-db", "3306", "60", "java", "-jar", "escuelas_project-v1.jar"]