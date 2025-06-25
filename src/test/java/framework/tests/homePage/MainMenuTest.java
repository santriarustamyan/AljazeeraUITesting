package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class MainMenuTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    @Test
    public void verifyMainAndSubMenus() {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.NEWS), "News");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.MIDDLE_EAST), "Middle East");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.EXPLAINED), "Explained");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.OPINION), "Opinion");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.SPORT), "Sport");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.VIDEO), "Video");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.MORE), "More");

        homePage.clickMainMenu(HomePage.MainMenu.NEWS);
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.AFRICA), "Africa");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.ASIA), "Asia");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.US_CANADA), "US & Canada");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.LATIN_AMERICA), "Latin America");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.EUROPE), "Europe");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.ASIA_PACIFIC), "Asia Pacific");

        homePage.clickMainMenu(HomePage.MainMenu.MORE);
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.FEATURES), "Features");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.ECONOMY), "Economy");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.HUMAN_RIGHTS), "Human Rights");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.CLIMATE_CRISIS), "Climate Crisis");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.INVESTIGATIONS), "Investigations");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.INTERACTIVES), "Interactives");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.IN_PICTURES), "In Pictures");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.SCIENCE_TECHNOLOGY), "Science & Technology");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.PODCASTS), "Podcasts");

        softAssert.assertAll();
    }
}
