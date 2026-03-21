package org.slack.clients;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import org.slack.exceptions.SlackTokenAuthException;

import java.io.IOException;

public class SlackAuthClient implements AuthClient<AuthTestResponse> {
    private final Slack slack;

    public SlackAuthClient() {
        this.slack = Slack.getInstance();
    }

    public AuthTestResponse authTest(String token) {
        try {
            return slack.methods(token).authTest(r -> r);
        } catch (IOException | SlackApiException ie) {
            throw new SlackTokenAuthException(ie.getMessage());
        }
    }

}
