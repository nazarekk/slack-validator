package org.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;

import java.io.IOException;

public class SlackApiClient {
    private final Slack slack;

    public SlackApiClient() {
        this.slack = Slack.getInstance();
    }

    public AuthTestResponse authTest(String token) throws IOException, SlackApiException {
        return slack.methods(token).authTest(r -> r);
    }
}
