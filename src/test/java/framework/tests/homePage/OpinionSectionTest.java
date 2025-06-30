package framework.tests.homePage;

import framework.pages.HomePage;
import framework.pages.OpinionsPostPage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for verifying the Opinion section behavior on the homepage.
 */
@Epic("Content")
@Feature("Opinion Section")
@Owner("Santri")
@Tag("UI")
public class OpinionSectionTest extends BaseTest {

    private HomePage homePage;

    /**
     * Initializes the driver and loads the homepage before each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * Verifies that the Opinion section is visible and contains exactly six posts.
     */
    @Test(description = "Verify Opinion section is visible and contains exactly 6 posts")
    @Story("Opinion Section Visibility and Count")
    @Description("Ensure that the Opinion section is displayed and displays exactly six posts on the homepage")
    @Severity(SeverityLevel.NORMAL)
    public void verifyOpinionSectionVisibleAndContainsSixPosts() {
        Allure.step("Check that the Opinion section is visible");
        Assert.assertTrue(
                homePage.isOpinionSectionVisible(),
                "Opinion section must be visible"
        );

        Allure.step("Check that there are exactly 6 posts in the Opinion section");
        int postCount = homePage.getOpinionPostCount();
        Assert.assertEquals(
                postCount,
                6,
                "Opinion section should contain exactly 6 posts"
        );
    }

    /**
     * Verifies that the creator name and the URL are consistent when navigating to the article page.
     */
    @Test(description = "Verify title and author match between card and article")
    @Story("Opinion Post Details Consistency")
    @Description("Verify that the creator name displayed in the Opinion card matches the author on the article page and that URL is correct")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyArticleTitleAndAuthorMatchAfterClick() {
        Allure.step("Ensure Opinion section is loaded and visible");
        homePage.ensureOpinionSectionVisible();

        Allure.step("Get random creator name from Opinion section card");
        String creatorBeforeClick = homePage.getRandomCreatorName();

        Allure.step("Click on the random post from Opinion section");
        homePage.clickRandomPost();

        Allure.step("Create OpinionsPostPage object");
        OpinionsPostPage postPage = new OpinionsPostPage(driver);

        Allure.step("Get creator name from the article page");
        String creatorAfterClick = postPage.getCreatorName().trim();

        Allure.step("Verify that creator names match");
        Assert.assertTrue(
                creatorAfterClick.toLowerCase().contains(creatorBeforeClick.toLowerCase()),
                String.format("Creator name after click [%s] should contain the name before click [%s]",
                        creatorAfterClick, creatorBeforeClick)
        );

        Allure.step("Verify that URL contains '/opinions/'");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("/opinions/"),
                "URL does not contain '/opinions/' as expected"
        );
    }
}
