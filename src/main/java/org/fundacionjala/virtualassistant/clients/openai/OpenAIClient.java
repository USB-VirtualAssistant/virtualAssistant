package org.fundacionjala.virtualassistant.clients.openai;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OpenAIClient implements AIClientInterface {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String TOKEN = dotenv.get("TOKEN_AICLIENTE");
    public static final String MODEL = "text-davinci-003";
    public static final int MAX_TOKENS = 1000;
    public static final double TEMPERATURE = 0.8;
    public static final boolean ECHO = true;
    private OpenAiService service;

    public OpenAIClient(OpenAiService service) {
        this.service = service;
    }

    @Override
    public String chat(String request) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(request)
                .model(MODEL)
                .maxTokens(MAX_TOKENS)
                .temperature(TEMPERATURE)
                .echo(ECHO)
                .build();

        StringBuilder answerBuilder = new StringBuilder();
        this.service.createCompletion(completionRequest)
                .getChoices()
                .forEach(choice -> answerBuilder.append(choice.getText()));
        return removePatternFromStart(answerBuilder, completionRequest.getPrompt());
    }

    @Override
    public String removePatternFromStart(StringBuilder input, String toRemovePattern) {
        int patternFinishIndex = toRemovePattern.length();
        return input.substring(patternFinishIndex).trim();
    }
}
