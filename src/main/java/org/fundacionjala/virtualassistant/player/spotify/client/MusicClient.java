package org.fundacionjala.virtualassistant.player.spotify.client;

public interface MusicClient {
    String exchangeAuthCodeForAccessToken(String authorizationCode);

    String getSavedAlbums(String accessToken);

    String getSavedTracks(String accessToken);

    String getFollowed(String accessToken);

    String getPlayerInfo(String accessToken);

    void playCurrentSong();

    boolean playSongOnDevice(String trackUri);

    void logout();
}
