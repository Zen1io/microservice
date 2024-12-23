# Stage 1: Base image with Maven and dependencies
FROM maven:3.9-eclipse-temurin-17 AS base

WORKDIR /app


COPY pom.xml .
RUN mvn dependency:go-offline -B

# Stage 2: Build application
FROM base AS builder


COPY src ./src
RUN mvn clean package -DskipTests

# Stage 3: Runtime environment
FROM eclipse-temurin:17-jdk AS runtime

WORKDIR /app


COPY --from=builder /app/target/Microservice.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
