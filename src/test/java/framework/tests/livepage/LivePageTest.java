package framework.tests.livepage;

import framework.pages.HomePage;
import framework.pages.LivePage;
import framework.tests.basePage.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class LivePageTest extends BaseTest {

    private HomePage homePage;
    private LivePage livePage;
    private SoftAssert softAssert = new SoftAssert();

    private final By popupLocator = By.id("user-accounts-tooltip");

    @BeforeMethod
    public void setUp() {
        homePage = new HomePage(driver);
        livePage = new LivePage(driver);
    }

    @Test(description = "Verify LIVE button navigation and functionality")
    public void verifyLiveButton() {

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

}
