package org.slack.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TokenValidator {
    abstract public Boolean validate(Object authResponse, String scope);

    public Map<String, Boolean> bulkValidate(Object authResponse, List<String> scopes) {
        Map<String, Boolean> validatedScopes = new HashMap<>();
        for (String scope : scopes) {
            Boolean validationResult = this.validate(authResponse, scope);
            validatedScopes.put(scope,validationResult);
        }
        return validatedScopes;
    }
}
