package org.fundacionjala.virtualassistant.player.spotify.exceptions;

public enum ErrorMsg {
    ACCESS_TOKEN_JSON("Failed to process Access token in response JSON"),
    TOKEN_JSON("Failed to process in response JSON"),
    API_CALL_FAILED("Music API call failed:"),
    FAILED_REQUEST_API("Failed to make request to Music API"),
    NO_TRACK_PLAYING("No track is currently playing.")
    ;

    private final String message;

    ErrorMsg(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
