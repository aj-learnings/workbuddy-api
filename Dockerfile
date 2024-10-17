# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Package the application (skip tests if you want to speed up the build)
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight image for running the application
FROM openjdk:17-jdk-alpine
LABEL org.opencontainers.image.authors="ajlearnings"

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/workbuddy-api-0.0.1-SNAPSHOT.jar workbuddy-api-0.0.1-SNAPSHOT.jar

# Set environment variables for MongoDB, Redis, and Kafka
ENV MONGO_URI="mongodb://localhost:27017"
ENV REDIS_HOST="localhost"
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=""
ENV SENDER_EMAIL="email@email.com"
ENV SENDER_PASSWORD="password"
ENV KAFKA_URI="localhost:9092"

# Run the application
ENTRYPOINT ["java", "-jar", "workbuddy-api-0.0.1-SNAPSHOT.jar"]
