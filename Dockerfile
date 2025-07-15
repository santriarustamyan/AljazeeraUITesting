FROM maven:3.9.4-eclipse-temurin-17

# Устанавливаем зависимости
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    gnupg \
    xvfb \
    ca-certificates \
    fonts-liberation \
    libappindicator3-1 \
    libasound2 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libcups2 \
    libdbus-1-3 \
    libgdk-pixbuf2.0-0 \
    libnspr4 \
    libnss3 \
    libx11-xcb1 \
    libxcomposite1 \
    libxdamage1 \
    libxrandr2 \
    xdg-utils \
    libgbm1 \
    libvulkan1 \
    lsb-release \
    && rm -rf /var/lib/apt/lists/*

# Установка Google Chrome
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt install -y ./google-chrome-stable_current_amd64.deb && \
    rm google-chrome-stable_current_amd64.deb

ENV CHROME_BIN=/usr/bin/google-chrome
ENV PATH="$PATH:/usr/local/bin"

# Установка Allure CLI
RUN wget https://github.com/allure-framework/allure2/releases/download/2.24.1/allure-2.24.1.tgz && \
    tar -zxvf allure-2.24.1.tgz && \
    mv allure-2.24.1 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm allure-2.24.1.tgz

# Создание рабочей директории
WORKDIR /app

# Копируем проект
COPY . .

# Предустановка зависимостей
RUN mvn clean install -DskipTests

# Запуск тестов
CMD ["sh", "-c", "xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test -DsuiteXmlFile=src/test/resources/testng.xml"]


#
## 1. Base image: Maven + JDK 17
#FROM maven:3.9.4-eclipse-temurin-17
#
## 2. Install required dependencies: Chrome, Xvfb for headless, and nginx for serving reports
#RUN apt-get update && apt-get install -y \
#    wget \
#    unzip \
#    curl \
#    gnupg \
#    xvfb \
#    ca-certificates \
#    fonts-liberation \
#    libappindicator3-1 \
#    libasound2 \
#    libatk-bridge2.0-0 \
#    libatk1.0-0 \
#    libcups2 \
#    libdbus-1-3 \
#    libgdk-pixbuf2.0-0 \
#    libnspr4 \
#    libnss3 \
#    libx11-xcb1 \
#    libxcomposite1 \
#    libxdamage1 \
#    libxrandr2 \
#    xdg-utils \
#    libgbm1 \
#    libvulkan1 \
#    lsb-release \
#    nginx \
#  && rm -rf /var/lib/apt/lists/*
#
## 3. Install Google Chrome (stable version)
#RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
#  && apt install -y ./google-chrome-stable_current_amd64.deb \
#  && rm google-chrome-stable_current_amd64.deb
#
## 4. Set environment variables for Chrome and Allure
#ENV CHROME_BIN=/usr/bin/google-chrome
#ENV PATH="$PATH:/usr/local/bin"
#
## 5. Install Allure CLI for generating reports
#RUN wget https://github.com/allure-framework/allure2/releases/download/2.24.1/allure-2.24.1.tgz \
#  && tar -zxvf allure-2.24.1.tgz \
#  && mv allure-2.24.1 /opt/allure \
#  && ln -s /opt/allure/bin/allure /usr/bin/allure \
#  && rm allure-2.24.1.tgz
#
## 6. Create a working directory inside the container
#WORKDIR /app
#
## 7. Copy the Maven project into the container
#COPY . .
#
## 8. Pre-install Maven dependencies to cache them
#RUN mvn clean install -DskipTests
#
## 9. Expose nginx port for external access
#EXPOSE 8080
#
## 10. Entry point:
##    1) Run tests with Xvfb (headless Chrome)
##    2) Generate Allure HTML report
##    3) Copy the report into nginx's serving folder
##    4) Start nginx to serve the report on http://localhost:8080
#CMD sh -c "\
#  echo '=== Running UI tests ===' && \
#  xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test -DsuiteXmlFile=src/test/resources/testng.xml && \
#  echo '=== Generating Allure HTML report ===' && \
#  allure generate target/allure-results -o target/allure-report --clean && \
#  echo '=== Copying report to nginx folder ===' && \
#  rm -rf /var/www/html/* && cp -r target/allure-report/* /var/www/html/ && \
#  echo 'Allure report available at: http://localhost:8080' && \
#  nginx -g 'daemon off;'"
