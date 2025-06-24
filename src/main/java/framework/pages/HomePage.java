package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

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

}
