package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SocialMediaLinksTest extends BaseTest {

    HomePage homePage;

    @BeforeMethod
    public void setUp(){
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    @Test
    public void verifySocialMediaLinksAreVisibleAndClickable() {


        for (HomePage.SocialMediaLinks link : HomePage.SocialMediaLinks.values()) {
            Assert.assertTrue(homePage.isSocialLinkVisible(link),
                    "Social media link is not visible: " + link.name());
            homePage.clickSocialMediaLink(link);
            homePage.switchToNewTab();
            Assert.assertTrue(driver.getCurrentUrl().contains(getExpectedUrlPart(link)),
                    "Redirection failed for: " + link.name());
            homePage.closeCurrentTab();
            homePage.switchToMainTab();
        }
    }

    private String getExpectedUrlPart(HomePage.SocialMediaLinks link) {
        return switch (link) {
            case FACEBOOK -> "facebook.com/aljazeera";
            case X -> "x.com/ajenglish";
            case YOUTUBE -> "youtube.com/aljazeeraenglish";
            case INSTAGRAM -> "instagram.com/aljazeeraenglish";
        };
    }
}