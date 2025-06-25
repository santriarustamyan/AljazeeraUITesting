package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MostPopularMobileTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        startDriver("mobile");
        homePage = new HomePage(driver);
    }

    @Test(description = "Verify 'Most Popular' section is NOT visible on mobile")
    public void verifyMostPopularSectionNotVisibleOnMobile() {
        Assert.assertFalse(homePage.isMostPopularVisible(), "'Most Popular' section should not be visible on mobile");
    }
}
