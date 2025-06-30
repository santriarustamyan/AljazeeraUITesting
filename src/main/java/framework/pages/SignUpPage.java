package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Sign-Up page.
 */
public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    // Locators for sign-up form fields and button
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By submitButton = By.xpath("//button[contains(@class, 'aj-form__submit-button') and contains(@class, 'active')]");

    /**
     * Fills the sign-up form with the given email and password.
     *
     * @param email    The email address to input
     * @param password The password to input
     */
    public void fillSignUpForm(String email, String password) {
        typeText(emailField, email);
        typeText(passwordField, password);
    }

    /**
     * Clicks the submit button to submit the form.
     */
    public void submitForm() {
        safeClick(submitButton);
    }
}
