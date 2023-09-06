package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactoryImpl;
import org.fundacionjala.virtualassistant.user_intetions.client.RasaClient;
import org.fundacionjala.virtualassistant.user_intetions.client.response.Intent;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProxyOpenAITest {
    private Proxy proxy;
    private RasaClient rasaClient;

    private static final String CHAT = "chat";
    private static final String RESULT = "result";
    private static final List<IntentEntity> ENTITIES = List.of(new IntentEntity("entity", "value"));
    private static final IntentResponse INTENT_RESPONSE = new IntentResponse(ENTITIES, new Intent(0, CHAT));
    private static final IntentResponse EMPTY_INTENT_RESPONSE = new IntentResponse(ENTITIES, new Intent(0, ""));

    @BeforeEach
    void setUp() {
        rasaClient = mock(RasaClient.class);
        MusicService musicService = mock(MusicService.class);
        ChatService chatService = mock(ChatService.class);
        TaskActionManagerFactory taskActionManagerFactory = new TaskActionManagerFactoryImpl(musicService, chatService);
        IntentFactory intentFactory = new IntentFactoryImpl();

        proxy = new Proxy(rasaClient, intentFactory, taskActionManagerFactory);

        when(chatService.chat(any())).thenReturn(RESULT);
    }

    @Test
    void givenChatEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(INTENT_RESPONSE, HttpStatus.OK));

        String handledIntent = proxy.handleIntent(CHAT);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenEmptyEnumWhenHandleIntentThenHandleException() {
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(EMPTY_INTENT_RESPONSE, HttpStatus.OK));

        IntentException exception = assertThrows(IntentException.class, () -> proxy.handleIntent(""));
        assertEquals(IntentException.INTENT_NOT_FOUND, exception.getMessage());
    }
}