package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test suite to verify social media links in the footer.
 */
@Epic("Social Media")
@Feature("Footer Links")
@Owner("Santri")
@Tag("Regression")
public class SocialMediaLinksTest extends BaseTest {

    private HomePage homePage;

    /**
     * Setup method to initialize the WebDriver and navigate to the homepage.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * Verifies that each social media icon is visible, clickable, and redirects to the correct external URL.
     */
    @Test(description = "Verify social media links are visible, clickable, and redirect correctly")
    @Story("Verify all social media links")
    @Description("Check that all footer social links are visible, clickable, and open the correct URL in a new tab")
    @Severity(SeverityLevel.NORMAL)
    public void verifySocialMediaLinksAreVisibleAndClickable() {

        for (HomePage.SocialMediaLinks link : HomePage.SocialMediaLinks.values()) {

            Allure.step("Verify visibility of " + link.name() + " link");
            Assert.assertTrue(
                    homePage.isSocialLinkVisible(link),
                    "Social media link is not visible: " + link.name()
            );

            Allure.step("Click " + link.name() + " link and switch to new tab");
            homePage.clickSocialMediaLink(link);
            homePage.switchToNewTab();

            Allure.step("Verify redirected URL for " + link.name());
            String expectedUrlPart = getExpectedUrlPart(link);
            String actualUrl = driver.getCurrentUrl();
            Assert.assertTrue(
                    actualUrl.contains(expectedUrlPart),
                    "Expected URL to contain '" + expectedUrlPart + "' but was: " + actualUrl
            );

            Allure.step("Close tab and return to main tab");
            homePage.closeCurrentTab();
            homePage.switchToMainTab();
        }
    }

    /**
     * Returns expected URL substrings for each social media link to validate redirects.
     *
     * @param link The social media link enum.
     * @return Expected part of the URL.
     */
    private String getExpectedUrlPart(HomePage.SocialMediaLinks link) {
        return switch (link) {
            case FACEBOOK -> "facebook.com/aljazeera";
            case X -> "x.com/ajenglish";
            case YOUTUBE -> "youtube.com/aljazeeraenglish";
            case INSTAGRAM -> "instagram.com/aljazeeraenglish";
        };
    }
}
