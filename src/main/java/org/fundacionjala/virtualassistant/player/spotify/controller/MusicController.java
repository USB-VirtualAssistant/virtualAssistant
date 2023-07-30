package org.fundacionjala.virtualassistant.player.spotify.controller;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MusicController {

    private final MusicService musicService;
    private final SpotifyClient spotifyClient;

    @Autowired
    public MusicController(MusicService musicService, SpotifyClient spotifyClient) {
        this.musicService = musicService;
        this.spotifyClient = spotifyClient;
    }

    @GetMapping("/")
    public RedirectView redirectToSpotifyAuthorization() {
        return spotifyClient.redirectToSpotifyAuthorization();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> spotifyCallback(@RequestParam("code") String code) {
        return spotifyClient.spotifyCallback(code);
    }

    @GetMapping("/albums")
    public ResponseEntity<String> getUserSavedAlbums() {
        return musicService.getUserSavedAlbums();
    }

    @GetMapping("/tracks")
    public ResponseEntity<String> getUserSavedTracks() {
        return musicService.getUserSavedTracks();
    }

    @GetMapping("/following")
    public ResponseEntity<String> getUserFollowingArtists() {
        return musicService.getUserFollowingArtists();
    }

    @GetMapping("/player")
    public ResponseEntity<String> getUserPlayerInformation() {
        return musicService.getUserPlayerInformation();
    }

    @PostMapping("/play")
    public ResponseEntity<String> playSong(@RequestParam("trackUri") String trackUri) {
        return musicService.playSong(trackUri);
    }

    @GetMapping("/pause")
    public ResponseEntity<String> pauseCurrentTrack() {
        return musicService.pauseCurrentTrack();
    }

    @GetMapping("/next")
    public ResponseEntity<String> playNextTrack() {
        return musicService.playNextTrack();
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logTheUserOut() {
        musicService.logTheUserOut();
        return ResponseEntity.ok("Logged out successfully.");
    }
}
