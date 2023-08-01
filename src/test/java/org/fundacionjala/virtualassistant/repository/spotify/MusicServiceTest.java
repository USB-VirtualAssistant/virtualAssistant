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
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");

        String realAlbumData = "{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}";
        when(spotifyClient.getSavedAlbums("dummyAccessToken")).thenReturn(realAlbumData);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedAlbums();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}", response.getBody());
    }

    @Test
    public void testGetUserSavedAlbums_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedAlbums();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());
    }

    @Test
    public void testGetUserSavedTracks_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");

        String realTracksData = "{\"items\": [{\"track\": {\"name\": \"Track 1\"}}, {\"track\": {\"name\": \"Track 2\"}}]}";
        when(spotifyClient.getSavedTracks("dummyAccessToken")).thenReturn(realTracksData);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedTracks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(realTracksData, response.getBody());
    }

    @Test
    public void testGetUserSavedTracks_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedTracks();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());
    }

    @Test
    public void testPlayPreviousTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");
        when(spotifyClient.playPreviousTrackOnDevice()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playPreviousTrack();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Playing previous track.", response.getBody());
    }

    @Test
    public void testPlayPreviousTrack_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playPreviousTrack();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());
    }

    @Test
    public void testPlayCurrentTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playCurrentTrack();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Playback has been resumed.", response.getBody());

        verify(spotifyClient, times(1)).playSong();
    }

    @Test
    public void testPlayCurrentTrack_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playCurrentTrack();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());

        verify(spotifyClient, never()).playSong();
    }

    @Test
    public void testGetUserSavedFollowing_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");

        String realFollowingData = "Radiohead, Gustavo Cerati, Arctic Monkeys";
        when(spotifyClient.getFollowed("dummyAccessToken")).thenReturn(realFollowingData);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserFollowingArtists();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Radiohead, Gustavo Cerati, Arctic Monkeys", response.getBody());
    }

    @Test
    public void testGetUserSavedFollowing_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserFollowingArtists();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());
    }

    @Test
    public void testGetUserPlayerInformation_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");

        String realPlayerData = "{\"device\": {\"name\": \"Device 1\"}, \"is_playing\": true, \"item\": {\"name\": \"Song 1\"}}";
        when(spotifyClient.getPlayerInfo("dummyAccessToken")).thenReturn(realPlayerData);

        String simplifiedPlayerData = "{\"device\": \"Device 1\", \"is_playing\": true, \"current_track\": \"Song 1\"}";
        when(spotifyClient.extractPlayerData(realPlayerData)).thenReturn(simplifiedPlayerData);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserPlayerInformation();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(simplifiedPlayerData, response.getBody());
    }

    @Test
    public void testGetUserPlayerInformation_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserPlayerInformation();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());
    }

    @Test
    public void testPauseCurrentTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");
        when(spotifyClient.getPlayerInfo("dummyAccessToken")).thenReturn("playerData");
        when(spotifyClient.extractCurrentTrackUri("playerData")).thenReturn("currentTrackUri");

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.pauseCurrentTrack();
        assertEquals("Current track has been paused.", response.getBody());

        verify(spotifyClient, times(1)).pauseSongOnDevice("currentTrackUri");
    }

    @Test
    public void testPauseCurrentTrack_NoTrackPlaying() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");
        when(spotifyClient.getPlayerInfo("dummyAccessToken")).thenReturn("playerData");
        when(spotifyClient.extractCurrentTrackUri("playerData")).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.pauseCurrentTrack();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No track is currently playing.", response.getBody());

        verify(spotifyClient, never()).pauseSongOnDevice(any());
    }

    @Test
    public void testPlayNextTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn("dummyAccessToken");
        when(spotifyClient.playNextTrackOnDevice()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playNextTrack();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Playing next track.", response.getBody());
    }

    @Test
    public void testPlayNextTrack_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playNextTrack();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access token not available.", response.getBody());
    }
}
