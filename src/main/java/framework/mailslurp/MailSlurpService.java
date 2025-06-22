package framework.mailslurp;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.Configuration;
import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import framework.config.Config;

public class MailSlurpService {

    private final ApiClient client;
    private final InboxControllerApi inboxApi;
    private final WaitForControllerApi waitForApi;

    public MailSlurpService() {
        client = Configuration.getDefaultApiClient();
        client.setApiKey(Config.getMailSlurpApiKey());
        client.setConnectTimeout(30000);
        client.setReadTimeout(30000);
        inboxApi = new InboxControllerApi(client);
        waitForApi = new WaitForControllerApi(client);
    }

    public InboxDto createInbox() throws Exception {
        return inboxApi.createInboxWithDefaults().execute();
    }

    public Email waitForLatestEmail(InboxDto inbox) throws Exception {
        return waitForApi
                .waitForLatestEmail()
                .inboxId(inbox.getId())
                .timeout(60000L)
                .unreadOnly(true)
                .execute();
    }
}

