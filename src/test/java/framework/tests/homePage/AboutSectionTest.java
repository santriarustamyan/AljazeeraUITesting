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
 * This test verifies that the 'About' section in the homepage footer
 * is visible, non-clickable, and each link correctly navigates.
 */
@Epic("Footer")
@Feature("'About' Section")
@Owner("Santri")
@Tag("UI")
public class AboutSectionTest extends BaseTest {

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
     * Verifies the 'About' section header visibility and links' navigation.
     */
    @Test(description = "Verify 'About' header is visible and links navigate correctly in the same tab")
    @Story("Validate 'About' footer section")
    @Description("Ensures 'About' header is visible and each link navigates properly in the same tab.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAboutSectionLinks() {
        // Scroll to footer
        homePage.scrollToBottom();

        // Verify header
        WebElement header = homePage.getAboutSectionHeader();
        Assert.assertTrue(
                header.isDisplayed(),
                "'About' section header is not visible."
        );
        Assert.assertNotEquals(
                header.getTagName(),
                "a",
                "'About' header must not be a clickable link."
        );

        // Iterate over each link
        for (HomePage.AboutLinks link : HomePage.AboutLinks.values()) {
            homePage.scrollToBottom();

            WebElement linkElement = homePage.getAboutLink(link);
            String linkText = linkElement.getText().trim();
            Allure.step("Validating about link: " + linkText);

            Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
            Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");


            // Special case: Cookie Preferences opens a pop-up
            if (link == HomePage.AboutLinks.COOKIE_PREFERENCES) {

                homePage.safeClick(linkElement);
                boolean wasClosed = homePage.closeCookiePreferencesModalIfPresent();
                Allure.step("Cookie preferences modal closed: " + wasClosed);
                Assert.assertTrue(wasClosed, "Cookie preferences modal should have appeared and been closed.");
                continue;
            }
            // Expected href
            String expectedHref = linkElement.getAttribute("href").trim();

            // Click link
            homePage.safeClick(linkElement);
            homePage.waitForPageLoaded();

            homePage.waitForTitleNotEmpty();

            String actualUrl = driver.getCurrentUrl().trim();

            // Normalize URL to avoid protocol and www differences
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

            String pageTitle = driver.getTitle().trim();
            Allure.step("Page title after navigation: '" + pageTitle + "'");
            Assert.assertFalse(
                    pageTitle.isEmpty(),
                    "The page title must not be empty. Current URL: " + driver.getCurrentUrl()
            );

            // Navigation back or reopen homepage
            driver.navigate().back();
            homePage.waitForPageLoaded();
            homePage.scrollToBottom();

            homePage.waitUntilVisible(homePage.getAboutSectionHeader());
        }
    }
}
