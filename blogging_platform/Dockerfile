# Etapa 1: Construcción del proyecto
FROM maven:3-openjdk-17 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos necesarios para compilar
COPY pom.xml .
COPY src ./src

# Compila la aplicación sin ejecutar pruebas
RUN mvn clean package -DskipTests

# Etapa 2: Contenedor para ejecutar el JAR
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]