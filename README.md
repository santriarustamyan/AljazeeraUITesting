# Selenium Test Automation Framework

A clean, scalable Selenium WebDriver automation framework built with Java 21, Maven, and TestNG. The framework follows the Page Object Model (POM) design pattern for better maintainability, scalability, and reusability.

---

## 🚀 Technologies Used

- Java 21
- Maven 3.9.10
- Selenium WebDriver 4.33.0
- TestNG 7.11.0
- SLF4J Logging
- Page Object Model (POM)

---

## 🖥 Prerequisites

### 1️⃣ Install Java JDK 21

- Download and install from:  
  https://www.oracle.com/java/technologies/downloads/

Verify installation:

```
java -version
```

2️⃣ Install Maven
Download and install from:
https://maven.apache.org/download.cgi

Verify installation:

```
mvn -version
```
Execute Tests
Run all tests via Maven:

```
mvn clean test
```

## 📩 MailSlurp Setup
The project uses MailSlurp to handle temporary email creation for testing purposes.

Steps:
Go to MailSlurp.

Create a free account.

Navigate to API Keys in your MailSlurp dashboard.

Generate a new API Key.

### Create .env file
Inside the project root folder, create a file called .env:

```
touch .env
```
Add your MailSlurp API Key inside .env:

```
MAILSLURP_API_KEY=your_generated_api_key_here
```
⚠ Important: Do not commit your .env file to any public repository.

## ▶️ How to Run Tests
In the project root directory, execute:

```
mvn clean test
```

Tests will automatically download the required ChromeDriver version using WebDriverManager.

By default, tests run in headless mode (browser will not open).

### 🖥 If you want to run tests with visible browser window:
1.Open DriverFactory.java.

2.Set the headless property to false:

```
headless=false
```