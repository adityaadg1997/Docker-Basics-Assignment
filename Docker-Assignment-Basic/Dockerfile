# Use official OpenJDK image as a parent
FROM openjdk:17-jdk-slim

# Set working directory
#WORKDIR /app

# Copy built jar to the container
#COPY target/web-service-0.0.1-SNAPSHOT.jar app.jar
# Copy the built JAR file into the container # . for current directory
COPY target/docker-assignment-basic.jar .

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "docker-assignment-basic.jar"]
