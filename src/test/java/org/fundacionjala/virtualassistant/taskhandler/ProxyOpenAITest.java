package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProxyOpenAITest {
    private Proxy proxy;
    private ChatService chatService;
    private static final String TEXT = "text for tests";
    private static final String CHAT = "chat";

    @BeforeEach
    void setUp() {
        chatService = mock(ChatService.class);
        TaskActionManagerFactory taskActionManagerFactory = new TaskActionManagerFactoryImpl(
                Map.of(
                        "chat", Intent.CHAT_GPT
                ),

                chatService
        );
        IntentFactory intentFactory = new IntentFactoryImpl();
        proxy = new Proxy(taskActionManagerFactory, intentFactory);
        when(chatService.chat(any())).thenReturn(TEXT);
    }

    @Test
    void givenChatEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(CHAT);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenEmptyEnumWhenHandleIntentThenHandleException() {
        IntentException exception = assertThrows(IntentException.class, () -> proxy.handleIntent(""));
        assertEquals(IntentException.INTENT_NOT_FOUND, exception.getMessage());
    }
}