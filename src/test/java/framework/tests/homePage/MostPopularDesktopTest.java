//package framework.tests.homePage;
//
//import framework.pages.HomePage;
//import framework.tests.basePage.BaseTest;
//import io.qameta.allure.*;
//import io.qameta.allure.testng.Tag;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
///**
// * Tests to verify the visibility and item count of the 'Most Popular' section
// * on the homepage when accessed in desktop mode.
// */
//@Epic("Most Popular Section")
//@Feature("Desktop Display")
//@Owner("Santri")
//@Tag("UI")
//public class MostPopularDesktopTest extends BaseTest {
//
//    private HomePage homePage;
//
//    /**
//     * Initializes the desktop driver and opens the homepage before each test.
//     */
//    @BeforeMethod(alwaysRun = true)
//    public void setup() {
//        startDriver("desktop");
//        homePage = new HomePage(driver);
//    }
//
//    /**
//     * Verifies that the 'Most Popular' section is visible on the homepage in desktop mode.
//     */
//    @Test(description = "Verify that 'Most Popular' section is visible on Desktop")
//    @Story("Display on desktop")
//    @Description("Ensure that the 'Most Popular' section is displayed when viewed on a desktop resolution")
//    @Severity(SeverityLevel.NORMAL)
//    public void verifyMostPopularSectionIsVisible() {
//        Allure.step("Checking if 'Most Popular' section is displayed on the homepage");
//        boolean isVisible = homePage.isMostPopularVisible();
//
//        Assert.assertTrue(
//                isVisible,
//                "'Most Popular' section is not visible on Desktop."
//        );
//    }
//
//    /**
//     * Verifies that the 'Most Popular' section contains exactly 10 posts.
//     */
//    @Test(description = "Verify that 'Most Popular' section has exactly 10 items on Desktop")
//    @Story("Item count verification")
//    @Description("Ensure that there are exactly 10 articles listed in the 'Most Popular' section")
//    @Severity(SeverityLevel.MINOR)
//    public void verifyTrendingArticlesCountIsTen() {
//        Allure.step("Retrieving number of posts in 'Most Popular' section");
//        int actualCount = homePage.getMostPopularPostsCount();
//
//        Allure.step("Validating that there are exactly 10 posts (found: " + actualCount + ")");
//        Assert.assertEquals(
//                actualCount,
//                10,
//                "Expected 10 trending articles but found: " + actualCount
//        );
//    }
//}
