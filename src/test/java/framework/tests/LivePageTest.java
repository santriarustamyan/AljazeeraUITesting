package framework.tests;

import framework.config.ConfigReader;
import framework.driver.DriverFactory;
import framework.pages.HomePage;
import framework.pages.LivePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class LivePageTest {

    WebDriver driver;
    HomePage homePage;
    LivePage livePage;
    SoftAssert softAssert = new SoftAssert();

    private final By popupLocator = By.id("user-accounts-tooltip");

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.get("base.url"));
        homePage = new HomePage(driver);
    }

    @Test(description = "Verify LIVE button navigation and functionality")
    public void verifyLiveButton() {
        livePage = new LivePage(driver);
        homePage.waitForInvisibility(popupLocator);
        homePage.clickMainMenu(HomePage.MainMenu.LIVE);

        // Verify correct URL after navigation
        softAssert.assertTrue(driver.getCurrentUrl().contains("/live"), "URL is incorrect after clicking LIVE button");

        // Verify LIVE page header
        softAssert.assertTrue(livePage.isLiveHeaderDisplayed(), "LIVE page header is not displayed");

        // Verify Switch Player button
        softAssert.assertTrue(livePage.isSwitchPlayerPresent(), "Switch Player button is not present");

        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
