package org.slack.services;

import java.util.List;
import java.util.Map;

public interface TokenValidationService {

    ValidationResult validateToken(String token, List<String> scopes);

    record ValidationResult(Map<String, Boolean> scopeResults, boolean isSuccess) {}
}
