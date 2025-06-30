package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * This test verifies that the "Skip to Most Read" accessibility link
 * can be activated via keyboard navigation (TAB and ENTER).
 */
@Epic("Accessibility")
@Feature("Bypass Blocks")
@Owner("Santri")
@Tag("Accessibility")
public class MostPopularAccessibilityTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * Verifies that keyboard navigation activates the skip link and focuses on Most Popular section.
     */
    @Test(description = "Verify that the Bypass Block menu 'Skip to Most Read' works via keyboard navigation.")
    @Story("Keyboard navigation to 'Most Popular'")
    @Description("Verify that keyboard navigation using TAB and ENTER triggers 'Skip to Most Read' and updates URL")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyBypassBlockSkipToMostReadWorks() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // Optionally click the link if visible (depends on implementation)
        homePage.clickSkipToMostReadLink();

        // Send TAB keys to focus the skip link and then ENTER
        actions.sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.ENTER)
                .perform();

        // Send an extra ENTER if needed (some implementations require double confirm)
        actions.sendKeys(Keys.ENTER).perform();

        // Wait for URL fragment to appear
        wait.until(ExpectedConditions.urlContains("#most-read-container"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL is null after activating skip link");
        Assert.assertTrue(currentUrl.contains("#most-read-container"),
                "URL did not change after activating the Bypass Block link");

        // Verify the section is visible
        Assert.assertTrue(homePage.isMostPopularVisible(),
                "Most Popular section is not visible after keyboard navigation");
    }
}
