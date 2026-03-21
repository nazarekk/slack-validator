package org.slack.services;

import com.slack.api.methods.response.auth.AuthTestResponse;
import org.slack.clients.AuthClient;
import org.slack.clients.SlackAuthClient;
import org.slack.validators.SlackTokenValidator;
import org.slack.validators.TokenValidator;

import java.util.List;
import java.util.Map;

public class SlackTokenValidationService implements TokenValidationService {
    private final AuthClient<AuthTestResponse> authClient;
    private final TokenValidator tokenValidator;

    public SlackTokenValidationService() {
        this.authClient = new SlackAuthClient();
        this.tokenValidator = new SlackTokenValidator();
    }

    public SlackTokenValidationService(AuthClient<AuthTestResponse> authClient, TokenValidator tokenValidator) {
        this.authClient = authClient;
        this.tokenValidator = tokenValidator;
    }

    @Override
    public ValidationResult validateToken(String token, List<String> scopes) {
        AuthTestResponse response = authClient.authTest(token);
        Map<String, Boolean> validatedScopes = tokenValidator.bulkValidate(response, scopes);

        return new ValidationResult(
                validatedScopes,
                !validatedScopes.containsValue(false)
        );
    }
}
