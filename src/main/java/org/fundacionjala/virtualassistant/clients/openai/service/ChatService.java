package org.fundacionjala.virtualassistant.clients.openai.service;

import com.theokanning.openai.service.OpenAiService;
import org.fundacionjala.virtualassistant.clients.openai.client.OpenAiClient;

import javax.validation.constraints.NotEmpty;

public class ChatService {
    private OpenAiClient openAiClient;

    public ChatService(OpenAiClient openAiClient){
        this.openAiClient = openAiClient;
    }

    @NotEmpty
    public String chat(String request) {
        OpenAiService service = new OpenAiService(openAiClient.getToken());

        StringBuilder answerBuilder = new StringBuilder();
        service.createCompletion(openAiClient.buildCompletionRequest(request))
                .getChoices()
                .forEach(choice -> answerBuilder.append(choice.getText()));
        return removePatternFromStart(answerBuilder, openAiClient.buildCompletionRequest(request).getPrompt());
    }

    private String removePatternFromStart(StringBuilder input, String toRemovePattern) {
        int patternFinishIndex = toRemovePattern.length();
        return input.substring(patternFinishIndex).trim();
    }
}
