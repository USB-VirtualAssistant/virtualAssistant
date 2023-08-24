package org.fundacionjala.virtualassistant.openai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.CompletionChoice;
import org.fundacionjala.virtualassistant.clients.openai.client.OpenAiClient;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OpenAIClientTest {

    @Mock
    private OpenAiService openAiService;

    private OpenAiClient openAIClient;
    private  ChatService chatService;

    private static final String REQUEST = "hi how are you";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        openAIClient = new OpenAiClient();
        chatService = new ChatService(openAIClient);
    }

    @Test
    void shouldReturnAStringAndCallOpenAIServiceMethodsWhenARequestIsUseInOpenAIClient() {
        CompletionChoice completionChoice = new CompletionChoice();
        completionChoice.setText(REQUEST);

        CompletionResult completionResult = mock(CompletionResult.class);
        when(completionResult.getChoices()).thenReturn(List.of(completionChoice));

        CompletionRequest completionRequest = openAIClient.buildCompletionRequest(REQUEST);

        when(openAiService.createCompletion(completionRequest)).thenReturn(completionResult);

        String result = chatService.chat(REQUEST);

        verify(openAiService).createCompletion(completionRequest);
        verify(completionResult).getChoices();
        assertNotNull(result, "result should not be null");
    }
}
