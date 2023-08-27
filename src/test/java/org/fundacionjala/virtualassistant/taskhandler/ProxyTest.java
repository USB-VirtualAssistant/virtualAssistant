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
    private MusicService musicService;
    private static final String TEXT = "text for tests";

    @BeforeEach
    void setUp() {
        musicService = mock(MusicService.class);
        TaskActionFactory<SpotifyIntent> factory = new SpotifyTaskActionFactory(musicService);
        proxy = new Proxy(factory);
    }

    @Test
    void givenGetAlbumEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.getUserSavedAlbums()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("GET_ALBUMS");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenGetTracksEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.getUserSavedTracks()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("GET_TRACKS");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenGetFollowingEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.getUserFollowingArtists()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("GET_FOLLOWING");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenGetPlayerEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.getUserPlayerInformation()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("GET_PLAYER");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenPauseEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.pauseCurrentTrack()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("PAUSE");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenNextEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.playNextTrack()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("NEXT");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenPreviousEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.playPreviousTrack()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("PREVIOUS");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenContinueEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(musicService.getUserFollowingArtists()).thenReturn(ResponseEntity.ok(TEXT));
        String handledIntent = proxy.handleIntent("CONTINUE");
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenEmptyEnumWhenHandleIntentThenHandleException() {
        IntentException exception = assertThrows(IntentException.class, () -> proxy.handleIntent(""));
        assertEquals(IntentException.INTENT_NOT_FOUND, exception.getMessage());
    }
}