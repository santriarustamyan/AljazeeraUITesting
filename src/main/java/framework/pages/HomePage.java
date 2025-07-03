package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Top navigation main menu items
     */
    public enum MainMenu {
        NEWS("//a[@href='/news/']"),
        MIDDLE_EAST("//a[@href='/middle-east/']"),
        EXPLAINED("//a[@href='/tag/explainer/']"),
        OPINION("//a[@href='/opinion/']"),
        SPORT("//a[@href='/sports/']"),
        VIDEO("//a[@href='/videos/']"),
        MORE("//button[text()='More']"),
        LIVE("//div[@class='site-header__live-cta']//a[@href='/live']"),
        SIGNUP("//div[@class='site-header__account']//button[@id='user-accounts-tooltip']");

        private final String xpath;

        MainMenu(String xpath) {
            this.xpath = xpath;
        }

        public By getLocator() {
            return By.xpath(xpath);
        }
    }

    /**
     * Submenus under News section
     */
    public enum NewsSubMenu {
        AFRICA("//a[@href='/africa/']"),
        ASIA("//a[@href='/asia/']"),
        US_CANADA("//a[@href='/us-canada/']"),
        LATIN_AMERICA("//a[@href='/latin-america/']"),
        EUROPE("//a[@href='/europe/']"),
        ASIA_PACIFIC("//a[@href='/asia-pacific/']");

        private final String xpath;

        NewsSubMenu(String xpath) {
            this.xpath = xpath;
        }

        public By getLocator() {
            return By.xpath(xpath);
        }
    }

    /**
     * Submenus under More section
     */
    public enum MoreSubMenu {
        FEATURES("//a[@href='/features/']"),
        ECONOMY("//a[@href='/economy/']"),
        HUMAN_RIGHTS("//a[@href='/tag/human-rights/']"),
        CLIMATE_CRISIS("//a[span[text()='Climate Crisis']]"),
        INVESTIGATIONS("//a[@href='/investigations/']"),
        INTERACTIVES("//a[@href='/interactives/']"),
        IN_PICTURES("//a[@href='/gallery/']"),
        SCIENCE_TECHNOLOGY("//a[@href='/tag/science-and-technology/']"),
        PODCASTS("//a[@href='/audio/podcasts']");

        private final String xpath;

        MoreSubMenu(String xpath) {
            this.xpath = xpath;
        }

        public By getLocator() {
            return By.xpath(xpath);
        }
    }

    /**
     * Social media links in the footer/header
     */
    public enum SocialMediaLinks {
        FACEBOOK("//a[@aria-label='Follow us on Facebook']"),
        X("//a[@aria-label='Follow us on Twitter']"),
        YOUTUBE("//a[@aria-label='Follow us on Youtube']"),
        INSTAGRAM("//a[@aria-label='Follow us on Instagram']");

        private final String xpath;

        SocialMediaLinks(String xpath) {
            this.xpath = xpath;
        }

        public By getLocator() {
            return By.xpath(xpath);
        }
    }

    // Locators for Most Popular section
    private final By mostPopularSection = By.className("trending-articles__header-title");
    private final By mostPopularPostsCount = By.cssSelector("ol.trending-articles__list li");
    private final By skipToMostReadLink = By.cssSelector(".screen-reader-text");

    // Locators for Opinion section
    private final By opinionSectionTitle = By.cssSelector(".card-opinion-collection__heading");
    private final By opinionPosts = By.cssSelector("ul.card-opinion-collection__list li");
    private final By opinionPostCreator = By.className("card-opinion-collection__article__authorlink");

    // 'About' footer section header
    private final By aboutSectionHeader = By.xpath("(//ul[@class='menu footer-menu']//li[@class='menu__item menu__item--aje menu__item--has-submenu']//h2)[1]");

    // 'Our Channels' footer section header
    private final By ourChannelsHeader = By.xpath("(//ul[@class='menu footer-menu']//li[@class='menu__item menu__item--aje menu__item--has-submenu']//h2)[3]");

    private int lastRandomIndex = -1;

    // ------------------- Menu Methods -------------------

    public void clickMainMenu(MainMenu menu) {
        safeClick(menu.getLocator());
    }

    public String getMainMenuText(MainMenu menu) {
        return getText(menu.getLocator());
    }

    public void clickNewsSubMenu(NewsSubMenu subMenu) {
        safeClick(subMenu.getLocator());
    }

    public String getNewsSubMenuText(NewsSubMenu subMenu) {
        return getText(subMenu.getLocator());
    }

    public void clickMoreSubMenu(MoreSubMenu subMenu) {
        safeClick(subMenu.getLocator());
    }

    public String getMoreSubMenuText(MoreSubMenu subMenu) {
        return getText(subMenu.getLocator());
    }

    public void clickSocialMediaLink(SocialMediaLinks link) {
        safeClick(link.getLocator());
    }

    public boolean isSocialLinkVisible(SocialMediaLinks link) {
        return isDisplayed(link.getLocator());
    }

    // ------------------- Most Popular Methods -------------------

    public boolean isMostPopularVisible() {
        scrollToElement(mostPopularSection);
        return isDisplayed(mostPopularSection);
    }

    /**
     * Checks whether the 'Most Popular' section is present in the DOM without considering visibility.
     *
     * @return true if the section is present, false otherwise
     */
    public boolean isMostPopularPresent() {
        return isElementPresent(mostPopularSection);
    }

    /**
     * Checks whether the 'Most Popular' section is either invisible or absent from the DOM.
     *
     * @return true if the section is invisible or absent
     */
    public boolean isMostPopularInvisibleOrAbsent() {
        return isInvisibleOrAbsent(mostPopularSection);
    }

    public int getMostPopularPostsCount() {
        scrollToElement(mostPopularSection);
        return driver.findElements(mostPopularPostsCount).size();
    }

    public void clickSkipToMostReadLink() {
        waitForClickability(skipToMostReadLink).sendKeys(Keys.ENTER);
    }

    // ------------------- Opinion Section Methods -------------------

    public boolean isOpinionSectionVisible() {
        scrollToElement(opinionSectionTitle);
        return isDisplayed(opinionSectionTitle);
    }

    public void ensureOpinionSectionVisible() {
        scrollDown(3000);
        waitForVisibility(opinionPosts);
    }

    public int getOpinionPostCount() {
        ensureOpinionSectionVisible();
        return driver.findElements(opinionPosts).size();
    }

    public List<WebElement> getOpinionPostElements() {
        ensureOpinionSectionVisible();
        return driver.findElements(opinionPosts);
    }

    public String getCreatorName(WebElement postElement) {
        WebElement creatorElement = wait.until(driver -> postElement.findElement(opinionPostCreator));
        return creatorElement.getText().trim();
    }

    /**
     * Selects a random creator name and stores the index for later click
     */
    public String getRandomCreatorName() {
        List<WebElement> posts = getOpinionPostElements();
        int randomIndex = new Random().nextInt(posts.size());
        this.lastRandomIndex = randomIndex;
        return getCreatorName(posts.get(randomIndex));
    }

    /**
     * Clicks on the same random post selected in getRandomCreatorName()
     */
    public void clickRandomPost() {
        if (lastRandomIndex < 0) {
            throw new IllegalStateException("Random index not set. Call getRandomCreatorName() first.");
        }
        clickPost(lastRandomIndex);
    }

    /**
     * Clicks a post by its index
     */
    public void clickPost(int index) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                List<WebElement> posts = getOpinionPostElements();
                if (index < 0 || index >= posts.size()) {
                    throw new IndexOutOfBoundsException("Invalid index for Opinion post: " + index);
                }
                WebElement link = posts.get(index).findElement(By.tagName("a"));
                safeClick(link);
                return;
            } catch (StaleElementReferenceException e) {
                logger.warn("Stale element detected during click. Retrying...");
            }
        }
        throw new RuntimeException("Failed to click opinion post after retries.");
    }

    // ------------------- About Section -------------------

    /**
     * Enum representing footer 'About' section links.
     */
    public enum AboutLinks {
        ABOUT_US("//a[@href='https://www.aljazeera.com/about-us']"),
        CODE_OF_ETHICS("//a[@href='/code-of-ethics/']"),
        TERMS_AND_CONDITIONS("//a[@href='/terms-and-conditions/']"),
        EU_EEA_REGULATORY_NOTICE("//a[@href='/eu-eea-regulatory/']"),
        PRIVACY_POLICY("//a[@href='https://privacy.aljazeera.net/']"),
        COOKIE_POLICY("//a[@href='https://privacy.aljazeera.net/cookie/']"),
        COOKIE_PREFERENCES("//a[@href='#cookiesPreferences']"),
        SITEMAP("//a[@href='https://www.aljazeera.com/sitemap']"),
        WORK_FOR_US("//a[@href='https://careers.aljazeera.net/']");

        private final String xpath;

        AboutLinks(String xpath) {
            this.xpath = xpath;
        }

        public By getLocator() {
            return By.xpath(xpath);
        }
    }

    /**
     * Returns the header WebElement of the 'About' section.
     */
    public WebElement getAboutSectionHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(aboutSectionHeader));
    }

    /**
     * Returns a specific link WebElement from the 'About' section.
     */
    public WebElement getAboutLink(AboutLinks link) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(link.getLocator()));
    }

    /**
     * Attempts to close the Cookie Preferences modal if it appears within a short wait.
     *
     * @return true if the modal was found and closed, false if modal did not appear.
     */
    public boolean closeCookiePreferencesModalIfPresent() {
        By modalLocator = By.cssSelector("[aria-label='Privacy Preference Center']");
        By saveButtonLocator = By.cssSelector("button[class='save-preference-btn-handler onetrust-close-btn-handler']");

        try {
            // Short explicit wait for presence (e.g., up to 5 seconds)
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(modalLocator));

            waitForVisibility(modalLocator);

            WebElement saveButton = waitForClickability(saveButtonLocator);
            safeClick(saveButton);

            isInvisibleOrAbsent(modalLocator);
            return true;

        } catch (TimeoutException e) {
            // Modal never appeared within 5 seconds
            return false;
        }
    }


//     ------------------- OurChannels Section Methods -------------------

    /**
     * Enum for locating 'Our Channels' footer links on Al Jazeera home page.
     */
    public enum OurChannels {
        AL_JAZEERA_ARABIC("//a[@href='http://aljazeera.net/']"),
        AL_JAZEERA_ENGLISH("//a[@href='https://www.aljazeera.com/']"),
        INVESTIGATIVE_UNIT("//a[@href='https://www.ajiunit.com/']"),
        MUBASHER("//a[@href='https://www.aljazeeramubasher.net/']"),
        DOCUMENTARY("//a[@href='http://doc.aljazeera.net/']"),
        BALKANS("//a[@href='http://balkans.aljazeera.net/']"),
        AJ_PLUS("//a[@href='http://ajplus.net/']");

        private final String xpath;

        OurChannels(String xpath) {
            this.xpath = xpath;
        }

        /**
         * Returns By locator for the link.
         */
        public By getLocator() {
            return By.xpath(xpath);
        }
    }

    /**
     * Returns the WebElement representing the 'Our Channels' section header in the footer.
     *
     * @return WebElement of the 'Our Channels' header
     */
    public WebElement getOurChannelsHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ourChannelsHeader));
    }

    /**
     * Returns the WebElement for a specific 'Our Channels' link.
     *
     * @param channel Enum representing the link.
     * @return WebElement of the link.
     */
    public WebElement getOurChannelLink(OurChannels channel) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(channel.getLocator()));
    }

}
