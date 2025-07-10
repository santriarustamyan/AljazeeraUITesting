# Base image with JDK and Maven
FROM maven:3.9.4-eclipse-temurin-17

# Install dependencies
RUN apt-get update && apt-get install -y \
    chromium-browser \
    chromium-chromedriver \
    xvfb \
    wget \
    unzip \
    curl && \
    rm -rf /var/lib/apt/lists/*

# Download and install Chrome manually
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt install -y ./google-chrome-stable_current_amd64.deb && \
    rm google-chrome-stable_current_amd64.deb

# Download and install ChromeDriver
RUN CHROME_VERSION=$(google-chrome --version | grep -oP '\d+\.\d+\.\d+') && \
    wget -O chromedriver.zip https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip && \
    unzip chromedriver.zip && \
    mv chromedriver /usr/bin/chromedriver && \
    chmod +x /usr/bin/chromedriver && \
    rm chromedriver.zip


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
