package org.fundacionjala.virtualassistant.player.spotify.client;

public interface MusicClient {
    String exchangeAuthCodeForAccessToken(String authorizationCode);

    String getSavedAlbums();

    String getSavedTracks();

    String getFollowed();

    String getPlayerInfo();

    boolean playCurrentSong();

    boolean playSongOnDevice(String trackUri);
    boolean playPreviousTrackOnDevice();
    boolean isNotAuthorized();
    boolean pauseSongOnDevice(String trackUri);
    String extractCurrentTrackUri(String playerData);
    String extractPlayerData(String playerData);
    String searchTrackByArtistAndTrack(String artist, String track);
    boolean playNextTrackOnDevice();

    void logout();
}
