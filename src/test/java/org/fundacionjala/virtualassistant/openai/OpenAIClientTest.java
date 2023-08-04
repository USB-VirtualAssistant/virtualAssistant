package org.fundacionjala.virtualassistant.openai;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.fundacionjala.virtualassistant.clients.openai.OpenAIClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenAIClientTest {

    private OpenAIClient client;
    private OpenAiService service;

    @BeforeEach
    void setUp() {
        service = mock(OpenAiService.class);
        client = new OpenAIClient(service);
    }

    @Test
    public void chatNotNull() {
        CompletionChoice choice = new CompletionChoice();
        choice.setText("Hello world");
        CompletionResult completion = new CompletionResult();
        completion.setChoices(Collections.singletonList(choice));
        when(service.createCompletion(any())).thenReturn(completion);
        String result = client.chat("Hi");

        assertTrue(isStringNotNull(result), "Chat result should not be null");
    }

    @Test
    public void chatNotEmpty() {
        CompletionChoice choice = new CompletionChoice();
        choice.setText("Hello world");
        CompletionResult completion = new CompletionResult();
        completion.setChoices(Collections.singletonList(choice));
        when(service.createCompletion(any())).thenReturn(completion);
        String result = client.chat("Hi");

        assertFalse(result.isEmpty(), "Chat result should not be empty");
    }
    public boolean isStringNotNull(String input) {
        return input != null;
    }
}