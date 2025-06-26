package framework.tests.livepage;

import framework.pages.HomePage;
import framework.pages.LivePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("Livestream")
@Feature("Live Page")
@Owner("Santri")
@Tag("UI")
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
    @Story("Player visibility")
    @Description("Check that Livestream player is visible on LIVE page")
    @Severity(SeverityLevel.BLOCKER)
    public void verifyLivestreamPlayerIsVisible() {
        homePage.waitForOptionalInvisibility(popupLocator);
        homePage.clickMainMenu(HomePage.MainMenu.LIVE);

        Assert.assertTrue(livePage.isLivestreamPlayerVisible(), "Livestream player is not visible on the page");
    }

    @Test(description = "Verify that the Switch Player button is visible on the LIVE page")
    @Story("Switch Player button")
    @Description("Ensure the 'Switch Player' button is visible on LIVE page")
    @Severity(SeverityLevel.NORMAL)
    public void verifySwitchPlayerButtonVisible() {
        homePage.waitForOptionalInvisibility(popupLocator);
        homePage.clickMainMenu(HomePage.MainMenu.LIVE);

        Assert.assertTrue(livePage.isSwitchPlayerPresent(), "Switch Player button is not present on the page");
    }

}
