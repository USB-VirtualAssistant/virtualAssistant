package org.fundacionjala.virtualassistant.player.spotify.service;

import org.fundacionjala.virtualassistant.player.spotify.client.MusicClient;
import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    private final MusicClient spotifyClient;
    private final static String PLAYING_NEXT = "Playing next track.";
    private final static String FAILED_NEXT = "Failed to play next track.";
    private final static String PLAYING_PREVIOUS = "Playing previous track.";
    private final static String FAILED_PREVIOUS = "Failed to play previous track.";
    private final static String SONG_NOT_FOUND = "Song not found.";
    private final static String PLAYING_SONG = "Playing song by ";
    private final static String FAILED_SONG = "Failed to play the song.";
    private final static String PLAYBACK_RESUMED = "Playback has been resumed.";
    private final static String BAD_REQUEST = "No track is currently playing.";
    private final static String TRACK_PAUSED = "Current track has been paused.";

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
        return ResponseEntity.ok(PLAYBACK_RESUMED);
    }


    public ResponseEntity<String> pauseCurrentTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String playerData = spotifyClient.getPlayerInfo();
        String currentTrackUri = spotifyClient.extractCurrentTrackUri(playerData);

        if (currentTrackUri == null) {
            return ResponseEntity.badRequest().body(BAD_REQUEST);
        }

        spotifyClient.pauseSongOnDevice(currentTrackUri);
        return ResponseEntity.ok(TRACK_PAUSED);

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
            return ResponseEntity.ok(PLAYING_NEXT);
        } else {
            return ResponseEntity.badRequest().body(FAILED_NEXT);
        }
    }

    public ResponseEntity<String> playPreviousTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        boolean success = spotifyClient.playPreviousTrackOnDevice();
        if (success) {
            return ResponseEntity.ok(PLAYING_PREVIOUS);
        } else {
            return ResponseEntity.badRequest().body(FAILED_PREVIOUS);
        }
    }

    public ResponseEntity<String> playSongByArtistAndTrack(String artist, String track) {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String trackUri = spotifyClient.searchTrackByArtistAndTrack(artist, track);

        if (trackUri == null) {
            return ResponseEntity.badRequest().body(SONG_NOT_FOUND);
        }

        boolean success = spotifyClient.playSongOnDevice(trackUri);

        if (success) {
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("Playing song by ").append(artist).append(": ").append(track);
            return ResponseEntity.ok(responseBuilder.toString());
        } else {
            return ResponseEntity.badRequest().body(FAILED_SONG);
        }
    }
}
