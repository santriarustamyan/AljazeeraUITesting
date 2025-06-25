package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LivePage extends BasePage {

    public LivePage(WebDriver driver) {
        super(driver);
    }

    private final By switchPlayerButton = By.id("liveStreamPlayerHelpButton");

    private final By livestreamIframe = By.cssSelector("[playsinline='playsinline']"); // проверь iframe сам

    public boolean isLivestreamPlayerVisible() {
        try {
            WebElement player = waitForVisibility(livestreamIframe);
            return player.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSwitchPlayerPresent() {
        return isDisplayed(switchPlayerButton);
    }
}
