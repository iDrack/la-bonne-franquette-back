FROM openjdk:17-jdk-alpine
LABEL name="la-franquette-back-docker"
LABEL maintainer="tdignoire@pm.me"
LABEL authors="Thomas Dignoire"

ARG MAIL
ARG MAIL_PASS
ARG PORT
ARG USER
ARG BDD_PASS

ENV SPRING_MAIL_ACCOUNT=${MAIL}
ENV SPRING_MAIL_PASSWORD=${MAIL_PASS}
ENV PORT=${PORT}
ENV BDD_USERNAME=${USER}
ENV BDD_PASSWORD=${BDD_PASS}

COPY *.jar /app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]