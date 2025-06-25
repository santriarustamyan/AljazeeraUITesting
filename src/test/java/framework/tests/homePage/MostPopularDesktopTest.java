package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MostPopularDesktopTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setup() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    @Test(description = "Verify that 'Most Popular' section is visible on Desktop")
    public void verifyMostPopularSectionIsVisible() {
        Assert.assertTrue(homePage.isMostPopularVisible(), "'Most Popular' section is not visible on Desktop.");
    }

    @Test(description = "Verify that 'Most Popular' section has exactly 10 items on Desktop")
    public void verifyTrendingArticlesCountIsTen() {
        int count = homePage.getMostPopularPostsCount();
        Assert.assertEquals(count, 10, "Expected 10 trending articles but found: " + count);
    }
}
