package org.fundacionjala.virtualassistant.opennpl;

import org.fundacionjala.virtualassistant.opennlp.TaskHandler;
import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskHandlerTest {

    public final static String ACCESS_TOKEN = "Access Token";
    public final static String ALBUM_DATA = "{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}";
    public final static String TRACKS_DATA = "{\"items\": [{\"track\": {\"name\": \"Track 1\"}}, {\"track\": {\"name\": \"Track 2\"}}]}";
    public final static String INFORMATION_QUERY = "Tell me about the major achievements of Marie Curie";
    public final static String ALBUMS_QUERY = "Get all my albums";
    public final static String TRACKS_QUERY = "Get the best tracks from my playlist";
    public final static String CONTACTS_QUERY = "Email Jane Foster \"Updates on the project\"";

    @Test
    void givenText_whenAskingInformation_thenGetAnswer() {
        TaskHandler taskHandler = new TaskHandler();

        String response = taskHandler.handleIntents(INFORMATION_QUERY);

        assertEquals("Marie Curie", response);
    }

    @Test
    void givenText_whenGettingAlbums_thenGetAlbums() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        when(spotifyClient.getSavedAlbums()).thenReturn(ALBUM_DATA);
        MusicService musicService = new MusicService(spotifyClient);

        TaskHandler taskHandler = new TaskHandler(musicService);
        String response = taskHandler.handleIntents(ALBUMS_QUERY);

        assertEquals(ALBUM_DATA, response);
    }

    @Test
    void givenText_whenGettingTracks_thenGetTracks() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        when(spotifyClient.getSavedTracks()).thenReturn(TRACKS_DATA);
        MusicService musicService = new MusicService(spotifyClient);

        TaskHandler taskHandler = new TaskHandler(musicService);
        String response = taskHandler.handleIntents(TRACKS_QUERY);

        assertEquals(TRACKS_DATA, response);
    }

    @Test
    void givenText_whenAccessingContacts_thenContact() {
        TaskHandler taskHandler = new TaskHandler();

        String response = taskHandler.handleIntents(CONTACTS_QUERY);

        assertEquals("Jane Foster", response);
    }

    @Test
    void givenText_whenAskingInformation_thenGetVariousAnswers() {
        TaskHandler taskHandler = new TaskHandler();

        String response = taskHandler.handleIntents(INFORMATION_QUERY + " and John Meyer");

        assertEquals("Marie Curie, John Meyer", response);
    }

    @Test
    void givenText_whenAccessingContacts_thenVariousContacts() {
        TaskHandler taskHandler = new TaskHandler();

        String response = taskHandler.handleIntents(CONTACTS_QUERY + ". Message Emily Perez");

        assertEquals("Jane Foster, Emily Perez", response);
    }
}