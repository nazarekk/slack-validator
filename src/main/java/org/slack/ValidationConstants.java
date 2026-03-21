package org.slack;

public enum ValidationConstants {
    SUCCESS_MESSAGE("SUCCESS "),
    FAILED_MESSAGE("FAILED "),
    HAVE_ACCESS_MESSAGE("HAVE ACCESS"),
    NO_ACCESS_MESSAGE("NO ACCESS");

    private final String value;

    ValidationConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
