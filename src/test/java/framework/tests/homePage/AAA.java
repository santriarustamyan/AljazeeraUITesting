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
// * This test verifies that each link in the 'Connect' footer section
// * is visible, enabled, and navigates correctly.
// */
//@Epic("Footer")
//@Feature("'Connect' Section")
//@Story("Validate 'Connect' footer section")
//@Owner("Santri")
//@Tag("UI")
//public class AAA extends BaseTest {
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
//     * DataProvider supplying all ConnectLinks enum values.
//     */
//    @DataProvider(name = "connectLinks")
//    public Object[][] provideConnectLinks() {
//        return java.util.Arrays.stream(HomePage.ConnectLinks.values())
//                .map(link -> new Object[]{link})
//                .toArray(Object[][]::new);
//    }
//
//    /**
//     * Verifies that each 'Connect' link is visible, enabled, and navigates correctly.
//     *
//     * @param link the link to validate
//     */
//    @Test(
//            dataProvider = "connectLinks",
//            description = "Verify each 'Connect' footer link navigates correctly in the same tab"
//    )
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Ensures each 'Connect' footer link is visible, enabled, and navigates to the correct URL with a non-empty title.")
//    public void verifyConnectSectionLink(HomePage.ConnectLinks link) {
//        Allure.step("Scroll to the footer");
//        homePage.scrollToBottom();
//
//        // Verify the 'Connect' section header visibility
//        WebElement header = homePage.getConnectSectionHeader();
//        Assert.assertTrue(
//                header.isDisplayed(),
//                "'Connect' section header must be visible."
//        );
//        Assert.assertNotEquals(
//                header.getTagName(),
//                "a",
//                "'Connect' section header must not be a clickable link."
//        );
//
//        // Prepare link
//        WebElement linkElement = homePage.getConnectLink(link);
//        String linkText = linkElement.getText().trim();
//        Allure.step("Validating 'Connect' link: " + linkText);
//
//        Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
//        Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");
//
//        // Capture expected href
//        String expectedHref = linkElement.getAttribute("href").trim();
//
//        // Act: click the link
//        homePage.safeClick(linkElement);
//        homePage.waitForPageLoaded();
//        homePage.waitForTitleNotEmpty();
//
//        String actualUrl = driver.getCurrentUrl().trim();
//
//        // Special case: Podcasts redirect
//        if (link == HomePage.ConnectLinks.PODCASTS) {
//            Assert.assertTrue(
//                    actualUrl.contains("/audio/podcasts"),
//                    "Expected Podcasts URL to contain '/audio/podcasts' but was: " + actualUrl
//            );
//        } else {
//            // Normalize URLs
//            String normalizedExpected = expectedHref
//                    .replaceFirst("^https?://", "")
//                    .replaceFirst("^www\\.", "")
//                    .replaceAll("/$", "");
//            String normalizedActual = actualUrl
//                    .replaceFirst("^https?://", "")
//                    .replaceFirst("^www\\.", "")
//                    .replaceAll("/$", "");
//
//            Assert.assertTrue(
//                    normalizedActual.startsWith(normalizedExpected),
//                    "Expected URL to start with: " + normalizedExpected + ", but was: " + actualUrl
//            );
//        }
//
//        // Verify page title
//        String pageTitle = driver.getTitle().trim();
//        Allure.step("Page title after navigation: '" + pageTitle + "'");
//        Assert.assertFalse(
//                pageTitle.isEmpty(),
//                "The page title must not be empty. Current URL: " + driver.getCurrentUrl()
//        );
//
//        // Navigate back
//        driver.navigate().back();
//        homePage.waitForPageLoaded();
//        homePage.scrollToBottom();
//        homePage.waitUntilVisible(homePage.getConnectSectionHeader());
//    }
//}