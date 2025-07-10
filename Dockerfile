# Use official Maven image with Eclipse Temurin JDK 17 (compatible with ARM architecture)
FROM maven:3.9.4-eclipse-temurin-17

# Switch to root to install packages
USER root

# Install required packages including Chromium and Xvfb
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    gnupg \
    xvfb \
    chromium \
    chromium-driver \
    default-jdk \
    && rm -rf /var/lib/apt/lists/*

# Set environment variables for Chrome and Xvfb
ENV CHROME_BIN=/usr/bin/chromium
ENV DISPLAY=:99

# Install Allure CLI
RUN wget https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.tgz && \
    tar -xvzf allure-2.27.0.tgz && \
    mv allure-2.27.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -f allure-2.27.0.tgz

# Set working directory
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Pre-install dependencies and build the project (skip tests)
RUN mvn clean install -DskipTests

# Default command to run tests and generate Allure report
CMD ["sh", "-c", "xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test && allure generate target/allure-results -o target/allure-report --clean"]
