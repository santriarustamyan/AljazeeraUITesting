package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This test verifies that the 'Connect' section in the homepage footer
 * is visible and each link correctly navigates in the same tab.
 */
@Epic("Footer")
@Feature("'Connect' Section")
@Story("Validate 'Connect' footer section")
@Owner("Santri")
@Tag("UI")
public class ConnectSectionTest extends BaseTest {

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
     * Verifies that the 'Connect' section header is visible and each link navigates correctly.
     */
    @Test(description = "Verify 'Connect' header visibility and correct navigation of each link in the same tab")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Ensures that the 'Connect' section header is displayed, non-clickable, and all footer links work as expected.")
    public void verifyConnectSectionLinks() {
        // Scroll to footer
        homePage.scrollToBottom();

        // Verify the 'Connect' section header
        WebElement header = homePage.getConnectSectionHeader();
        Assert.assertTrue(
                header.isDisplayed(),
                "'Connect' section header is not visible."
        );
        Assert.assertNotEquals(
                header.getTagName(),
                "a",
                "'Connect' header must not be a clickable link."
        );

        // Iterate over each link in the 'Connect' section
        for (HomePage.ConnectLinks link : HomePage.ConnectLinks.values()) {
            homePage.scrollToBottom();

            WebElement linkElement = homePage.getConnectLink(link);
            String linkText = linkElement.getText().trim();
            Allure.step("Validating 'Connect' link: " + linkText);

            Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
            Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");

            // Get the expected href
            String expectedHref = linkElement.getAttribute("href").trim();

            // Click the link
            homePage.safeClick(linkElement);
            homePage.waitForPageLoaded();
            homePage.waitForTitleNotEmpty();

            // Get the current URL after navigation
            String actualUrl = driver.getCurrentUrl().trim();

            // Special case for Podcasts redirect
            if (link == HomePage.ConnectLinks.PODCASTS) {
                Assert.assertTrue(
                        actualUrl.contains("/audio/podcasts"),
                        "Expected podcasts URL to contain '/audio/podcasts' but was: " + actualUrl
                );
            } else {
                // Normalize URLs by removing protocol and www
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

            // Verify the page title is not empty
            Assert.assertFalse(
                    driver.getTitle().trim().isEmpty(),
                    "The page title must not be empty."
            );

            // Navigate back to the homepage
            driver.navigate().back();
            homePage.waitForPageLoaded();
            homePage.scrollToBottom();
            homePage.waitUntilVisible(homePage.getConnectSectionHeader());
        }
    }
}
