


# 1. Base image: Maven + JDK 17
FROM maven:3.9.4-eclipse-temurin-17



# 2. Install dependencies: Chrome, Xvfb, nginx
RUN apt-get update && apt-get install -y \


    wget unzip curl gnupg xvfb ca-certificates \
    fonts-liberation libappindicator3-1 libasound2 \
    libatk-bridge2.0-0 libatk1.0-0 libcups2 libdbus-1-3 \
    libgdk-pixbuf2.0-0 libnspr4 libnss3 libx11-xcb1 \
    libxcomposite1 libxdamage1 libxrandr2 xdg-utils \
    libgbm1 libvulkan1 lsb-release nginx \
  && rm -rf /var/lib/apt/lists/*

# 3. Install Google Chrome
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
  && apt install -y ./google-chrome-stable_current_amd64.deb \
  && rm google-chrome-stable_current_amd64.deb

# 4. Set env for Chrome & Allure
ENV CHROME_BIN=/usr/bin/google-chrome
ENV PATH="$PATH:/usr/local/bin"

# 5. Install Allure CLI
RUN wget https://github.com/allure-framework/allure2/releases/download/2.24.1/allure-2.24.1.tgz \
  && tar -zxvf allure-2.24.1.tgz \
  && mv allure-2.24.1 /opt/allure \
  && ln -s /opt/allure/bin/allure /usr/bin/allure \
  && rm allure-2.24.1.tgz



# 6. Working directory \
    WORKDIR /app



# 7. Copy project
COPY . .


# 8. Cache Maven dependencies
RUN mvn clean install -DskipTests


# 9. Expose nginx port
EXPOSE 8080

# 10. Run tests + debug steps + serve report
CMD sh -c "\
  echo '=== STEP 1: Running UI tests ===' && \
  xvfb-run --auto-servernum --server-args='-screen 0 1920x1080x24' mvn test -DsuiteXmlFile=src/test/resources/testng.xml || true && \
  echo '=== DEBUG: Contents of target/allure-results ===' && \
  ls -la target/allure-results || true && \
  echo '=== STEP 2: Generating Allure HTML report ===' && \
  allure generate target/allure-results -o target/allure-report --clean || true && \
  echo '=== DEBUG: Contents of target/allure-report ===' && \
  ls -la target/allure-report || true && \
  echo '=== STEP 3: Copy report into nginx folder ===' && \
  rm -rf /var/www/html/* && cp -r target/allure-report/* /var/www/html/ || true && \
  echo '=== DEBUG: Contents of /var/www/html ===' && \
  ls -la /var/www/html || true && \
  echo '✅ Allure report should be available at: http://localhost:8080' && \
  nginx -g 'daemon off;'"







