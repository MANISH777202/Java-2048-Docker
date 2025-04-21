# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jdk-slim

# Install necessary X11 libraries and font dependencies
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    fonts-dejavu \
    fonts-dejavu-core \
    fontconfig \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Copy the source code into the container at /app
COPY src/ /app

# Compile the Java application
RUN javac Game2048.java

# Set the entrypoint to run the compiled class
CMD ["java", "Game2048"]

# Expose the port the app runs on (if required, for GUI apps this may not be necessary)
EXPOSE 8080
