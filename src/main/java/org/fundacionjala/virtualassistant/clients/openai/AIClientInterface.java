package org.fundacionjala.virtualassistant.clients.openai;

public interface AIClientInterface {
    String chat(String request);

    String removePatternFromStart(StringBuilder input, String toRemovePattern);
}
