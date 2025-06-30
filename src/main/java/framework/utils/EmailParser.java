package framework.utils;

import com.mailslurp.models.Email;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Utility class for extracting data from emails received via MailSlurp.
 */
public class EmailParser {

    /**
     * Extracts the verification link from the email body HTML.
     *
     * @param email The MailSlurp email object containing the email content.
     * @return The verification URL as a string.
     * @throws RuntimeException if the verification link is not found.
     */
    public static String extractVerificationLink(Email email) {
        Document doc = Jsoup.parse(email.getBody());

        // Attempt to find the link by CSS selector
        Element linkElement = doc.selectFirst("td.r17-c.nl2go-default-textstyle a");
        if (linkElement == null) {
            // Fallback: try a more general selector if specific one fails
            linkElement = doc.selectFirst("a[href]");
        }
        if (linkElement == null) {
            throw new RuntimeException("Verification link not found in email body.");
        }
        return linkElement.attr("href");
    }
}
