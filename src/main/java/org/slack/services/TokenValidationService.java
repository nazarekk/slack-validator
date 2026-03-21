package org.slack.services;

import org.slack.ValidationConstants;

import java.util.List;
import java.util.Map;

public interface TokenValidationService {

    ValidationResult validateToken(String token, List<String> scopes);

    class ValidationResult {
        public Map<String, Boolean> scopeResults;
        public boolean isSuccess;

        public ValidationResult(Map<String, Boolean> scopeResults, boolean isSuccess) {
            this.scopeResults = scopeResults;
            this.isSuccess = isSuccess;
        }

        public String toString() {
            StringBuilder scopesResult = new StringBuilder();
            String overallResult = isSuccess ?
                    ValidationConstants.SUCCESS_MESSAGE.getValue() :
                    ValidationConstants.FAILED_MESSAGE.getValue();
            for (Map.Entry<String, Boolean> entry : scopeResults.entrySet()) {
                scopesResult.append(entry.getKey()).append(" - ").append(entry.getValue() ?
                        ValidationConstants.HAVE_ACCESS_MESSAGE.getValue() :
                        ValidationConstants.NO_ACCESS_MESSAGE.getValue()).append("\n");
            }
            return scopesResult + overallResult;
        }
    }
}
