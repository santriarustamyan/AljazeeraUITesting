package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This test verifies that all main and sub menu items on the homepage
 * are visible and have correct labels.
 */
@Epic("Navigation")
@Feature("Main Menu")
@Owner("Santri")
@Tag("UI")
public class MainMenuTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
    }

    /**
     * DataProvider for main menu items and their expected labels.
     */
    @DataProvider(name = "mainMenuItems")
    public Object[][] provideMainMenuItems() {
        return new Object[][]{
                {HomePage.MainMenu.NEWS, "News"},
                {HomePage.MainMenu.MIDDLE_EAST, "Middle East"},
                {HomePage.MainMenu.EXPLAINED, "Explained"},
                {HomePage.MainMenu.OPINION, "Opinion"},
                {HomePage.MainMenu.SPORT, "Sport"},
                {HomePage.MainMenu.VIDEO, "Video"},
                {HomePage.MainMenu.MORE, "More"}
        };
    }

    /**
     * Verifies that each main menu item is visible and has the correct label.
     */
    @Test(dataProvider = "mainMenuItems", description = "Verify each main menu item is visible and has correct text.")
    @Story("Main menu items visibility and labels")
    @Severity(SeverityLevel.NORMAL)
    @Description("Checks that every main menu item is visible and displays the expected text.")
    public void verifyMainMenuItem(HomePage.MainMenu menu, String expectedLabel) {
        Allure.step("Verifying main menu item: " + expectedLabel);
        String actualLabel = homePage.getMainMenuText(menu);
        Assert.assertEquals(
                actualLabel,
                expectedLabel,
                "Main menu label mismatch for: " + expectedLabel
        );
    }

    /**
     * DataProvider for News submenu items.
     */
    @DataProvider(name = "newsSubMenuItems")
    public Object[][] provideNewsSubMenuItems() {
        return new Object[][]{
                {HomePage.NewsSubMenu.AFRICA, "Africa"},
                {HomePage.NewsSubMenu.ASIA, "Asia"},
                {HomePage.NewsSubMenu.US_CANADA, "US & Canada"},
                {HomePage.NewsSubMenu.LATIN_AMERICA, "Latin America"},
                {HomePage.NewsSubMenu.EUROPE, "Europe"},
                {HomePage.NewsSubMenu.ASIA_PACIFIC, "Asia Pacific"}
        };
    }

    /**
     * Verifies that each News submenu item is visible and has the correct label.
     */
    @Test(dataProvider = "newsSubMenuItems", description = "Verify each News submenu item label.")
    @Story("News submenu items")
    @Severity(SeverityLevel.NORMAL)
    @Description("Checks that each News submenu item is visible and has the correct text.")
    public void verifyNewsSubMenuItem(HomePage.NewsSubMenu submenu, String expectedLabel) {
        Allure.step("Opening News submenu");
        homePage.clickMainMenu(HomePage.MainMenu.NEWS);
        Allure.step("Verifying News submenu item: " + expectedLabel);
        String actualLabel = homePage.getNewsSubMenuText(submenu);
        Assert.assertEquals(
                actualLabel,
                expectedLabel,
                "News submenu label mismatch for: " + expectedLabel
        );
    }

    /**
     * DataProvider for More submenu items.
     */
    @DataProvider(name = "moreSubMenuItems")
    public Object[][] provideMoreSubMenuItems() {
        return new Object[][]{
                {HomePage.MoreSubMenu.FEATURES, "Features"},
                {HomePage.MoreSubMenu.ECONOMY, "Economy"},
                {HomePage.MoreSubMenu.HUMAN_RIGHTS, "Human Rights"},
                {HomePage.MoreSubMenu.CLIMATE_CRISIS, "Climate Crisis"},
                {HomePage.MoreSubMenu.INVESTIGATIONS, "Investigations"},
                {HomePage.MoreSubMenu.INTERACTIVES, "Interactives"},
                {HomePage.MoreSubMenu.IN_PICTURES, "In Pictures"},
                {HomePage.MoreSubMenu.SCIENCE_TECHNOLOGY, "Science & Technology"},
                {HomePage.MoreSubMenu.PODCASTS, "Podcasts"}
        };
    }

    /**
     * Verifies that each More submenu item is visible and has the correct label.
     */
    @Test(dataProvider = "moreSubMenuItems", description = "Verify each More submenu item label.")
    @Story("More submenu items")
    @Severity(SeverityLevel.NORMAL)
    @Description("Checks that each More submenu item is visible and has the expected text.")
    public void verifyMoreSubMenuItem(HomePage.MoreSubMenu submenu, String expectedLabel) {
        Allure.step("Opening More submenu");
        homePage.clickMainMenu(HomePage.MainMenu.MORE);
        Allure.step("Verifying More submenu item: " + expectedLabel);
        String actualLabel = homePage.getMoreSubMenuText(submenu);
        Assert.assertEquals(
                actualLabel,
                expectedLabel,
                "More submenu label mismatch for: " + expectedLabel
        );
    }
}
