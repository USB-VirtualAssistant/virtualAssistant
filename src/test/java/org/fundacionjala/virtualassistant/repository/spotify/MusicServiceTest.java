package org.fundacionjala.virtualassistant.repository.spotify;

import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MusicServiceTest {

    @Test
    public void testGetUserSavedAlbums_Success() {
        // Mock the SpotifyClient
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");

        // Real JSON response for saved albums
        String realAlbumData = "{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}";
        when(spotifyClient.getSavedAlbums("dummyAccessToken")).thenReturn(realAlbumData);

        // Create MusicService with the mock SpotifyClient
        MusicService musicService = new MusicService(spotifyClient);

        // Test the method
        ResponseEntity<String> response = musicService.getUserSavedAlbums();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}", response.getBody());
    }

    @Test
    public void testGetUserSavedAlbums_Unauthorized() {
        // Mock the SpotifyClient
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        // Create MusicService with the mock SpotifyClient
        MusicService musicService = new MusicService(spotifyClient);

        // Test the method
        ResponseEntity<String> response = musicService.getUserSavedAlbums();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());
    }
}
