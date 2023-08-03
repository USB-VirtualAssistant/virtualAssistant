package org.fundacionjala.virtualassistant.player.spotify.client;

import org.fundacionjala.virtualassistant.player.spotify.exceptions.MusicPlayerException;
import org.fundacionjala.virtualassistant.player.spotify.exceptions.TokenExtractionException;

public interface MusicClient {

    String getSavedAlbums();

    String getSavedTracks();

    String getFollowed();

    String getPlayerInfo();

    boolean playCurrentSong();

    boolean playSongOnDevice(String trackUri) throws MusicPlayerException;
    boolean playPreviousTrackOnDevice();
    boolean isNotAuthorized();
    boolean pauseSongOnDevice(String trackUri);
    String extractCurrentTrackUri(String playerData);
    String extractPlayerData(String playerData) ;
    String searchTrackByArtistAndTrack(String artist, String track);
    boolean playNextTrackOnDevice();

    void logout();
}
