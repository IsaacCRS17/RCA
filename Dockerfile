# Utiliza una imagen base de Java
FROM openjdk:11

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml para instalar dependencias
COPY pom.xml .

# Instala las dependencias utilizando Maven
RUN mvn clean install

# Copia el resto de los archivos del proyecto
COPY . .

# Expone el puerto en el que la aplicación escucha
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "target/your-app.jar"]
