# Base image with JDK and Maven
FROM maven:3.9.4-eclipse-temurin-17

# Install dependencies
RUN apt-get update && apt-get install -y \
    chromium \
    chromium-driver \
    xvfb \
    wget \
    unzip \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Set environment variable for Chromium
ENV CHROME_BIN=/usr/bin/chromium

# Create working directory
WORKDIR /app

# Copy project files into container
COPY . .

# Pre-install dependencies
RUN mvn clean install -DskipTests

# Run tests with headless browser
CMD ["sh", "-c", "xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test"]
