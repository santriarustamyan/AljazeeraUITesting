package framework.tests.livepage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test suite verifying social media links in the footer.
 */
@Epic("Social Media")
@Feature("Footer Links")
@Owner("Santri")
@Tag("Regression")
public class SocialMediaLinksTest extends BaseTest {

    private HomePage homePage;

    /**
     * Initializes WebDriver and navigates to the homepage before each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * DataProvider supplying all social media links and expected URL substrings.
     *
     * @return Object[][] containing link enums and expected URL parts.
     */
    @DataProvider(name = "socialLinksProvider")
    public Object[][] socialLinksProvider() {
        return new Object[][]{
                {HomePage.SocialMediaLinks.FACEBOOK, "facebook.com/aljazeera"},
                {HomePage.SocialMediaLinks.X, "x.com/ajenglish"},
                {HomePage.SocialMediaLinks.YOUTUBE, "youtube.com/aljazeeraenglish"},
                {HomePage.SocialMediaLinks.INSTAGRAM, "instagram.com/aljazeeraenglish"}
        };
    }

    /**
     * Verifies that each social media link is visible, clickable,
     * and redirects to the correct external URL.
     *
     * @param link             The social media link enum.
     * @param expectedUrlPart  Substring expected in the redirected URL.
     */
    @Test(
            description = "Verify social media link visibility, clickability, and correct redirection",
            dataProvider = "socialLinksProvider"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Validate social media footer links")
    @Description("Ensures each social media footer link is visible, clickable, and redirects to the correct URL in a new tab.")
    public void verifySocialMediaLinkNavigation(HomePage.SocialMediaLinks link, String expectedUrlPart) {

        Allure.step("Verify the visibility of the '" + link.name() + "' social media link");
        Assert.assertTrue(
                homePage.isSocialLinkVisible(link),
                "The social media link is not visible: " + link.name()
        );

        Allure.step("Click the '" + link.name() + "' link and switch to the new tab");
        homePage.clickSocialMediaLink(link);
        homePage.switchToNewTab();

        Allure.step("Validate the redirected URL contains the expected substring");
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                actualUrl.contains(expectedUrlPart),
                "Expected URL to contain '" + expectedUrlPart + "', but was: " + actualUrl
        );

        Allure.step("Close the new tab and switch back to the main tab");
        homePage.closeCurrentTab();
        homePage.switchToMainTab();
    }
}
