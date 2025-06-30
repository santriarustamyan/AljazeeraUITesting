package framework.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Abstract base class for all Page Objects.
 * Provides common Selenium utility methods with built-in wait support.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * Waits for the document ready state to be 'complete'.
     */
    public void waitForPageLoaded() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Scrolls the page down by a specified number of pixels.
     *
     * @param pixels number of pixels to scroll down
     */
    public void scrollDown(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, arguments[0]);", pixels);
    }

    /**
     * Scrolls the element into the center of the viewport.
     */
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    /**
     * Scrolls to a specific element located by a locator.
     */
    protected void scrollToElement(By locator) {
        WebElement element = waitForVisibility(locator);
        scrollIntoView(element);
    }

    /**
     * Clicks an element using safe wait, scrolls to it and handles interception fallback.
     */
    protected void safeClick(By locator) {
        WebElement element = waitForClickability(locator);
        scrollIntoView(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            logger.warn("Element click intercepted, fallback to JS click: {}", locator);
            clickWithJS(element);
        }
    }

    /**
     * Clicks a WebElement safely with wait and JS fallback.
     */
    protected void safeClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        scrollIntoView(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            logger.warn("Element click intercepted, fallback to JS click");
            clickWithJS(element);
        }
    }

    /**
     * Waits for an element to be visible and returns it.
     */
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Checks whether an element exists in the DOM without waiting for visibility.
     *
     * @param locator the By locator of the element
     * @return true if the element is present in the DOM, false otherwise
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks whether an element is invisible or completely absent from the DOM.
     *
     * @param locator the By locator of the element
     * @return true if the element is either invisible or not present
     */
    public boolean isInvisibleOrAbsent(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            // If the element is still visible after the timeout, return false
            return false;
        }
    }

    /**
     * Waits for an element to be clickable and returns it.
     */
    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for an element to be present in the DOM (regardless of visibility).
     */
    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Gets text from an element after ensuring it is visible.
     */
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * Types text into an input field after waiting for it to be clickable.
     */
    protected void typeText(By locator, String text) {
        WebElement element = waitForClickability(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Returns true if the element is visible within timeout, false otherwise.
     */
    public boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Fallback click method using JavaScript.
     */
    protected void clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Switches WebDriver context to the newly opened browser tab.
     */
    public void switchToNewTab() {
        // Get all window handles
        var windowHandles = driver.getWindowHandles();
        // Switch to the last one (newly opened)
        driver.switchTo().window(windowHandles.toArray()[windowHandles.size() - 1].toString());
    }

    /**
     * Switches WebDriver context back to the main (first) browser tab.
     */
    public void switchToMainTab() {
        // Get the first window handle
        var mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
    }

    /**
     * Closes the currently active browser tab.
     */
    public void closeCurrentTab() {
        driver.close();
    }

    /**
     * Waits for an optional overlay or popup to disappear.
     * If the element is not present, does nothing.
     */
    public void waitForOptionalInvisibility(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException | NoSuchElementException ignored) {
            logger.info("Overlay not visible or already disappeared: " + locator);
        }
    }

}
