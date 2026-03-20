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
        if (args.length < 2) {
            System.err.println("Invalid input. Please provide a token followed by at least one scope to validate.");
            System.exit(1);
        }
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
