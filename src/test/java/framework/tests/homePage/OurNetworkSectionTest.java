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
 * This test verifies that the 'Our Network' section in the homepage footer
 * is visible and that each link navigates correctly in the same tab.
 */
@Epic("Footer")
@Feature("'Our Network' Section")
@Story("Validate 'Our Network' footer section")
@Owner("Santri")
@Tag("UI")
public class OurNetworkSectionTest extends BaseTest {

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
     * Verifies the 'Our Network' header visibility and that all links navigate correctly.
     */
    @Test(description = "Verify 'Our Network' header visibility and correct navigation of each link in the same tab")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Ensures that the 'Our Network' section header is displayed, non-clickable, and all footer links open the expected destinations.")
    public void verifyOurNetworkSectionLinks() {
        // Scroll to the footer
        homePage.scrollToBottom();

        // Verify the 'Our Network' section header
        WebElement header = homePage.getOurNetworkSectionHeader();
        Assert.assertTrue(
                header.isDisplayed(),
                "'Our Network' section header is not visible."
        );
        Assert.assertNotEquals(
                header.getTagName(),
                "a",
                "'Our Network' header must not be a clickable link."
        );

        // Iterate over each link in the 'Our Network' section
        for (HomePage.OurNetworkLinks link : HomePage.OurNetworkLinks.values()) {
            homePage.scrollToBottom();

            WebElement linkElement = homePage.getOurNetworkLink(link);
            String linkText = linkElement.getText().trim();
            Allure.step("Validating 'Our Network' link: " + linkText);

            Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
            Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");

            // Capture expected href
            String expectedHref = linkElement.getAttribute("href").trim();

            // Click the link and wait for navigation
            homePage.safeClick(linkElement);
            homePage.waitForPageLoaded();
            homePage.waitForTitleNotEmpty();

            String actualUrl = driver.getCurrentUrl().trim();

            // Special handling for MEDIA_INSTITUTE redirect
            if (link == HomePage.OurNetworkLinks.MEDIA_INSTITUTE) {
                Assert.assertTrue(
                        actualUrl.contains("institute.aljazeera.net"),
                        "Expected MEDIA_INSTITUTE URL to contain 'institute.aljazeera.net' but was: " + actualUrl
                );
            } else {
                // Normalize URLs by removing protocol, www, and trailing slashes
                String normalizedExpected = expectedHref
                        .replaceFirst("^https?://", "")
                        .replaceFirst("^www\\.", "")
                        .replaceAll("/$", "");
                String normalizedActual = actualUrl
                        .replaceFirst("^https?://", "")
                        .replaceFirst("^www\\.", "")
                        .replaceAll("/$", "");

                Assert.assertTrue(
                        normalizedActual.startsWith(normalizedExpected),
                        "Expected URL to start with: " + normalizedExpected + " but was: " + actualUrl
                );
            }

            // Verify that the page title is not empty
            Assert.assertFalse(
                    driver.getTitle().trim().isEmpty(),
                    "The page title must not be empty."
            );

            // Navigate back to the homepage and re-validate the header
            driver.navigate().back();
            homePage.waitForPageLoaded();
            homePage.scrollToBottom();
            homePage.waitUntilVisible(homePage.getOurNetworkSectionHeader());
        }
    }
}
