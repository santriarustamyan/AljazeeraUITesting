# Use a lightweight Selenium Chromium image (preconfigured for ARM and x86)
FROM seleniarm/standalone-chromium:latest

USER root

# Set the working directory inside the container
WORKDIR /app

# Install Java, Maven, and Allure CLI
RUN apt-get update && \
    apt-get install -y unzip wget default-jdk maven && \
    wget https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.tgz && \
    tar -xvzf allure-2.27.0.tgz && \
    mv allure-2.27.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -f allure-2.27.0.tgz

# Copy all project files into the container
COPY . .

# Build the Maven project without running tests
RUN mvn clean install -DskipTests

# Expose port for the Allure report
EXPOSE 8088

# Default command to run tests and generate Allure report
CMD ["sh", "-c", "mvn test && allure generate target/allure-results -o target/allure-report --clean"]
