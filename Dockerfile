# Use a lightweight Selenium Chromium image
FROM seleniarm/standalone-chromium:latest

USER root

WORKDIR /app

# Install Java, Maven, and Allure CLI
RUN apt-get update && \
    apt-get install -y unzip wget default-jdk maven && \
    wget https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.tgz && \
    tar -xvzf allure-2.27.0.tgz && \
    mv allure-2.27.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -f allure-2.27.0.tgz

# Copy test project
COPY . /app

RUN mvn clean install -DskipTests

EXPOSE 8088

CMD ["sh", "-c", "mvn test && cp -r target/allure-results /app/target/allure-results && allure generate /app/target/allure-results -o /app/target/allure-report --clean"]
