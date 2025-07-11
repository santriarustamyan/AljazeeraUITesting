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
// * This test verifies that each link in the 'Our Network' footer section
// * is visible and navigates correctly in the same tab.
// */
//@Epic("Footer")
//@Feature("'Our Network' Section")
//@Story("Validate 'Our Network' footer section")
//@Owner("Santri")
//@Tag("UI")
//public class OurNetworkSectionTest extends BaseTest {
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
//     * DataProvider supplying all OurNetworkLinks enum values.
//     */
//    @DataProvider(name = "ourNetworkLinks")
//    public Object[][] provideOurNetworkLinks() {
//        return java.util.Arrays.stream(HomePage.OurNetworkLinks.values())
//                .map(link -> new Object[]{link})
//                .toArray(Object[][]::new);
//    }
//
//    /**
//     * Verifies that the 'Our Network' section header is visible and
//     * that each link navigates correctly.
//     *
//     * @param link the link to validate
//     */
//    @Test(
//            dataProvider = "ourNetworkLinks",
//            description = "Verify each 'Our Network' footer link navigates correctly in the same tab"
//    )
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Ensures that the 'Our Network' section header is displayed, non-clickable, and each footer link opens the expected destination URL with a non-empty title.")
//    public void verifyOurNetworkLinkNavigation(HomePage.OurNetworkLinks link) {
//        Allure.step("Scroll to the footer section");
//        homePage.scrollToBottom();
//
//        // Verify the 'Our Network' section header
//        WebElement header = homePage.getOurNetworkSectionHeader();
//        Assert.assertTrue(
//                header.isDisplayed(),
//                "'Our Network' section header is not visible."
//        );
//        Assert.assertNotEquals(
//                header.getTagName(),
//                "a",
//                "'Our Network' header must not be a clickable link."
//        );
//
//        // Prepare the link element
//        WebElement linkElement = homePage.getOurNetworkLink(link);
//        String linkText = linkElement.getText().trim();
//        Allure.step("Validating 'Our Network' link: " + linkText);
//
//        Assert.assertTrue(linkElement.isDisplayed(), "Link '" + linkText + "' must be visible.");
//        Assert.assertTrue(linkElement.isEnabled(), "Link '" + linkText + "' must be enabled.");
//
//        // Capture expected href
//        String expectedHref = linkElement.getAttribute("href").trim();
//
//        // Click the link and wait for navigation
//        homePage.safeClick(linkElement);
//        homePage.waitForPageLoaded();
//        homePage.waitForTitleNotEmpty();
//
//        String actualUrl = driver.getCurrentUrl().trim();
//
//        // Special handling for MEDIA_INSTITUTE redirect
//        if (link == HomePage.OurNetworkLinks.MEDIA_INSTITUTE) {
//            Assert.assertTrue(
//                    actualUrl.contains("institute.aljazeera.net"),
//                    "Expected MEDIA_INSTITUTE URL to contain 'institute.aljazeera.net' but was: " + actualUrl
//            );
//        } else {
//            // Normalize URLs by removing protocol, www, and trailing slashes
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
//                    "Expected URL to start with: " + normalizedExpected + " but was: " + actualUrl
//            );
//        }
//
//        // Verify that the page title is not empty
//        String pageTitle = driver.getTitle().trim();
//        Allure.step("Page title after navigation: '" + pageTitle + "'");
//        Assert.assertFalse(
//                pageTitle.isEmpty(),
//                "The page title must not be empty."
//        );
//
//        // Navigate back to the homepage for the next iteration
//        driver.navigate().back();
//        homePage.waitForPageLoaded();
//        homePage.scrollToBottom();
//        homePage.waitUntilVisible(homePage.getOurNetworkSectionHeader());
//    }
//}
