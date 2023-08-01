package org.fundacionjala.virtualassistant.player.spotify.client;

public interface MusicClient {
    String exchangeAuthorizationCodeForAccessToken(String authorizationCode);
    String getUserSavedAlbumsFromSpotify(String accessToken);
    String getUserSavedTracksFromSpotify(String accessToken);
    String getUserFollowingArtistsFromSpotify(String accessToken);
    String getUserPlayerInformationFromSpotify(String accessToken);
    void playSongOnDevice();

    void logTheUserOut();
}
