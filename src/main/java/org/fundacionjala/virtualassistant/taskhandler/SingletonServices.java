package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;

public class SingletonServices {

    private static MusicService musicService;

    private SingletonServices() {}

    public static synchronized MusicService getMusicService() {
        if (musicService == null) {
            musicService = new MusicService(new SpotifyClient());
        }
        return musicService;
    }
}
