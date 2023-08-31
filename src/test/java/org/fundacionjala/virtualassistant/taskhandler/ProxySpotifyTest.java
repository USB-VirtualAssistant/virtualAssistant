package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProxySpotifyTest {
    private Proxy proxy;
    private MusicService musicService;
    private static final String TEXT = "text for tests";
    private static final String MUSIC_CONTINUE = "music_continue";
    private static final String MUSIC_PAUSE = "music_pause";
    private static final String MUSIC_NEXT = "music_next";
    private static final String MUSIC_ALBUMS = "music_albums";
    private static final String MUSIC_FOLLOWING = "music_following";
    private static final String MUSIC_PLAYER = "music_player";
    private static final String MUSIC_PREVIOUS = "music_previous";
    private static final String MUSIC_TRACKS = "music_tracks";
    private static final String MUSIC_PLAY = "music_play";

    @BeforeEach
    void setUp() {
        musicService = mock(MusicService.class);
        TaskActionManagerFactory taskActionManagerFactory = new TaskActionManagerFactoryImpl(
                Map.of(
                        "music_continue", Intent.SPOTIFY,
                        "music_pause", Intent.SPOTIFY,
                        "music_next", Intent.SPOTIFY,
                        "music_albums", Intent.SPOTIFY,
                        "music_following", Intent.SPOTIFY,
                        "music_player", Intent.SPOTIFY,
                        "music_previous", Intent.SPOTIFY,
                        "music_tracks", Intent.SPOTIFY,
                        "music_play", Intent.SPOTIFY
                ),

                musicService
        );
        IntentFactory intentFactory = new IntentFactoryImpl();
        proxy = new Proxy(taskActionManagerFactory, intentFactory);
        when(musicService.getUserSavedAlbums()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.getUserSavedTracks()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.getUserFollowingArtists()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.getUserPlayerInformation()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.pauseCurrentTrack()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.getUserFollowingArtists()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.playNextTrack()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.playPreviousTrack()).thenReturn(ResponseEntity.ok(TEXT));
        when(musicService.playSongByArtistAndTrack(any(), any())).thenReturn(ResponseEntity.ok(TEXT));
    }

    @Test
    void givenGetAlbumEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_ALBUMS);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenGetTracksEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_TRACKS);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenGetFollowingEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_FOLLOWING);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenGetPlayerEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_PLAYER);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenPauseEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_PAUSE);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenNextEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_NEXT);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenPreviousEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_PREVIOUS);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenContinueEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_CONTINUE);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenPlayEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        String handledIntent = proxy.handleIntent(MUSIC_PLAY);
        assertNotNull(handledIntent);
        assertEquals(TEXT, handledIntent);
    }

    @Test
    void givenEmptyEnumWhenHandleIntentThenHandleException() {
        IntentException exception = assertThrows(IntentException.class, () -> proxy.handleIntent(""));
        assertEquals(IntentException.INTENT_NOT_FOUND, exception.getMessage());
    }
}