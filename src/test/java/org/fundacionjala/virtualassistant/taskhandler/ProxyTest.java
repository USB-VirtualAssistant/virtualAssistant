package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.SpotifyTaskActionFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProxyTest {
    private Proxy proxy;
    private TaskActionFactory<SpotifyIntent> factory;
    private MusicService musicService;
    private static final String TEXT = "text for tests";

    @BeforeEach
    void setUp() {
        musicService = mock(MusicService.class);
        factory = new SpotifyTaskActionFactory(musicService);
        proxy = new Proxy(factory);
    }

    @Test
    void shouldMatchWithContinueActionEnum() throws IntentException {
        when(musicService.getUserFollowingArtists()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("CONTINUE");
        assertNotNull(handledIntent);
        assertEquals(handledIntent, TEXT);
    }

    @Test
    void shouldThrowAnIntentException() {
        IntentException exception = assertThrows(IntentException.class, () -> proxy.handleIntent(""));
        assertEquals(exception.getMessage(), IntentException.INTENT_NOT_FOUND);
    }
}