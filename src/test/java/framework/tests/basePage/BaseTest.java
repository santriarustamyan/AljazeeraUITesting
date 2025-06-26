package framework.tests.basePage;

import framework.config.Config;
import framework.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
public abstract class BaseTest {

    protected WebDriver driver;

    protected void startDriver(String mode) {
        driver = DriverFactory.getDriver(mode);
        driver.get(Config.get("base.url"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }

}
