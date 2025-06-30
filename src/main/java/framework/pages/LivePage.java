package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object representing the LIVE page and its functionalities.
 */
public class LivePage extends BasePage {

    public LivePage(WebDriver driver) {
        super(driver);
    }

    // Locator for the "Switch Player" button
    private final By switchPlayerButton = By.id("liveStreamPlayerHelpButton");

    // Locator for the livestream iframe (may need validation)
    private final By livestreamIframe = By.cssSelector("[playsinline='playsinline']");

    /**
     * Checks if the livestream video player iframe is visible.
     *
     * @return true if the player iframe is visible, false otherwise
     */
    public boolean isLivestreamPlayerVisible() {
        try {
            WebElement player = waitForVisibility(livestreamIframe);
            return player.isDisplayed();
        } catch (Exception e) {
            logger.warn("Livestream player not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the "Switch Player" button is present and visible.
     *
     * @return true if the button is displayed, false otherwise
     */
    public boolean isSwitchPlayerPresent() {
        return isDisplayed(switchPlayerButton);
    }
}
