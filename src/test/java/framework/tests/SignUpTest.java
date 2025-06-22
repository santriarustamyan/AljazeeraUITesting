package framework.tests;

import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import framework.config.Config;
import framework.driver.DriverFactory;
import framework.mailslurp.MailSlurpService;
import framework.pages.HomePage;
import framework.pages.SignUpPage;
import framework.utils.EmailParser;
import framework.utils.TestDataGenerator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SignUpTest {

    private WebDriver driver;
    private MailSlurpService mailSlurpService;
    private InboxDto inbox;

    @BeforeClass
    public void setup() throws Exception {
        driver = DriverFactory.getDriver();
        mailSlurpService = new MailSlurpService();
        inbox = mailSlurpService.createInbox();
    }

    @Test
    public void testSignUpFlow() throws Exception {
        driver.get(Config.get("base.url"));

        HomePage homePage = new HomePage(driver);
        homePage.clickMainMenu(HomePage.MainMenu.SIGNUP);

        SignUpPage signUpPage = new SignUpPage(driver);
        String password = TestDataGenerator.generatePassword();
        String email = inbox.getEmailAddress();


        signUpPage.fillSignUpForm(email, password);
        signUpPage.submitForm();

        Email emailReceived = mailSlurpService.waitForLatestEmail(inbox);
        Assert.assertNotNull(emailReceived);
        Assert.assertTrue(emailReceived.getSubject().contains("Verify"));

        String verificationLink = EmailParser.extractVerificationLink(emailReceived);
        driver.navigate().to(verificationLink);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains("/onboarding/newsletters"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/onboarding/newsletters"), "Verification failed.");
    }

@AfterClass
    public void teardown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
