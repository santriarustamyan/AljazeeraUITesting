package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests to verify that the 'Most Popular' section is not displayed in mobile mode.
 */
@Epic("Most Popular Section")
@Feature("Mobile Display")
@Owner("Santri")
@Tag("Mobile")
public class MostPopularMobileTest extends BaseTest {

    private HomePage homePage;

    /**
     * Initializes the mobile driver and opens the homepage before each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        startDriver("mobile");
        homePage = new HomePage(driver);
    }

    /**
     * Verifies that the 'Most Popular' section is not visible on the homepage when accessed in mobile mode.
     */
    @Test(description = "Verify 'Most Popular' section is NOT visible on mobile")
    @Story("Not displayed on mobile")
    @Description("Verify that the 'Most Popular' section is hidden when viewing the site on a mobile device")
    @Severity(SeverityLevel.NORMAL)
    public void verifyMostPopularSectionNotVisibleOnMobile() {
        Assert.assertTrue(homePage.isMostPopularInvisibleOrAbsent(), "'Most Popular' section should not be visible on mobile.");

    }
}
