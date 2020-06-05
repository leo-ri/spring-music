FROM openjdk:8-jdk-alpine
ARG APP=build/libs/spring-music-*.jar
COPY ${APP} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
