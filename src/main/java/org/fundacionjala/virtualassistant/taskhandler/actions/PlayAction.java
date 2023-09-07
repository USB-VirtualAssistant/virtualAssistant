package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;

public class PlayAction implements TaskAction {
    private MusicService musicService;

    public PlayAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction(String intent) {
        return musicService.playSongByArtistAndTrack(intent, intent).getBody();
    }
}
