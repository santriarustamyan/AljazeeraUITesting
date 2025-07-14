//package framework.tests.signup;
//
//import com.mailslurp.models.Email;
//import com.mailslurp.models.InboxDto;
//import framework.mailslurp.MailSlurpService;
//import framework.pages.HomePage;
//import framework.pages.SignUpPage;
//import framework.tests.basePage.BaseTest;
//import framework.utils.EmailParser;
//import framework.utils.TestDataGenerator;
//import io.qameta.allure.*;
//import io.qameta.allure.testng.Tag;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.*;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Test suite for verifying the user sign-up flow, including email verification.
// */
//@Epic("User Registration")
//@Feature("Sign Up Flow")
//@Owner("Santri")
//@Tag("Signup")
//public class SignUpTest extends BaseTest {
//
//    private HomePage homePage;
//    private SignUpPage signUpPage;
//    private static MailSlurpService mailSlurpService;
//    private final List<InboxDto> createdInboxes = new ArrayList<>();
//
//    /**
//     * Initialize MailSlurp service once per class.
//     */
//    @BeforeClass(alwaysRun = true)
//    public void beforeClass() {
//        mailSlurpService = new MailSlurpService();
//    }
//
//    /**
//     * Initialize WebDriver and page objects before each test.
//     */
//    @BeforeMethod(alwaysRun = true)
//    public void setUp() {
//        startDriver("desktop");
//        homePage = new HomePage(driver);
//        signUpPage = new SignUpPage(driver);
//    }
//
//    /**
//     * Data provider generating dynamic inboxes and passwords.
//     */
//    @DataProvider(name = "signupCredentials")
//    public Object[][] signupCredentialsProvider() throws Exception {
//        InboxDto inbox1 = createInbox();
//        InboxDto inbox2 = createInbox();
//
//        return new Object[][]{
//                {inbox1, inbox1.getEmailAddress(), TestDataGenerator.generatePassword()},
//                {inbox2, inbox2.getEmailAddress(), TestDataGenerator.generatePassword()}
//        };
//    }
//
//    /**
//     * Creates and stores a new inbox for cleanup.
//     */
//    @Step("Create new MailSlurp inbox")
//    private InboxDto createInbox() throws Exception {
//        InboxDto inbox = mailSlurpService.createInbox();
//        createdInboxes.add(inbox);
//        return inbox;
//    }
//
//    /**
//     * Cleanup created inboxes after each test to avoid clutter.
//     */
//    @AfterMethod(alwaysRun = true)
//    public void cleanUpInboxes() {
//        for (InboxDto inbox : createdInboxes) {
//            mailSlurpService.deleteInbox(inbox.getId());
//        }
//        createdInboxes.clear();
//    }
//
//    /**
//     * Verifies the full sign-up flow including form submission and email verification.
//     *
//     * @param inbox        MailSlurp inbox instance
//     * @param emailAddress Generated email address
//     * @param password     Generated password
//     */
//    @Test(
//            description = "Verify Sign Up flow with email verification",
//            dataProvider = "signupCredentials"
//    )
//    @Story("Email verification")
//    @Description("Test the full sign-up flow including form submission and email verification")
//    @Severity(SeverityLevel.CRITICAL)
//    public void verifySignUpFlow(InboxDto inbox, String emailAddress, String password) throws Exception {
//
//        openSignUpPage();
//
//        fillAndSubmitSignUpForm(emailAddress, password);
//
//        Email verificationEmail = waitForVerificationEmail(inbox);
//
//        String verificationLink = extractVerificationLink(verificationEmail);
//
//        verifyEmailLinkNavigatesToOnboarding(verificationLink);
//    }
//
//    @Step("Navigate to Sign Up page")
//    private void openSignUpPage() {
//        homePage.clickMainMenu(HomePage.MainMenu.SIGNUP);
//    }
//
//    @Step("Fill Sign Up form and submit it")
//    private void fillAndSubmitSignUpForm(String email, String password) {
//        signUpPage.fillSignUpForm(email, password);
//        signUpPage.submitForm();
//    }
//
//    @Step("Wait for verification email to arrive")
//    private Email waitForVerificationEmail(InboxDto inbox) throws Exception {
//        Email email = mailSlurpService.waitForLatestEmail(inbox);
//        Assert.assertNotNull(email, "Verification email was not received");
//        Assert.assertTrue(
//                email.getSubject().toLowerCase().contains("verify"),
//                "Unexpected email subject: " + email.getSubject()
//        );
//        return email;
//    }
//
//    @Step("Extract verification link from email")
//    private String extractVerificationLink(Email email) {
//        return EmailParser.extractVerificationLink(email);
//    }
//
//    @Step("Navigate to verification link and assert onboarding page is displayed")
//    private void verifyEmailLinkNavigatesToOnboarding(String verificationLink) {
//        driver.navigate().to(verificationLink);
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        wait.until(ExpectedConditions.urlContains("/onboarding/newsletters"));
//
//        Assert.assertTrue(
//                driver.getCurrentUrl().contains("/onboarding/newsletters"),
//                "Verification failed: unexpected URL " + driver.getCurrentUrl()
//        );
//    }
//}
