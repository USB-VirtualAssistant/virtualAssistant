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
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String savedAlbums = spotifyClient.getSavedAlbums(spotifyClient.accessToken);

        String simplifiedData = spotifyClient.extractAlbumsData(savedAlbums);

        return ResponseEntity.ok(simplifiedData);
    }

    public ResponseEntity<String> getUserSavedTracks() {
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String tracksData = spotifyClient.getSavedTracks(spotifyClient.accessToken);

        String simplifiedData = spotifyClient.extractTracksData(tracksData);

        return ResponseEntity.ok(simplifiedData);
    }

    public ResponseEntity<String> getUserFollowingArtists() {
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String followingData = spotifyClient.getFollowed(spotifyClient.accessToken);

        String simplifiedData = spotifyClient.extractFollowingData(followingData);

        return ResponseEntity.ok(simplifiedData);
    }

    public ResponseEntity<String> getUserPlayerInformation() {
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        String playerData = spotifyClient.getPlayerInfo(spotifyClient.accessToken);

        String simplifiedData = spotifyClient.extractPlayerData(playerData);

        return ResponseEntity.ok(simplifiedData);
    }

    public ResponseEntity<String> playSong(String trackUri) {
        return null;
    }

    public ResponseEntity<String> playCurrentTrack() {
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        // Resume the playback of the currently paused track using the token of access stored
        spotifyClient.playSong();

        return ResponseEntity.ok("Playback has been resumed.");
    }


    public ResponseEntity<String> pauseCurrentTrack() {
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        // Obtener la información del reproductor del usuario desde la API de Spotify
        String playerData = spotifyClient.getPlayerInfo(spotifyClient.accessToken);

        // Extraer el URI de la canción actualmente en reproducción
        String currentTrackUri = spotifyClient.extractCurrentTrackUri(playerData);

        if (currentTrackUri == null) {
            return ResponseEntity.badRequest().body("No track is currently playing.");
        }

        // Pausar la canción actual utilizando el token de acceso almacenado
        spotifyClient.pauseSongOnDevice(currentTrackUri);

        return ResponseEntity.ok("Current track has been paused.");

    }

    public void logTheUserOut() {
        spotifyClient.logout();
    }

    @GetMapping("/next")
    public ResponseEntity<String> playNextTrack() {
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        // Avanzar a la siguiente canción utilizando el token de acceso almacenado
        boolean success = spotifyClient.playNextTrackOnDevice();

        if (success) {
            return ResponseEntity.ok("Playing next track.");
        } else {
            return ResponseEntity.badRequest().body("Failed to play next track.");
        }
    }

    @GetMapping("/previous")
    public ResponseEntity<String> playPreviousTrack() {
        if (spotifyClient.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not available.");
        }

        // Play the previous track using the token stored in the Spotify client
        boolean success = spotifyClient.playPreviousTrackOnDevice();

        if (success) {
            return ResponseEntity.ok("Playing previous track.");
        } else {
            return ResponseEntity.badRequest().body("Failed to play previous track.");
        }
    }
}
