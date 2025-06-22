package framework.utils;

import com.mailslurp.models.Email;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class EmailParser {

    public static String extractVerificationLink(Email email) {
        Document doc = Jsoup.parse(email.getBody());
        Element linkElement = doc.selectFirst("td.r17-c.nl2go-default-textstyle a");
        if (linkElement == null) {
            throw new RuntimeException("Verification link not found in email body.");
        }
        return linkElement.attr("href");
    }
}
