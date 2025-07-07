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
     * Initializes the WebDriver and loads the homepage before each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * Verifies that the Opinion section is visible and contains exactly six posts.
     */
    @Test(
            description = "Verify that the Opinion section is visible and contains exactly 6 posts"
    )
    @Story("Opinion Section Visibility and Count")
    @Description("Ensure that the Opinion section is displayed and contains exactly six posts on the homepage")
    @Severity(SeverityLevel.NORMAL)
    public void verifyOpinionSectionVisibleAndContainsSixPosts() {
        Allure.step("Scroll to the Opinion section and verify it is visible");
        homePage.ensureOpinionSectionVisible();
        Assert.assertTrue(
                homePage.isOpinionSectionVisible(),
                "Opinion section must be visible."
        );

        Allure.step("Verify that exactly 6 posts are displayed in the Opinion section");
        int postCount = homePage.getOpinionPostCount();
        Assert.assertEquals(
                postCount,
                6,
                "Expected exactly 6 posts in the Opinion section."
        );
    }

    /**
     * Verifies that the creator name and URL are consistent when navigating to the article page.
     */
    @Test(
            description = "Verify that the creator name and title match between the card and article page"
    )
    @Story("Opinion Post Details Consistency")
    @Description("Ensure that the creator name on the Opinion card matches the author on the article page, and that the URL is correct")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyArticleTitleAndAuthorMatchAfterClick() {
        Allure.step("Ensure the Opinion section is visible");
        homePage.ensureOpinionSectionVisible();

        Allure.step("Get the creator name from a random Opinion post card");
        String creatorBeforeClick = homePage.getRandomCreatorName().trim();

        Allure.step("Click a random Opinion post card");
        homePage.clickRandomPost();

        Allure.step("Create an OpinionsPostPage instance to validate article details");
        OpinionsPostPage postPage = new OpinionsPostPage(driver);

        Allure.step("Get the creator name displayed on the article page");
        String creatorAfterClick = postPage.getCreatorName().trim();

        Allure.step("Validate that the creator names match between the card and article");
        Assert.assertTrue(
                creatorAfterClick.toLowerCase().contains(creatorBeforeClick.toLowerCase()),
                String.format(
                        "Expected the creator name [%s] on the article page to contain [%s]",
                        creatorAfterClick,
                        creatorBeforeClick
                )
        );

        Allure.step("Validate that the URL contains '/opinions/'");
        String currentUrl = driver.getCurrentUrl().trim();
        Assert.assertTrue(
                currentUrl.contains("/opinions/"),
                "Expected the URL to contain '/opinions/'. Actual URL: " + currentUrl
        );
    }
}
