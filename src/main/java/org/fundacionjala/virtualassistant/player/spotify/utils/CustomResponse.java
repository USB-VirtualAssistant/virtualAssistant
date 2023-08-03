package org.fundacionjala.virtualassistant.player.spotify.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse {
    private final static String NOT_ACCESS_TOKEN = "Access token not available.";
    private final static String SUCCESS_LOGIN = "Logged in successfully.";

    public static ResponseEntity<String> notAccessTokenResponse() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_ACCESS_TOKEN);
    }

    public static ResponseEntity<String> successLogin() {
        return success(SUCCESS_LOGIN);
    }

    public static ResponseEntity<String> failedLogin(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    public static ResponseEntity<String> success(String message) {
        return ResponseEntity.ok(message);
    }


}
