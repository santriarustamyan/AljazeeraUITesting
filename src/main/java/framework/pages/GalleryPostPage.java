package framework.pages;

import framework.base.BasePage;
import framework.config.Config;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GalleryPostPage extends BasePage {

    public GalleryPostPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================

    // First image preview card in the gallery section
    private final By firstGalleryImage = By.className("responsive-image");

    // Title of the first gallery article card
    private final By firstGalleryCardTitle = By.className("article-card__title");

    // Post title (inside the article page)
    private final By postTitle = By.xpath("//header[@class='article-header'] //h1");

    // Meta date/time (e.g., publish date)
    private final By postMeta = By.xpath("(//*[@class='date-simple']//span)[2]");

    // Article body images (standard)
    private final By articleBody = By.className("responsive-image");

    // Article body images (AMP version)
    private final By articleAMPBody = By.xpath("//*[contains(@class, 'i-amphtml-element') and contains(@class, 'i-amphtml-layout-responsive') and contains(@class, 'i-amphtml-built')]");

    // Gallery modal wrapper
    private final By galleryModal = By.className("lg-img-wrap");

    // Currently visible image inside the gallery modal
    private final By galleryImage = By.cssSelector(".lg-current .lg-object.lg-image");

    // Next button in the gallery modal
    private final By galleryNextButton = By.cssSelector("[aria-label='Next slide']");

    // Zoom button inside the gallery modal
    private final By galleryZoomImage = By.id("lg-zoom-in");

    // Close button in the gallery modal
    private final By galleryCloseBtn = By.cssSelector(".lg-close.lg-icon");

    // Newsletter subscription form widget
    private final By newsletterWidget = By.cssSelector(".sib-newsletter-form.general-style");

    // "Related" section title element
    private final By relatedSection = By.id("article-related-title");

    // List of related article posts
    private final By relatedPosts = By.cssSelector("#article-related ul");

    // "More From Topic" section (shown in mobile layout)
    private final By moreFromTopicSection = By.id("article-more-from-topic-title");

    private final By moreFromTopicPosts = By.cssSelector(".article-more-from-topic .article-pre-footer__list");

    // AMP page identifier: <html amp>
    private final By ampLogo = By.cssSelector("html[amp]");

    // Close button for reading list popup
    private final By popUpCloseBtn = By.cssSelector(".tooltip_close-btn");

    // ================= ACTIONS & CHECKS =================

    // Click on the first gallery card
    public void clickFirstGalleryImage() {
        safeClick(firstGalleryCardTitle);
    }

    // Get the text of the first gallery title
    public String getFirstGalleryCardTitleText() {
        return waitForVisibility(firstGalleryCardTitle).getText();
    }

    // Get the post title
    public String getPostTitleText() {
        return waitForVisibility(postTitle).getText();
    }

    // Check if post metadata is visible
    public boolean isPostMetaVisible() {
        return isDisplayed(postMeta);
    }

    // Check if AMP article body is visible
    public boolean isArticleBodyAMPVisible() {
        return isDisplayed(articleAMPBody);
    }

    // Check if standard article body is visible
    public boolean isArticleBodyVisible() {
        return isDisplayed(articleBody);
    }

    // Check if the gallery modal is currently visible
    public boolean isGalleryModalVisible() {
        return isDisplayed(galleryModal);
    }

    // Click the "Next" button in the gallery modal
    public void clickGalleryNextButton() {
        safeClick(galleryNextButton);
    }

    // Check if the zoom icon is visible in the modal
    public boolean isZoomImageVisible() {
        return isDisplayed(galleryZoomImage);
    }

    // Scroll to and check if the newsletter widget is visible
    public boolean isNewsletterWidgetVisible() {
        scrollToElement(newsletterWidget);
        return isDisplayed(newsletterWidget);
    }

    // Check if the related section is visible
    public boolean isRelatedSectionVisible() {
        scrollToElement(relatedSection);
        return isDisplayed(relatedSection);
    }

    // Check if related posts under the related section are visible
    public boolean areRelatedPostsVisible() {
        return isDisplayed(relatedPosts);
    }

    // Check if "More From Topic" section is visible (mobile)
    public boolean isMoreFromTopicSectionVisible() {
        return isDisplayed(moreFromTopicSection);
    }

    // Check if posts in "More From Topic" are visible
    public boolean areMoreFromTopicPostsVisible() {
        return isDisplayed(moreFromTopicPosts);
    }

    // Check if current page is an AMP page
    public boolean isAmpPage() {
        return isDisplayed(ampLogo);
    }

    // ================= NEW METHODS =================

    // Click the first image inside the article to open modal
    public void openFirstGalleryImage() {
        waitForVisibility(articleBody).click();
    }

    // Get current image URL shown in the modal
    public String getCurrentGalleryImageSrc() {
        return getAttribute(galleryImage, "src");
    }

    // Wait for the gallery image source to change after clicking next
    public boolean isGalleryImageChanged(String previousImageSrc) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(driver -> {
                String currentSrc = getCurrentGalleryImageSrc();
                return !currentSrc.equals(previousImageSrc);
            });
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Zoom in on image in the gallery modal
    public void zoomInGalleryModal() {
        waitForVisibility(galleryZoomImage).click();
    }

    // Close the gallery modal
    public void closeGalleryModal() {
        safeClick(galleryCloseBtn);
    }

    // Verify if the gallery modal has been closed
    public boolean isGalleryModalClosed() {
        return !isDisplayed(galleryModal);
    }

    // Scroll down to "More From Topic" section and close popup if necessary
    public void scrollToMoreFromTopic() {
        scrollToBottom();
        closeReadingListPopupIfPresent(popUpCloseBtn);
        scrollToBottom();
    }

    // Click the first article in "More From Topic"
    public void clickFirstMoreFromTopicPost() {
        waitForVisibility(moreFromTopicPosts).click();
    }

    // Wait until the gallery post content is loaded
    public void waitForGalleryPage() {
        waitForVisibility(postTitle);
    }

    // Wait for AMP-specific page content to load
    public void waitForAMPPage() {
        waitForVisibility(postTitle); // Can be updated to a more AMP-specific element if needed
    }

    // Build and open AMP version of a regular post URL
    public void openAMPVersion(String regularUrl) {
        Allure.step("Build AMP URL");
        String ampUrl = regularUrl.replace(Config.get("base.url"), Config.get("base.url") + "amp/");
        Allure.step("Navigate to AMP URL: " + ampUrl);
        driver.get(ampUrl);
    }
}
