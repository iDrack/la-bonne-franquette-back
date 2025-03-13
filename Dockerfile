FROM openjdk:17-jdk-alpine
LABEL name="la-franquette-back-docker"
LABEL maintainer="tdignoire@pm.me"
LABEL authors="Thomas Dignoire"

COPY .env /.env
COPY target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]