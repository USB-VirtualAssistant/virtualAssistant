package org.fundacionjala.virtualassistant.player.spotify.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse {
    private final static String NOT_ACCESS_TOKEN = "Access token not available.";

    public static ResponseEntity<String> notAccessTokenResponse() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_ACCESS_TOKEN);
    }
}
