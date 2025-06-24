package framework.tests.signup;

import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import framework.mailslurp.MailSlurpService;
import framework.pages.HomePage;
import framework.pages.SignUpPage;
import framework.tests.basePage.BaseTest;
import framework.utils.EmailParser;
import framework.utils.TestDataGenerator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SignUpTest extends BaseTest {


    private HomePage homePage;
    private SignUpPage signUpPage;
    private MailSlurpService mailSlurpService;
    private InboxDto inbox;

    @BeforeMethod
    public void setUp() {
        homePage = new HomePage(driver);
        signUpPage = new SignUpPage(driver);
        mailSlurpService = new MailSlurpService();
    }

    @Test(description = "Verify Sign Up flow with email verification")
    public void verifySignUpFlow() throws Exception {

        // Create new inbox
        inbox = mailSlurpService.createInbox();
        String email = inbox.getEmailAddress();
        String password = TestDataGenerator.generatePassword();

        // Open Sign Up page
        homePage.clickMainMenu(HomePage.MainMenu.SIGNUP);

        // Fill and submit Sign Up form
        signUpPage.fillSignUpForm(email, password);
        signUpPage.submitForm();

        // Wait and verify received email
        Email emailReceived = mailSlurpService.waitForLatestEmail(inbox);
        Assert.assertNotNull(emailReceived, "No verification email received");
        Assert.assertTrue(emailReceived.getSubject().contains("Verify"), "Verification email subject incorrect");

        // Extract verification link and navigate
        String verificationLink = EmailParser.extractVerificationLink(emailReceived);
        driver.navigate().to(verificationLink);

        // Wait for verification confirmation page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains("/onboarding/newsletters"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/onboarding/newsletters"), "Verification failed.");
    }

}
