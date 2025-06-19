package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LivePage extends BasePage {

    public LivePage(WebDriver driver) {
        super(driver);
    }

    private final By liveHeader = By.cssSelector("div.live-stream-player__widget-header > h1");
    private final By switchPlayerButton = By.id("liveStreamPlayerHelpButton");

    public boolean isLiveHeaderDisplayed() {
        return isDisplayed(liveHeader);
    }

    public boolean isSwitchPlayerPresent() {
        return isDisplayed(switchPlayerButton);
    }
}

