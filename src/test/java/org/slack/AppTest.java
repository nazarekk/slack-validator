package org.slack;

import com.slack.api.methods.response.auth.AuthTestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slack.validators.SlackTokenValidator;
import org.slack.validators.TokenValidator;

import java.util.Arrays;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @org.junit.jupiter.api.Test
    @DisplayName("Should return true for valid token")
    void whenValidTokenAndScopeIsProvided_thenShouldReturnTrue() {
        AuthTestResponse authResponse = new AuthTestResponse();

        authResponse.setHttpResponseHeaders(Map.of("x-oauth-scopes", Arrays.asList("channels:history", "groups:history", "channels:read")));
        TokenValidator validator = new SlackTokenValidator();
        Assertions.assertTrue(validator.validate(authResponse, "channels:read"));
    }

    @Test
    @DisplayName("Should return true for valid token")
    void whenValidTokenAndInvalidScopeIsProvided_thenShouldReturnFalse() {
        AuthTestResponse authResponse = new AuthTestResponse();

        authResponse.setHttpResponseHeaders(Map.of("x-oauth-scopes", Arrays.asList("channels:history", "groups:history")));
        TokenValidator validator = new SlackTokenValidator();
        Assertions.assertFalse(validator.validate(authResponse, "channels:read"));
    }
}
