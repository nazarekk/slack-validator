package org.slack.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TokenValidator {
    abstract public Boolean validate(Object authResponse, String scope);

    public Map<String, Boolean> bulkValidate(Object authResponse, List<String> scopes) {
        Map<String, Boolean> validatedScopes = new HashMap<>(scopes.size());
        for (String scope : scopes) {
            Boolean validationResult = validate(authResponse, scope);
            validatedScopes.put(scope,validationResult);
        }
        return validatedScopes;
    }
}
