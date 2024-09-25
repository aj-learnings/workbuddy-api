FROM openjdk:17-jdk-alpine
MAINTAINER ajlearnings

# Copy the application jar file
COPY target/workbuddy-api-0.0.1-SNAPSHOT.jar workbuddy-api-0.0.1-SNAPSHOT.jar

# Set environment variables for MongoDB and Redis
ENV MONGO_URI="mongodb://localhost:27017"
ENV REDIS_HOST="localhost"
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=""
ENV SENDER_EMAIL="email@email.com"
ENV SENDER_PASSWORD="password"

# Run the application
ENTRYPOINT ["java", "-jar", "workbuddy-api-0.0.1-SNAPSHOT.jar"]
