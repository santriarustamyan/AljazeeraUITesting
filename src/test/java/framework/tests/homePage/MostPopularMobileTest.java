package framework.tests.homePage;

import framework.pages.HomePage;
import framework.tests.basePage.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Most Popular Section")
@Feature("Mobile Display")
@Owner("Santri")
@Tag("Mobile")
public class MostPopularMobileTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        startDriver("mobile");
        homePage = new HomePage(driver);
    }

    @Test(description = "Verify 'Most Popular' section is NOT visible on mobile")
    @Story("Not displayed on mobile")
    @Description("Verify that 'Most Popular' section is not visible on mobile view")
    @Severity(SeverityLevel.NORMAL)
    public void verifyMostPopularSectionNotVisibleOnMobile() {
        Assert.assertFalse(homePage.isMostPopularVisible(), "'Most Popular' section should not be visible on mobile");
    }
}
