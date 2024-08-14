FROM openjdk:17-jdk-alpine
MAINTAINER ajlearnings

# Copy the application jar file
COPY target/workbuddy-api-0.0.1-SNAPSHOT.jar workbuddy-api-0.0.1-SNAPSHOT.jar

# Copy the script file
COPY wait-and-start.sh wait-and-start.sh

# Set environment variables for MongoDB and Redis
ENV MONGO_HOST=localhost
ENV MONGO_PORT=27017
ENV REDIS_HOST=localhost
ENV REDIS_PORT=6379
ENV MONGO_ROOT_USERNAME=admin
ENV MONGO_ROOT_PASSWORD=password

# Run a script file to wait
RUN chmod +x wait-and-start.sh

# Run the application
ENTRYPOINT ["./wait-and-start.sh"]
