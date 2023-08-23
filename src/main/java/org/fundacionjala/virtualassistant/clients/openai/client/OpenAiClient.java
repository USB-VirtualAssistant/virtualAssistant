package org.fundacionjala.virtualassistant.clients.openai.client;

import com.theokanning.openai.completion.CompletionRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OpenAiClient implements AiClient{
    public static final String MODEL = "text-davinci-003";
    public static final int MAX_TOKENS = 1000;
    public static final double TEMPERATURE = 0.8;
    public static final boolean ECHO = true;
    public static final String TOKEN = "my_token";

    @Override
    public String getToken() {
        return TOKEN;
    }

    @Override
    public CompletionRequest buildCompletionRequest(String request) {
        return CompletionRequest.builder()
                .prompt(request)
                .model(MODEL)
                .maxTokens(MAX_TOKENS)
                .temperature(TEMPERATURE)
                .echo(ECHO)
                .build();
    }
}
