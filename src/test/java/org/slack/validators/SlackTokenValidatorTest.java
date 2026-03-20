package org.slack.validators;

import com.slack.api.methods.response.auth.AuthTestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;


public class SlackTokenValidatorTest
{

    private final SlackTokenValidator validator = new SlackTokenValidator();
    private static final AuthTestResponse authResponse = new AuthTestResponse();

    @BeforeAll
    static void setup() {
        authResponse.setHttpResponseHeaders(Map.of("x-oauth-scopes", Arrays.asList("channels:history", "groups:history", "channels:read")));
    }

    @Test
    @DisplayName("Should return true for valid token")
    void whenValidTokenAndScopeIsProvided_thenShouldReturnTrue() {
        Assertions.assertTrue(validator.validate(authResponse, "channels:read"));
    }

    @Test
    @DisplayName("Should return true for valid token with no required scope")
    void whenValidTokenAndInvalidScopeIsProvided_thenShouldReturnFalse() {
        Assertions.assertFalse(validator.validate(authResponse, "groups:read"));
    }

    @Test
    @DisplayName("Should return all true for valid token")
    void whenValidTokenAndScopesAreProvided_thenShouldReturnAllTrue() {
        Map<String, Boolean> validationResult = validator.bulkValidate(authResponse, Arrays.asList("channels:read", "channels:history", "channels:read"));
        Assertions.assertTrue(validationResult.get("channels:read"));
        Assertions.assertTrue(validationResult.get("channels:history"));
        Assertions.assertTrue(validationResult.get("channels:read"));
    }

    @Test
    @DisplayName("Should not return all true for valid token with no required scopes")
    void whenValidTokenAndInvalidScopesAreProvided_thenShouldReturnFalse() {
        Map<String, Boolean> validationResult = validator.bulkValidate(authResponse, Arrays.asList("channels:read", "channels:history", "groups:read"));
        Assertions.assertTrue(validationResult.get("channels:read"));
        Assertions.assertTrue(validationResult.get("channels:history"));
        Assertions.assertFalse(validationResult.get("groups:read"));
    }

    @Test
    @DisplayName("Should return empty map for valid token with no scopes")
    void whenValidTokenAndEmptyScopesAreProvided_thenShouldReturnEmptyMap() {
        Map<String, Boolean> validationResult = validator.bulkValidate(authResponse, Collections.EMPTY_LIST);
        Assertions.assertEquals(0, validationResult.size());
    }
}
