package org.fundacionjala.virtualassistant.task.handler;

import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.withai.TaskHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskHandlerTest {

    @Test
    public void testGetUserSavedAlbums_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("Access Token");

        String realAlbumData = "{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}";
        when(spotifyClient.getSavedAlbums()).thenReturn(realAlbumData);

        MusicService musicService = new MusicService(spotifyClient);

        TaskHandler taskHandler = new TaskHandler(musicService);
        String response = taskHandler.handleResponse();
        assertEquals("{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}", response);
    }
}