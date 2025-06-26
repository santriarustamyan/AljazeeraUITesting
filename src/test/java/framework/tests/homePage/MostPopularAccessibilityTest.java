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

    @Test(description = "Verify that the Bypass Block menu 'Skip to Most Read' works...")
    @Story("Keyboard navigation to 'Most Popular'")
    @Description("Verify that keyboard navigation using TAB and ENTER triggers 'Skip to Most Read'")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyBypassBlockSkipToMostReadWorks() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        homePage.clickSkipToMostReadLink();

        actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ENTER).perform();

        actions.sendKeys(Keys.ENTER).perform();

        wait.until(ExpectedConditions.urlContains("#most-read-container"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.contains("#most-read-container"), "URL did not change after activating the Bypass Block link");

        Assert.assertTrue(homePage.isMostPopularVisible(), "Most Popular section is not visible after navigation");
    }
}
