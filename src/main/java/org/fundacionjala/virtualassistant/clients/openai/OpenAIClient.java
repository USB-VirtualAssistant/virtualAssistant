<<<<<<< HEAD
=======
package org.fundacionjala.virtualassistant.clients.openai;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:openAi.properties")
public class OpenAIClient implements AIClient {
    public static final String MODEL = "text-davinci-003";
    public static final int MAX_TOKENS = 1000;
    public static final double TEMPERATURE = 0.8;
    public static final boolean ECHO = true;

    private OpenAiService service;

    @Value("${openAi.client.token}")
    private String clientToken;

    public OpenAIClient() {
        System.out.println(clientToken);
    }

    @Override
    public Boolean connect() {
        return null;
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

    private String removePatternFromStart(StringBuilder input, String toRemovePattern) {
        int patternFinishIndex = toRemovePattern.length();
        return input.substring(patternFinishIndex).trim();
    }
}
>>>>>>> va-039-melanyArgandona2002-added properties for open ai client
