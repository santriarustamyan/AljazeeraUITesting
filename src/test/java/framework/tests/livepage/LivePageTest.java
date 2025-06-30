package framework.tests.livepage;

import framework.pages.HomePage;
import framework.pages.LivePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test suite for verifying elements and functionality of the LIVE page.
 */
@Epic("Livestream")
@Feature("Live Page")
@Owner("Santri")
@Tag("UI")
public class LivePageTest extends BaseTest {

    private HomePage homePage;
    private LivePage livePage;

    // Locator of potential popup blocking interactions
    private final By popupLocator = By.id("user-accounts-tooltip");

    /**
     * Setup before each test: initialize driver and page objects.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
        livePage = new LivePage(driver);
    }

    /**
     * Verifies that the Livestream player is visible when navigating to the LIVE page.
     */
    @Test(description = "Verify that the Livestream player is visible on the LIVE page")
    @Story("Player Visibility")
    @Description("Check that the Livestream player is visible on the LIVE page")
    @Severity(SeverityLevel.BLOCKER)
    public void verifyLivestreamPlayerIsVisible() {

        Allure.step("Wait for optional pop-up to disappear");
        homePage.waitForOptionalInvisibility(popupLocator);

        Allure.step("Navigate to the LIVE page");
        homePage.clickMainMenu(HomePage.MainMenu.LIVE);

        Allure.step("Assert that the Livestream player is visible");
        Assert.assertTrue(
                livePage.isLivestreamPlayerVisible(),
                "Livestream player is not visible on the page"
        );
    }

    /**
     * Verifies that the 'Switch Player' button is present on the LIVE page.
     */
    @Test(description = "Verify that the Switch Player button is visible on the LIVE page")
    @Story("Switch Player Button")
    @Description("Ensure the 'Switch Player' button is visible on the LIVE page")
    @Severity(SeverityLevel.NORMAL)
    public void verifySwitchPlayerButtonVisible() {

        Allure.step("Wait for optional pop-up to disappear");
        homePage.waitForOptionalInvisibility(popupLocator);

        Allure.step("Navigate to the LIVE page");
        homePage.clickMainMenu(HomePage.MainMenu.LIVE);

        Allure.step("Assert that the Switch Player button is visible");
        Assert.assertTrue(
                livePage.isSwitchPlayerPresent(),
                "'Switch Player' button is not present on the page"
        );
    }
}
