package org.slack.validators;

import com.slack.api.methods.response.auth.AuthTestResponse;

import java.util.List;
import java.util.Objects;

public class SlackTokenValidator extends TokenValidator {

    public Boolean validate(Object authResponse, String scope) {
        AuthTestResponse transformedAuthResponse = (AuthTestResponse) authResponse;
        List<String> scopes = transformedAuthResponse.getHttpResponseHeaders().get("x-oauth-scopes");
        return Objects.nonNull(scopes) && transformedAuthResponse.getHttpResponseHeaders().get("x-oauth-scopes").stream().anyMatch(scope::equals);
    }
}
