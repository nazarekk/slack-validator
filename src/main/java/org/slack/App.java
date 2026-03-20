package org.slack;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import org.slack.validators.SlackTokenValidator;
import org.slack.validators.TokenValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, SlackApiException {
        String token = args[0];
        List<String> scopes = List.of(args).subList(1, args.length - 1);
        Map<String, Boolean> validatedScopes;
        SlackApiClient slackApiClient = new SlackApiClient();
        TokenValidator tokenValidator = new SlackTokenValidator();
        AuthTestResponse response = slackApiClient.authTest(token);
        validatedScopes = tokenValidator.bulkValidate(response, scopes);
        System.out.println("Validation results: ");
        validatedScopes.forEach((scope, hasAccess) -> System.out.println(scope + " - " + (hasAccess ?
                ValidationConstants.HAVE_ACCESS_MESSAGE :
                ValidationConstants.NO_ACCESS_MESSAGE
        )));
        System.out.println(validatedScopes.containsValue(false) ?
                ValidationConstants.FAILED_MESSAGE :
                ValidationConstants.SUCCESS_MESSAGE);
    }
}
