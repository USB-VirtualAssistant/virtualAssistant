package org.fundacionjala.virtualassistant.clients.openai;

public interface AIClient {
    Boolean connect();
    String chat(String request);
}
