package framework.tests.gallery;

import framework.pages.GalleryPostPage;
import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Frontend Gallery")
@Feature("Gallery Post Validation")
@Owner("Santri")
@Tag("UI")
public class GalleryPostTest extends BaseTest {

    private GalleryPostPage galleryPage;
    private HomePage homePage;

    /**
     * Validates full gallery post flow on desktop:
     * - Navigation
     * - Post structure
     * - Gallery modal
     * - Image switching
     * - Zoom
     * - Newsletter and related sections
     */
    @Test(description = "[Desktop] Full Gallery Post Flow")
    @Story("Desktop Validation")
    @Severity(SeverityLevel.BLOCKER)
    public void testGalleryPostDesktop() {

        Allure.step("Start desktop browser");
        startDriver("desktop");

        homePage = new HomePage(driver);
        galleryPage = new GalleryPostPage(driver);

        Allure.step("Navigate to 'In Pictures' section via menu");
        homePage.clickMainMenu(HomePage.MainMenu.MORE);
        homePage.clickMoreSubMenu(HomePage.MoreSubMenu.IN_PICTURES);

        Allure.step("Click on the first gallery card");
        galleryPage.clickFirstGalleryImage();

        Allure.step("Validate that post title is visible and not empty");
        Assert.assertFalse(galleryPage.getPostTitleText().isEmpty(), "Post title is missing");

        Allure.step("Validate that meta information (date) is visible");
        Assert.assertTrue(galleryPage.isPostMetaVisible(), "Post meta info is missing");

        Allure.step("Validate that article body is visible");
        Assert.assertTrue(galleryPage.isArticleBodyVisible(), "Article body is not visible");

        Allure.step("Click on the article image to open gallery modal");
        galleryPage.openFirstGalleryImage();
        Assert.assertTrue(galleryPage.isGalleryModalVisible(), "Gallery modal is not visible");

        Allure.step("Navigate to the next image in gallery");
        String prevSrc = galleryPage.getCurrentGalleryImageSrc();
        galleryPage.clickGalleryNextButton();
        Assert.assertTrue(galleryPage.isGalleryImageChanged(prevSrc), "Image did not change");

        Allure.step("Zoom in on gallery image");
        galleryPage.zoomInGalleryModal();
        Assert.assertTrue(galleryPage.isZoomImageVisible(), "Zoom is not working");

        Allure.step("Close the gallery modal");
        galleryPage.closeGalleryModal();
        Assert.assertTrue(galleryPage.isGalleryModalClosed(), "Modal did not close");

        Allure.step("Validate that newsletter widget is visible");
        Assert.assertTrue(galleryPage.isNewsletterWidgetVisible(), "Newsletter widget not visible");

        Allure.step("Validate that related section and posts are visible");
        Assert.assertTrue(galleryPage.isRelatedSectionVisible(), "Related section is not visible");
        Assert.assertTrue(galleryPage.areRelatedPostsVisible(), "No related posts found");
    }

    /**
     * Validates the "More From Topic" section on mobile devices
     */
    @Test(description = "[Mobile] Validate More From Topic Section")
    @Story("Mobile Validation")
    @Severity(SeverityLevel.CRITICAL)
    public void testGalleryPostMobile() {

        Allure.step("Start mobile browser");
        startDriver("mobile");

        galleryPage = new GalleryPostPage(driver);
        homePage = new HomePage(driver);

        Allure.step("Navigate to 'In Pictures' section on mobile");
        homePage.clickMainMenu(HomePage.MainMenu.MENU_FOR_MOBILE);
        homePage.clickMainMenu(HomePage.MainMenu.MORE);
        homePage.clickMoreSubMenu(HomePage.MoreSubMenu.IN_PICTURES);

        Allure.step("Click the first gallery post");
        galleryPage.clickFirstGalleryImage();

        Allure.step("Scroll to 'More From Topic' section and validate visibility");
        galleryPage.scrollToMoreFromTopic();
        Assert.assertTrue(galleryPage.isMoreFromTopicSectionVisible(), "'More From Topic' section missing");
        Assert.assertTrue(galleryPage.areMoreFromTopicPostsVisible(), "'More From Topic' posts not visible");

        Allure.step("Click first post in 'More From Topic' and validate navigation");
        galleryPage.clickFirstMoreFromTopicPost();
        galleryPage.waitForGalleryPage();

        Assert.assertFalse(galleryPage.getPostTitleText().isEmpty(), "New post did not load properly");
    }

    /**
     * Validates AMP version of a gallery post:
     * - AMP structure
     * - AMP body
     * - Related content
     */
    @Test(description = "[AMP] Validate Gallery Post via generated AMP URL")
    @Story("AMP Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testGalleryPostAMPViaHome() {

        Allure.step("Start desktop browser");
        startDriver("desktop");

        galleryPage = new GalleryPostPage(driver);
        homePage = new HomePage(driver);

        Allure.step("Navigate to 'In Pictures' section via menu");
        homePage.clickMainMenu(HomePage.MainMenu.MORE);
        homePage.clickMoreSubMenu(HomePage.MoreSubMenu.IN_PICTURES);

        Allure.step("Open first gallery post");
        galleryPage.clickFirstGalleryImage();

        Allure.step("Get current post URL");
        String regularUrl = driver.getCurrentUrl();

        Allure.step("Open AMP version of the post");
        galleryPage.openAMPVersion(regularUrl);

        Allure.step("Wait for AMP page to fully load");
        galleryPage.waitForAMPPage();

        Allure.step("Check if page is AMP-formatted");
        Assert.assertTrue(galleryPage.isAmpPage(), "Page is not AMP-compliant");

        Allure.step("Validate AMP title is present");
        Assert.assertFalse(galleryPage.getPostTitleText().isEmpty(), "AMP title is missing");

        Allure.step("Validate AMP article body content");
        Assert.assertTrue(galleryPage.isArticleBodyAMPVisible(), "AMP article body missing");

        Allure.step("Validate presence of related section");
        Assert.assertTrue(galleryPage.isRelatedSectionVisible(), "Related section missing on AMP");
    }
}
