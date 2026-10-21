# Multi-stage Dockerfile for building and running the Spring Boot app
# Stage 1: build using Gradle wrapper
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /workspace

# Copy Gradle wrapper and build files first for caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

# Copy source
COPY src ./src

# Build the application (produces bootJar)
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy jar from build stage
COPY --from=build /workspace/build/libs/*.jar app.jar

EXPOSE 8080

# Use a non-root user for better security
RUN useradd --create-home appuser || true
USER appuser

ENTRYPOINT ["sh","-c","java -Dserver.port=$PORT -jar /app/app.jar"]
