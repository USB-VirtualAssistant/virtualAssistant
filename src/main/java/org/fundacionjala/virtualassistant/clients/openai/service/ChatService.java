package org.fundacionjala.virtualassistant.clients.openai.service;


import com.theokanning.openai.service.OpenAiService;
import org.fundacionjala.virtualassistant.clients.openai.client.OpenAiClient;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Service
public class ChatService {
    private OpenAiClient openAiClient;
    private OpenAiService openAiService;

    public ChatService(OpenAiClient openAiClient, OpenAiService openAiService) {
        this.openAiClient = openAiClient;
        this.openAiService = openAiService;
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

    private String removePatternFromStart(String input, String toRemovePattern) {
        int patternFinishIndex = toRemovePattern.length();
        return input.substring(patternFinishIndex).trim();
    }
}
