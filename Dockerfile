FROM eclipse-temurin:11-jdk
COPY target/*.jar /app.jar
COPY .env .env
ENV USER_NAME ${POSTGRES_USER}
ENV PASSWORD ${POSTGRES_PASSWORD}
ENV URL ${POSTGRES_URL}

ENTRYPOINT ["java","-jar","/app.jar"]

#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp

# Copia los archivos de dependencia
#COPY target/dependency /app

# Copia los archivos de recursos de la aplicación
#COPY src/main/resources /app

# Copia la clase principal de la aplicación
#COPY src/main/kotlin/arquitecura/software/demo/DemoApplication.kt /app

#WORKDIR /app

# Compila la aplicación
#RUN javac -cp .:$(echo lib/*.jar | tr ' ' ':') DemoApplication.kt

ENTRYPOINT ["java","-cp",".:$(echo lib/*.jar | tr ' ' ':')","arquitectura.software.demo.DemoApplication"]
