package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;

public class PauseAction implements TaskAction {
    private MusicService musicService;

    public PauseAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction(String intent) {
        return musicService.pauseCurrentTrack().getBody();
    }
}
