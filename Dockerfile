# Base image with JDK and Maven
FROM maven:3.9.4-eclipse-temurin-17

# Install Chromium and necessary tools
RUN apt-get update && apt-get install -y \
    chromium-browser \
    chromium-chromedriver \
    xvfb \
    wget \
    unzip \
    curl && \
    rm -rf /var/lib/apt/lists/*

# Set environment variable for Chrome binary
ENV CHROME_BIN=/usr/bin/chromium-browser

# Create working directory
WORKDIR /app

# Copy project into container
COPY . .

# Pre-install Maven dependencies
RUN mvn clean install -DskipTests

# Run tests with headless display
CMD ["sh", "-c", "xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test"]
