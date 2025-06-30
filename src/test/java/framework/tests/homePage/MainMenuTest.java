package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

/**
 * This test verifies that all main and sub menu items on the homepage are visible and have correct labels.
 */
@Epic("Navigation")
@Feature("Main Menu")
@Owner("Santri")
@Tag("UI")
public class MainMenuTest extends BaseTest {

    private HomePage homePage;

    /**
     * Initializes WebDriver and opens the home page before each test.
     */
    @BeforeMethod
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * Verifies text labels of main menu and submenu items.
     */
    @Test(description = "Verify visibility and correctness of all main and submenu items on the homepage.")
    @Story("Verify main and sub menus are displayed correctly")
    @Description("Asserts visibility and correctness of main and submenu items on the homepage")
    @Severity(SeverityLevel.NORMAL)
    public void verifyMainAndSubMenus() {
        SoftAssert softAssert = new SoftAssert();

        // Main Menu Items
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.NEWS), "News", "Main menu: News");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.MIDDLE_EAST), "Middle East", "Main menu: Middle East");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.EXPLAINED), "Explained", "Main menu: Explained");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.OPINION), "Opinion", "Main menu: Opinion");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.SPORT), "Sport", "Main menu: Sport");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.VIDEO), "Video", "Main menu: Video");
        softAssert.assertEquals(homePage.getMainMenuText(HomePage.MainMenu.MORE), "More", "Main menu: More");

        // News Submenu
        homePage.clickMainMenu(HomePage.MainMenu.NEWS);
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.AFRICA), "Africa", "News submenu: Africa");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.ASIA), "Asia", "News submenu: Asia");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.US_CANADA), "US & Canada", "News submenu: US & Canada");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.LATIN_AMERICA), "Latin America", "News submenu: Latin America");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.EUROPE), "Europe", "News submenu: Europe");
        softAssert.assertEquals(homePage.getNewsSubMenuText(HomePage.NewsSubMenu.ASIA_PACIFIC), "Asia Pacific", "News submenu: Asia Pacific");

        // More Submenu
        homePage.clickMainMenu(HomePage.MainMenu.MORE);
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.FEATURES), "Features", "More submenu: Features");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.ECONOMY), "Economy", "More submenu: Economy");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.HUMAN_RIGHTS), "Human Rights", "More submenu: Human Rights");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.CLIMATE_CRISIS), "Climate Crisis", "More submenu: Climate Crisis");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.INVESTIGATIONS), "Investigations", "More submenu: Investigations");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.INTERACTIVES), "Interactives", "More submenu: Interactives");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.IN_PICTURES), "In Pictures", "More submenu: In Pictures");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.SCIENCE_TECHNOLOGY), "Science & Technology", "More submenu: Science & Technology");
        softAssert.assertEquals(homePage.getMoreSubMenuText(HomePage.MoreSubMenu.PODCASTS), "Podcasts", "More submenu: Podcasts");

        softAssert.assertAll();
    }
}
