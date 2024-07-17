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

# Instalar bash y copiar wait-for-it.sh al contenedor
RUN apk add --no-cache bash
COPY wait-for-it.sh /usr/src/app/
RUN chmod +x /usr/src/app/wait-for-it.sh

# Copiar el archivo JAR desde la fase de construcción
COPY --from=build /usr/src/app/target/escuelas_project-v1.jar escuelas_project-v1.jar

# Exponer el puerto
EXPOSE 3000

# Ejecutar el JAR
CMD ["/usr/src/app/wait-for-it.sh", "escuelas-db:3306", "-t", "60", "-s", "--", "java", "-jar", "escuelas_project-v1.jar"]