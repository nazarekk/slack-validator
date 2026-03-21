package org.slack;

import org.slack.services.SlackTokenValidationService;
import org.slack.services.TokenValidationService;
import org.slack.services.TokenValidationService.ValidationResult;

import java.util.List;

public class App {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Invalid input. Please provide a token followed by at least one scope to validate.");
            System.exit(1);
        }
        String token = args[0];
        List<String> scopes = List.of(args).subList(1, args.length);
        TokenValidationService tokenValidationService = new SlackTokenValidationService();
        ValidationResult validationResult = tokenValidationService.validateToken(token, scopes);
        System.out.println("Validation results: ");
        validationResult.scopeResults().forEach((scope, hasAccess) ->
                System.out.println(scope + " - " + (hasAccess ?
                        ValidationConstants.HAVE_ACCESS_MESSAGE.getValue() :
                        ValidationConstants.NO_ACCESS_MESSAGE.getValue()
                )));
        System.out.println(validationResult.isSuccess() ?
                ValidationConstants.SUCCESS_MESSAGE.getValue() :
                ValidationConstants.FAILED_MESSAGE.getValue()
        );
    }
}
