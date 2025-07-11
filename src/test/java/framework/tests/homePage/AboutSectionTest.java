//package framework.tests.homePage;
//
//import framework.pages.HomePage;
//import framework.tests.basePage.BaseTest;
//import io.qameta.allure.*;
//import io.qameta.allure.testng.Tag;
//import org.openqa.selenium.WebElement;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
///**
// * This test verifies that each link in the 'About' footer section
// * is visible and navigates correctly.
// */
//@Epic("Footer")
//@Feature("'About' Section")
//@Story("Validate 'About' footer section")
//@Owner("Santri")
//@Tag("UI")
//public class AboutSectionTest extends BaseTest {
//
//    private HomePage homePage;
//
//    /**
//     * Initializes WebDriver and opens the homepage before each test.
//     */
//    @BeforeMethod(alwaysRun = true)
//    public void setUp() {
//        startDriver("desktop");
//        homePage = new HomePage(driver);
//    }
//
//    /**
//     * DataProvider supplying all AboutLinks enum values.
//     */
//    @DataProvider(name = "aboutLinks")
//    public Object[][] provideAboutLinks() {
//        return java.util.Arrays.stream(HomePage.AboutLinks.values())
//                .map(link -> new Object[]{link})
//                .toArray(Object[][]::new);
//    }
//
//    /**
//     * Verifies that each 'About' link is visible, enabled, and navigates correctly.
//     *
//     * @param link the link to validate
//     */
//    @Test(
//            dataProvider = "aboutLinks",
//            description = "Verify each 'About' footer link navigates correctly in the same tab"
//    )
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Ensures each 'About' footer link is visible, enabled, and navigates to the correct URL with a non-empty title.")
//    public void verifyAboutSectionLink(HomePage.AboutLinks link) {
//        Allure.step("Scroll to the footer");
//        homePage.scrollToBottom();
//
//        // Verify header visibility
//        WebElement header = homePage.getAboutSectionHeader();
//        Assert.assertTrue(
//                header.isDisplayed(),
//                "'About' section header must be visible."
//        );
//        Assert.assertNotEquals(
//                header.getTagName(),
//                "a",
//                "'About' section header must not be a clickable link."
//        );
//
//        // Prepare link
//        WebElement linkElement = homePage.getAboutLink(link);
//        String linkText = linkElement.getText().trim();
//        Allure.step("Validating 'About' link: " + linkText);
//
//        Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
//        Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");
//
//        // Special case: Cookie Preferences modal
//        if (link == HomePage.AboutLinks.COOKIE_PREFERENCES) {
//            Allure.step("Opening Cookie Preferences modal");
//            homePage.safeClick(linkElement);
//            boolean wasClosed = homePage.closeCookiePreferencesModalIfPresent();
//            Allure.step("Cookie Preferences modal closed: " + wasClosed);
//            Assert.assertTrue(
//                    wasClosed,
//                    "Cookie Preferences modal should have appeared and been closed."
//            );
//            return; // Skip further checks for this case
//        }
//
//        // Expected href
//        String expectedHref = linkElement.getAttribute("href").trim();
//
//        // Act
//        homePage.safeClick(linkElement);
//        homePage.waitForPageLoaded();
//        homePage.waitForTitleNotEmpty();
//
//        // Assert URL
//        String actualUrl = driver.getCurrentUrl().trim();
//        String normalizedExpected = expectedHref
//                .replaceFirst("^https?://", "")
//                .replaceFirst("^www\\.", "")
//                .replaceAll("/$", "");
//        String normalizedActual = actualUrl
//                .replaceFirst("^https?://", "")
//                .replaceFirst("^www\\.", "")
//                .replaceAll("/$", "");
//
//        Assert.assertTrue(
//                normalizedActual.startsWith(normalizedExpected),
//                "Expected URL to start with: " + normalizedExpected + ", but was: " + actualUrl
//        );
//
//        // Assert page title
//        String pageTitle = driver.getTitle().trim();
//        Allure.step("Page title after navigation: '" + pageTitle + "'");
//        Assert.assertFalse(
//                pageTitle.isEmpty(),
//                "The page title must not be empty. Current URL: " + driver.getCurrentUrl()
//        );
//
//        // Navigate back to homepage
//        driver.navigate().back();
//        homePage.waitForPageLoaded();
//        homePage.scrollToBottom();
//        homePage.waitUntilVisible(homePage.getAboutSectionHeader());
//    }
//}
