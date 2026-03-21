package org.slack.clients;

public interface AuthClient<T> {

    T authTest(String token);
}
