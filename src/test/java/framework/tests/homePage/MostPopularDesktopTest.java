package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Most Popular Section")
@Feature("Desktop Display")
@Owner("Santri")
@Tag("UI")
public class MostPopularDesktopTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setup() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    @Test(description = "Verify that 'Most Popular' section is visible on Desktop")
    @Story("Display on desktop")
    @Description("Verify that 'Most Popular' section is visible on desktop")
    @Severity(SeverityLevel.NORMAL)
    public void verifyMostPopularSectionIsVisible() {
        Assert.assertTrue(homePage.isMostPopularVisible(), "'Most Popular' section is not visible on Desktop.");
    }

    @Test(description = "Verify that 'Most Popular' section has exactly 10 items on Desktop")
    @Story("Item count verification")
    @Description("Ensure there are exactly 10 items in 'Most Popular' section")
    @Severity(SeverityLevel.MINOR)
    public void verifyTrendingArticlesCountIsTen() {
        int count = homePage.getMostPopularPostsCount();
        Assert.assertEquals(count, 10, "Expected 10 trending articles but found: " + count);
    }
}
