package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;

public class Proxy {
    public String handleIntent(String userIntent) {
        Intent enumIntent = Intent.valueOf(userIntent);
        return enumIntent.getTaskAction().handleAction();
    }
}
