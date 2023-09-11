package org.fundacionjala.virtualassistant.openai.client;

import com.theokanning.openai.completion.CompletionRequest;

public interface AiClient {
    String getToken();

    CompletionRequest buildCompletionRequest(String request);
}
