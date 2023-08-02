package org.fundacionjala.virtualassistant.player.spotify.service;

import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class MusicService {

    private final SpotifyClient spotifyClient;

    @Autowired
    public MusicService(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public ResponseEntity<String> getUserSavedAlbums() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String savedAlbums = spotifyClient.getSavedAlbums(spotifyClient.getAccessToken());

        return ResponseEntity.ok(savedAlbums);
    }

    public ResponseEntity<String> getUserSavedTracks() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String tracksData = spotifyClient.getSavedTracks(spotifyClient.getAccessToken());

        return ResponseEntity.ok(tracksData);
    }

    public ResponseEntity<String> getUserFollowingArtists() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String followingData = spotifyClient.getFollowed(spotifyClient.getAccessToken());

        return ResponseEntity.ok(followingData);
    }

    public ResponseEntity<String> getUserPlayerInformation() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String playerData = spotifyClient.getPlayerInfo(spotifyClient.getAccessToken());

        String simplifiedData = spotifyClient.extractPlayerData(playerData);

        return ResponseEntity.ok(simplifiedData);
    }

    public ResponseEntity<String> playSong(String trackUri) {
        return null;
    }

    public ResponseEntity<String> playCurrentTrack() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        spotifyClient.playSong();

        return ResponseEntity.ok("Playback has been resumed.");
    }


    public ResponseEntity<String> pauseCurrentTrack() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String playerData = spotifyClient.getPlayerInfo(spotifyClient.getAccessToken());

        String currentTrackUri = spotifyClient.extractCurrentTrackUri(playerData);

        if (currentTrackUri == null) {
            return ResponseEntity.badRequest().body("No track is currently playing.");
        }

        spotifyClient.pauseSongOnDevice(currentTrackUri);

        return ResponseEntity.ok("Current track has been paused.");

    }

    public void logTheUserOut() {
        spotifyClient.logout();
    }

    public ResponseEntity<String> playNextTrack() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        boolean success = spotifyClient.playNextTrackOnDevice();

        if (success) {
            return ResponseEntity.ok("Playing next track.");
        } else {
            return ResponseEntity.badRequest().body("Failed to play next track.");
        }
    }

    public ResponseEntity<String> playPreviousTrack() {
        if (spotifyClient.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        boolean success = spotifyClient.playPreviousTrackOnDevice();

        if (success) {
            return ResponseEntity.ok("Playing previous track.");
        } else {
            return ResponseEntity.badRequest().body("Failed to play previous track.");
        }
    }
}
