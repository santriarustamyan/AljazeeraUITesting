package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By submitButton = By.xpath("//button[@class=\"aj-form__submit-button active\"]");
    public void fillSignUpForm(String email, String password) {
        typeText(emailField, email);
        typeText(passwordField, password);
    }

    public void submitForm() {
        safeClick(submitButton);
    }
}
