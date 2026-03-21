package org.slack.services;

import com.slack.api.methods.response.auth.AuthTestResponse;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slack.clients.AuthClient;
import org.slack.validators.SlackTokenValidator;


import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.when;

public class SlackTokenValidationServiceTest {

    private SlackTokenValidationService tokenValidationService;

    @Mock
    private AuthClient<AuthTestResponse> authClient;

    @BeforeEach
    void setup() {
        AuthTestResponse authResponse = new AuthTestResponse();
        MockitoAnnotations.openMocks(this);
        authResponse.setHttpResponseHeaders(Map.of("x-oauth-scopes", Arrays.asList("channels:history", "groups:history", "channels:read")));
        when(authClient.authTest(Mockito.any())).thenReturn(authResponse);
        tokenValidationService = new SlackTokenValidationService(authClient, new SlackTokenValidator());
    }

    @Test
    @DisplayName("Should return failed and no success for one of scopes")
    void whenInvalidTokenAndScopesAreProvided_thenShouldReturnFailureAndFalseForOneOfScopes() {
        TokenValidationService.ValidationResult validationResult = tokenValidationService.validateToken(
                "xoxb-1-A1B2C3D4-E5F6G7H8-I9J0K1L2-M3N4O5P6-Q7R8S9T0",
                Arrays.asList("channels:read", "channels:history", "groups:read"));
        Assertions.assertFalse(validationResult.isSuccess());
        Assertions.assertTrue(validationResult.scopeResults().get("channels:read"));
        Assertions.assertTrue(validationResult.scopeResults().get("channels:history"));
        Assertions.assertFalse(validationResult.scopeResults().get("groups:read"));
    }

    @Test
    @DisplayName("Should return failed and no success for one of scopes")
    void whenValidTokenAndScopesAreProvided_thenShouldReturnSuccessAndTrueForAllScopes() {
        TokenValidationService.ValidationResult validationResult = tokenValidationService.validateToken(
                "xoxb-1-A1B2C3D4-E5F6G7H8-I9J0K1L2-M3N4O5P6-Q7R8S9T0",
                Arrays.asList("channels:read", "channels:history", "groups:history"));
        Assertions.assertTrue(validationResult.isSuccess());
        Assertions.assertTrue(validationResult.scopeResults().get("channels:read"));
        Assertions.assertTrue(validationResult.scopeResults().get("channels:history"));
        Assertions.assertTrue(validationResult.scopeResults().get("groups:history"));
    }
}
