# 1. Base image: Maven + JDK 17
FROM maven:3.9.4-eclipse-temurin-17

# 2. Install required packages: Chrome, Xvfb (virtual display), nginx
RUN apt-get update && apt-get install -y \
    wget unzip curl gnupg xvfb ca-certificates \
    fonts-liberation libappindicator3-1 libasound2 \
    libatk-bridge2.0-0 libatk1.0-0 libcups2 libdbus-1-3 \
    libgdk-pixbuf2.0-0 libnspr4 libnss3 libx11-xcb1 \
    libxcomposite1 libxdamage1 libxrandr2 xdg-utils \
    libgbm1 libvulkan1 lsb-release nginx \
  && rm -rf /var/lib/apt/lists/*

# 3. Install Google Chrome (latest stable)
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
  && apt install -y ./google-chrome-stable_current_amd64.deb \
  && rm google-chrome-stable_current_amd64.deb

# 4. Set environment variables for Chrome & Allure
ENV CHROME_BIN=/usr/bin/google-chrome
ENV PATH="$PATH:/usr/local/bin"

# 5. Install Allure CLI (for generating HTML reports)
RUN wget https://github.com/allure-framework/allure2/releases/download/2.24.1/allure-2.24.1.tgz \
  && tar -zxvf allure-2.24.1.tgz \
  && mv allure-2.24.1 /opt/allure \
  && ln -s /opt/allure/bin/allure /usr/bin/allure \
  && rm allure-2.24.1.tgz

# 6. Set working directory inside container
WORKDIR /app

# 7. Copy only pom.xml first (to leverage Docker layer cache)
COPY pom.xml .

# 8. Download and cache Maven dependencies
RUN mvn dependency:go-offline -B

# 9. Copy the rest of the project files
COPY . .

# 10. Pre-build the project without running tests (to speed up later runs)
RUN mvn clean install -DskipTests

# 11. Expose nginx port for accessing reports
EXPOSE 8080

# 12. Default command:
# - Run tests inside virtual X server (Xvfb)
# - Generate Allure report
# - Copy report to nginx folder
# - Serve report at http://localhost:8080
CMD sh -c "\
  echo '=== STEP 1: Running tests ===' && \
  xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test -DsuiteXmlFile=src/test/resources/testng.xml || true && \
  echo '=== STEP 2: Generating Allure report ===' && \
  allure generate target/allure-results -o target/allure-report --clean || true && \
  echo '=== STEP 3: Serving report with nginx ===' && \
  rm -rf /var/www/html/* && cp -r target/allure-report/* /var/www/html/ && \
  echo '✅ Allure report available at http://localhost:8080' && \
  nginx -g 'daemon off;'"
