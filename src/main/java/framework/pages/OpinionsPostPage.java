package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Opinion Post detail page.
 */
public class OpinionsPostPage extends BasePage {

    public OpinionsPostPage(WebDriver driver) {
        super(driver);
    }

    // Locator for the author (creator) name
    private final By creatorName = By.cssSelector("ul.article-author li.article-author__item .author-link");

    // Locator for the post title
    private final By postTitle = By.cssSelector(".article-header h1"); // Adjust the selector if needed

    /**
     * Returns the text of the creator (author) name.
     *
     * @return Creator name text
     */
    public String getCreatorName() {
        return getText(creatorName);
    }

    /**
     * Returns the text of the post title.
     *
     * @return Post title text
     */
    public String getPostTitle() {
        return getText(postTitle);
    }
}
