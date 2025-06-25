package framework.tests.livepage;

import framework.pages.HomePage;
import framework.pages.LivePage;
import framework.tests.basePage.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class LivePageTest extends BaseTest {

    private HomePage homePage;
    private LivePage livePage;

    private final By popupLocator = By.id("user-accounts-tooltip");

    @BeforeMethod
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
        livePage = new LivePage(driver);
    }

    @Test(description = "Verify that the Livestream player is visible on the LIVE page")
    public void verifyLivestreamPlayerIsVisible() {
        homePage.waitForOptionalInvisibility(popupLocator);
        homePage.clickMainMenu(HomePage.MainMenu.LIVE);

        Assert.assertTrue(livePage.isLivestreamPlayerVisible(), "Livestream player is not visible on the page");
    }

    @Test(description = "Verify that the Switch Player button is visible on the LIVE page")
    public void verifySwitchPlayerButtonVisible() {
        homePage.waitForOptionalInvisibility(popupLocator);
        homePage.clickMainMenu(HomePage.MainMenu.LIVE);

        Assert.assertTrue(livePage.isSwitchPlayerPresent(), "Switch Player button is not present on the page");
    }

}
