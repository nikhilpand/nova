# Multi-stage Dockerfile for NOVA Ktor Backend Service
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . /app
RUN gradle :backend:build --no-daemon -x test

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/backend/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
