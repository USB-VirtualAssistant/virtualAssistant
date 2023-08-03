package org.fundacionjala.virtualassistant.clients.openai;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.util.*;

public class OpenAIClient {
    private static final String TOKEN = "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";
    public static final String MODEL = "text-davinci-003";
    public static final int MAX_TOKENS = 1000;
    public static final double TEMPERATURE = 0.8;
    public static final boolean ECHO = true;
    private final OpenAiService service;

    public OpenAIClient(OpenAiService service) {
        this.service = service;
    }

    public String chat(String request) {
        OpenAiService service = new OpenAiService(TOKEN);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(request)
                .model(MODEL)
                .maxTokens(MAX_TOKENS)
                .temperature(TEMPERATURE)
                .echo(ECHO)
                .build();

        StringBuilder answerBuilder = new StringBuilder();
        service.createCompletion(completionRequest).getChoices().forEach(choice -> answerBuilder.append(choice.getText()));
        return removePatternFromStart(answerBuilder, completionRequest.getPrompt());
    }

    public String removePatternFromStart(StringBuilder input, String toRemovePattern) {
        int patternFinishIndex = toRemovePattern.length();
        return input.substring(patternFinishIndex).trim();
    }
}
