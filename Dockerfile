FROM seleniarm/standalone-chromium:latest

USER root

# Установка JDK, Maven и unzip
RUN apt-get update && \
    apt-get install -y default-jdk maven unzip wget && \
    rm -rf /var/lib/apt/lists/*

# Установка Allure CLI
RUN wget https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.tgz && \
    tar -xvzf allure-2.27.0.tgz && \
    mv allure-2.27.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -f allure-2.27.0.tgz

# Рабочая директория
WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

# Запуск тестов и генерация отчета
CMD ["sh", "-c", "xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test && allure generate target/allure-results -o target/allure-report --clean"]
