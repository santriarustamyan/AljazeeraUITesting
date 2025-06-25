package framework.driver;

import framework.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class DriverFactory {

    public static WebDriver driver;

    public static WebDriver getDriver(String mode) {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            boolean isHeadless = Config.getBoolean("headless");

            if ("mobile".equalsIgnoreCase(mode)) {
                Map<String, Object> mobileEmulation = Map.of("deviceName", "iPhone X");
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
                if (isHeadless) options.addArguments("--headless=new");
            } else {
                if (isHeadless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                } else {
                    options.addArguments("--start-maximized");
                }

            }

            driver = new ChromeDriver(options);

            if (!isHeadless && !"mobile".equalsIgnoreCase(mode)) {
                driver.manage().window().maximize();
            } else if (!"mobile".equalsIgnoreCase(mode)) {
                driver.manage().window().setSize(new Dimension(1920, 1080));
            }}
        return driver;
    }


    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}



