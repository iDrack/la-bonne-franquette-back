FROM openjdk:17-jdk-alpine
LABEL name="la-franquette-back-docker"
LABEL maintainer="tdignoire@pm.me"
LABEL authors="Thomas Dignoire"

COPY *.jar /app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]