package org.fundacionjala.virtualassistant.clients.openai.service;


import com.theokanning.openai.service.OpenAiService;
import org.fundacionjala.virtualassistant.clients.openai.client.OpenAiClient;

import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ChatService {
    private OpenAiClient openAiClient;
    private OpenAiService openAiService;

    public ChatService(OpenAiClient openAiClient){
        this.openAiClient = openAiClient;
        this.openAiService = new OpenAiService(openAiClient.getToken());
    }

    @NotEmpty
    @Pattern(regexp = ".*[a-zA-Z].*")
    public String chat(String request) {
        String answer = generateChatAnswer(this.openAiService, request);
        return removePatternFromStart(answer, openAiClient.buildCompletionRequest(request).getPrompt());
    }

    private String generateChatAnswer(OpenAiService service, String request) {
        return service.createCompletion(openAiClient.buildCompletionRequest(request))
                .getChoices()
                .stream()
                .map(choice -> choice.getText())
                .collect(Collectors.joining());
    }

    private String removePatternFromStart(StringBuilder input, String toRemovePattern) {
        int patternFinishIndex = toRemovePattern.length();
        return input.substring(patternFinishIndex).trim();
    }
}
