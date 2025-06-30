package framework.tests.signup;

import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import framework.mailslurp.MailSlurpService;
import framework.pages.HomePage;
import framework.pages.SignUpPage;
import framework.tests.basePage.BaseTest;
import framework.utils.EmailParser;
import framework.utils.TestDataGenerator;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Test suite for verifying the user sign-up flow, including email verification.
 */
@Epic("User Registration")
@Feature("Sign Up Flow")
@Owner("Santri")
@Tag("Signup")
public class SignUpTest extends BaseTest {

    private HomePage homePage;
    private SignUpPage signUpPage;
    private MailSlurpService mailSlurpService;
    private InboxDto inbox;

    /**
     * Setup before each test: initialize driver and page objects.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        startDriver("desktop");
        homePage = new HomePage(driver);
        signUpPage = new SignUpPage(driver);
        mailSlurpService = new MailSlurpService();
    }

    /**
     * Verifies the full sign-up flow including form submission and email verification.
     */
    @Test(description = "Verify Sign Up flow with email verification")
    @Story("Email verification")
    @Description("Test the full sign-up flow including form submission and email verification")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySignUpFlow() throws Exception {

        Allure.step("Create new MailSlurp inbox");
        inbox = mailSlurpService.createInbox();
        String email = inbox.getEmailAddress();
        String password = TestDataGenerator.generatePassword();

        Allure.step("Navigate to Sign Up page");
        homePage.clickMainMenu(HomePage.MainMenu.SIGNUP);

        Allure.step("Fill Sign Up form with email: " + email);
        signUpPage.fillSignUpForm(email, password);

        Allure.step("Submit Sign Up form");
        signUpPage.submitForm();

        Allure.step("Wait for verification email");
        Email emailReceived = mailSlurpService.waitForLatestEmail(inbox);

        Allure.step("Assert that verification email was received");
        Assert.assertNotNull(emailReceived, "No verification email received");
        Assert.assertTrue(
                emailReceived.getSubject().toLowerCase().contains("verify"),
                "Verification email subject is incorrect: " + emailReceived.getSubject()
        );

        Allure.step("Extract verification link from email");
        String verificationLink = EmailParser.extractVerificationLink(emailReceived);

        Allure.step("Navigate to verification link");
        driver.navigate().to(verificationLink);

        Allure.step("Wait for onboarding page URL");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains("/onboarding/newsletters"));

        Allure.step("Assert that verification succeeded");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/onboarding/newsletters"),
                "Verification failed: unexpected URL " + driver.getCurrentUrl()
        );
    }
}
