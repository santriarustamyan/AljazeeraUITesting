package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

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
     * Verifies the 'Our Channels' header visibility and links' navigation.
     */
    @Test(description = "Verify 'Our Channels' header is visible and links navigate correctly in the same tab")
    @Story("Validate 'Our Channels' footer section")
    @Description("Ensures 'Our Channels' header is visible and each link navigates properly in the same tab.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyOurChannelsSectionLinks() {
        // Scroll to footer
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

        // Iterate over each link
        for (HomePage.OurChannels channel : HomePage.OurChannels.values()) {
            homePage.scrollToBottom();

            WebElement linkElement = homePage.getOurChannelLink(channel);
            String linkText = linkElement.getText().trim();
            Allure.step("Validating channel link: " + linkText);

            Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
            Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");

            // Expected href without protocol
            String expectedHref = linkElement.getAttribute("href").trim();

            // Click link
            homePage.safeClick(linkElement);
            homePage.waitForPageLoaded();

            // Actual URL without protocol
            String actualUrl = driver.getCurrentUrl().trim();

            // Special case for AJ+
            if (channel == HomePage.OurChannels.AJ_PLUS) {
                Assert.assertTrue(
                        actualUrl.startsWith("https://global.ajplus.net/english/home"),
                        "Expected AJ+ URL to start with 'https://global.ajplus.net/english/home' but was: " + actualUrl
                );
            } else {
                // Normalize by removing protocol and www
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

            // Verify page title
            Assert.assertFalse(
                    driver.getTitle().trim().isEmpty(),
                    "The page title must not be empty."
            );

            // Navigation back logic
            if (channel == HomePage.OurChannels.AL_JAZEERA_ENGLISH) {
                // Reopen the original homepage because it's overwritten
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

}
