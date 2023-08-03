package org.fundacionjala.virtualassistant.player.spotify.service;

import org.fundacionjala.virtualassistant.player.spotify.client.MusicClient;
import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MusicService {

    private final MusicClient spotifyClient;

    @Autowired
    public MusicService(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public ResponseEntity<String> getUserSavedAlbums() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String savedAlbums = spotifyClient.getSavedAlbums();
        return ResponseEntity.ok(savedAlbums);
    }

    public ResponseEntity<String> getUserSavedTracks() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String tracksData = spotifyClient.getSavedTracks();
        return ResponseEntity.ok(tracksData);
    }

    public ResponseEntity<String> getUserFollowingArtists() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String followingData = spotifyClient.getFollowed();
        return ResponseEntity.ok(followingData);
    }

    public ResponseEntity<String> getUserPlayerInformation() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String playerData = spotifyClient.getPlayerInfo();
        String simplifiedData = spotifyClient.extractPlayerData(playerData);

        return ResponseEntity.ok(simplifiedData);
    }

    public ResponseEntity<String> playCurrentTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        spotifyClient.playCurrentSong();
        return ResponseEntity.ok("Playback has been resumed.");
    }


    public ResponseEntity<String> pauseCurrentTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String playerData = spotifyClient.getPlayerInfo();
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
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }
        boolean success = spotifyClient.playNextTrackOnDevice();

        if (success) {
            return ResponseEntity.ok("Playing next track.");
        } else {
            return ResponseEntity.badRequest().body("Failed to play next track.");
        }
    }

    public ResponseEntity<String> playPreviousTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        boolean success = spotifyClient.playPreviousTrackOnDevice();
        if (success) {
            return ResponseEntity.ok("Playing previous track.");
        } else {
            return ResponseEntity.badRequest().body("Failed to play previous track.");
        }
    }

    public ResponseEntity<String> playSongByArtistAndTrack(String artist, String track) {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String trackUri = spotifyClient.searchTrackByArtistAndTrack(artist, track);

        if (trackUri == null) {
            return ResponseEntity.badRequest().body("Song not found.");
        }

        boolean success = spotifyClient.playSongOnDevice(trackUri);

        if (success) {
            return ResponseEntity.ok("Playing song by " + artist + ": " + track);
        } else {
            return ResponseEntity.badRequest().body("Failed to play the song.");
        }
    }
}
