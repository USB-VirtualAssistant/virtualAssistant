package org.fundacionjala.virtualassistant.openai;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.fundacionjala.virtualassistant.clients.openai.OpenAIClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OpenAIClientTest {

    private OpenAiService openAiService;
    private OpenAIClient openAIClient;
    private CompletionRequest completionRequest;
    private CompletionResult completionResult;
    private CompletionChoice completionChoice;
    private static final String REQUEST = "hi how are you";

    @BeforeEach
    void setUp() {
        openAiService = mock(OpenAiService.class);
        openAIClient = new OpenAIClient(openAiService);
        completionRequest = CompletionRequest.builder()
                .prompt(REQUEST)
                .model(OpenAIClient.MODEL)
                .maxTokens(OpenAIClient.MAX_TOKENS)
                .temperature(OpenAIClient.TEMPERATURE)
                .echo(OpenAIClient.ECHO)
                .build();

        completionChoice = new CompletionChoice();
        completionChoice.setText(REQUEST);
        completionResult = mock(CompletionResult.class);
    }

    @Test
    void shouldReturnAStringAndCallOpenAIServiceMethodsWhenARequestIsUseInOpenAIClient() {
        when(openAiService.createCompletion(completionRequest)).thenReturn(completionResult);
        when(completionResult.getChoices()).thenReturn(List.of(completionChoice));

        String result = openAIClient.chat(REQUEST);

        verify(openAiService).createCompletion(completionRequest);
        verify(completionResult).getChoices();
        assertNotNull(result, "result should not be null");
    }
}