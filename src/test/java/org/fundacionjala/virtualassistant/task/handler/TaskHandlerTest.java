package org.fundacionjala.virtualassistant.task.handler;

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

    @Test
    public void givenText_whenHandlingText_thenGetAlbums() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        when(spotifyClient.getSavedAlbums()).thenReturn(ALBUM_DATA);
        MusicService musicService = new MusicService(spotifyClient);

        TaskHandler taskHandler = new TaskHandler(musicService);
        String response = taskHandler.handleTask("Get all my albums.");
        assertEquals(ALBUM_DATA, response);
    }
}