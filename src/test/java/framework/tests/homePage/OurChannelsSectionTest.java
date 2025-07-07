package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This test verifies that the 'Our Channels' section in the homepage footer
 * is visible, non-clickable, and each link correctly navigates in the same tab.
 */
@Epic("Footer")
@Feature("'Our Channels' Section")
@Owner("Santri")
@Tag("UI")
public class OurChannelsSectionTest extends BaseTest {

    private HomePage homePage;

    /**
     * Initializes WebDriver and opens the homepage before each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * Provides all channel links from the OurChannels enum.
     *
     * @return Array of channels
     */
    @DataProvider(name = "channels")
    public Object[][] provideChannels() {
        return java.util.Arrays.stream(HomePage.OurChannels.values())
                .map(channel -> new Object[]{channel})
                .toArray(Object[][]::new);
    }

    /**
     * Verifies the 'Our Channels' header visibility and each link navigation.
     *
     * @param channel Channel link to validate
     */
    @Test(
            dataProvider = "channels",
            description = "Verify each 'Our Channels' footer link navigates correctly in the same tab"
    )
    @Story("Validate 'Our Channels' footer links")
    @Description("Ensures 'Our Channels' header is visible and each link navigates correctly in the same tab.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyOurChannelLinkNavigation(HomePage.OurChannels channel) {
        Allure.step("Scroll to footer section");
        homePage.scrollToBottom();

        // Verify header
        WebElement header = homePage.getOurChannelsHeader();
        Assert.assertTrue(
                header.isDisplayed(),
                "'Our Channels' section header is not visible."
        );
        Assert.assertNotEquals(
                header.getTagName(),
                "a",
                "'Our Channels' header must not be a clickable link."
        );

        // Prepare link element
        WebElement linkElement = homePage.getOurChannelLink(channel);
        String linkText = linkElement.getText().trim();
        Allure.step("Validating channel link: " + linkText);

        Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
        Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");

        // Expected href
        String expectedHref = linkElement.getAttribute("href").trim();

        // Click the link
        homePage.safeClick(linkElement);
        homePage.waitForPageLoaded();

        String actualUrl = driver.getCurrentUrl().trim();

        // Special handling for AJ+
        if (channel == HomePage.OurChannels.AJ_PLUS) {
            Assert.assertTrue(
                    actualUrl.startsWith("https://global.ajplus.net/english/home"),
                    "Expected AJ+ URL to start with 'https://global.ajplus.net/english/home' but was: " + actualUrl
            );
        } else {
            // Normalize URL
            String normalizedExpected = expectedHref
                    .replaceFirst("^https?://", "")
                    .replaceFirst("^www\\.", "");
            String normalizedActual = actualUrl
                    .replaceFirst("^https?://", "")
                    .replaceFirst("^www\\.", "");

            Assert.assertTrue(
                    normalizedActual.startsWith(normalizedExpected),
                    "Expected URL to start with: " + normalizedExpected + " but was: " + actualUrl
            );
        }

        // Assert non-empty title
        String pageTitle = driver.getTitle().trim();
        Allure.step("Page title after navigation: '" + pageTitle + "'");
        Assert.assertFalse(
                pageTitle.isEmpty(),
                "The page title must not be empty."
        );

        // Return to homepage for the next iteration
        if (channel == HomePage.OurChannels.AL_JAZEERA_ENGLISH) {
            driver.get("https://www.aljazeera.com/");
            homePage.waitForPageLoaded();
        } else {
            driver.navigate().back();
            homePage.waitForPageLoaded();
        }

        homePage.scrollToBottom();
        homePage.waitUntilVisible(homePage.getOurChannelsHeader());
    }
}
