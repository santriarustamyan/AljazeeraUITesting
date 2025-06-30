package framework.mailslurp;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.Configuration;
import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import framework.config.Config;

/**
 * Service class to interact with MailSlurp API for creating inboxes and fetching emails.
 */
public class MailSlurpService {

    private final ApiClient client;
    private final InboxControllerApi inboxApi;
    private final WaitForControllerApi waitForApi;

    /**
     * Initializes the MailSlurp client and APIs with configuration.
     */
    public MailSlurpService() {
        this.client = Configuration.getDefaultApiClient();
        this.client.setApiKey(Config.getMailSlurpApiKey());
        this.client.setConnectTimeout(30_000);
        this.client.setReadTimeout(30_000);

        this.inboxApi = new InboxControllerApi(client);
        this.waitForApi = new WaitForControllerApi(client);
    }

    /**
     * Creates a new inbox with default settings.
     *
     * @return InboxDto representing the created inbox.
     * @throws Exception if the API call fails.
     */
    public InboxDto createInbox() throws Exception {
        return inboxApi.createInboxWithDefaults().execute();
    }

    /**
     * Waits for the latest unread email to arrive in the specified inbox.
     *
     * @param inbox the inbox to monitor.
     * @return Email object containing the received email.
     * @throws Exception if the API call times out or fails.
     */
    public Email waitForLatestEmail(InboxDto inbox) throws Exception {
        return waitForApi
                .waitForLatestEmail()
                .inboxId(inbox.getId())
                .timeout(60_000L)
                .unreadOnly(true)
                .execute();
    }
}
