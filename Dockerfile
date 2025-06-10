FROM openjdk:17-jdk-alpine
LABEL name="la-franquette-back-docker"
LABEL maintainer="tdignoire@pm.me"
LABEL authors="Thomas Dignoire"


ENV SPRING_MAIL_ACCOUNT=""
ENV SPRING_MAIL_PASSWORD=""
ENV PORT="8081"
ENV BDD_USERNAME=""
ENV BDD_PASSWORD=""

COPY *.jar /app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]