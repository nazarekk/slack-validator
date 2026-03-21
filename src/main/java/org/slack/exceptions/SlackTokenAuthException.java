package org.slack.exceptions;

public class SlackTokenAuthException extends RuntimeException {
    public SlackTokenAuthException(String message) {
        super("Failed to get auth response: " + message);
    }
}
