package framework.tests.basePage;

import framework.config.Config;
import framework.driver.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;

/**
 * BaseTest provides common driver setup and teardown for all tests.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    /**
     * Starts the WebDriver in the specified mode (e.g., "desktop", "mobile").
     * Navigates to base URL from configuration.
     *
     * @param mode browser mode: "desktop" or "mobile"
     */
    @Step("Start driver in mode: {mode} and navigate to base URL")
    protected void startDriver(String mode) {
        driver = DriverFactory.getDriver(mode);
        driver.get(Config.get("base.url"));
    }

    /**
     * Quits WebDriver after each test method.
     */
    @AfterMethod(alwaysRun = true)
    @Step("Quit driver after test")
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
