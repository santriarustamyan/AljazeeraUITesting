package framework.driver;

import framework.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

/**
 * Factory class for creating and managing WebDriver instances.
 */
public class DriverFactory {

    private static WebDriver driver;

    /**
     * Returns a singleton WebDriver instance configured for the given mode.
     *
     * @param mode "desktop" or "mobile"
     * @return configured WebDriver instance
     */
    public static WebDriver getDriver(String mode) {
        if (driver == null) {

            ChromeOptions options = new ChromeOptions();

            if (!isRunningInDocker()) {
                WebDriverManager.chromedriver().setup();
            }

            // Suppress "Chrome is being controlled by automated software"
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            boolean isHeadless = Config.getBoolean("headless");

            if ("mobile".equalsIgnoreCase(mode)) {
                // Enable mobile emulation
                Map<String, Object> mobileEmulation = Map.of("deviceName", "iPhone X");
                options.setExperimentalOption("mobileEmulation", mobileEmulation);

                if (isHeadless) {
                    options.addArguments("--headless=new");
                }
            } else {
                // Desktop mode
                if (isHeadless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                } else {
                    options.addArguments("--start-maximized");
                }
            }

            driver = new ChromeDriver(options);

            // Adjust window size if needed
            if (!isHeadless && !"mobile".equalsIgnoreCase(mode)) {
                driver.manage().window().maximize();
            } else if (!"mobile".equalsIgnoreCase(mode)) {
                driver.manage().window().setSize(new Dimension(1920, 1080));
            }
        }

        return driver;
    }

    /**
     * Quits the WebDriver and cleans up the instance.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    private static boolean isRunningInDocker() {
        String env = System.getenv("DOCKER");
        return env != null && env.equalsIgnoreCase("true");
    }
}
